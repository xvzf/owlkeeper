\echo
\echo '--------------------------'
\echo 'Owlkeeper Table Bootstrap '
\echo '--------------------------'
\echo

\set VERBOSITY terse
\set ON_ERROR_STOP true

do language plpgsql $$ begin


raise notice '[+] Creating sysconf schema and add migration stamp';
do $sysconf$ begin

  create table if not exists sysconf (
    id serial primary key
    , created timestamp default now()
    , param text not null unique
    , value text not null
  );

  insert into sysconf (param, value) values ('migration', 'bootstrap');
end $sysconf$;


--
-- User and team related schemas
--
raise notice '[+] Creating developer and team related schemas';
do $developers$ begin

  -- Users
  create table if not exists developer (
    id serial primary key
    , created timestamp default now()
    , name text not null
    , role text default ''
    , email text not null unique check(email like '_%@__%.__%') -- Checks if E-Mail valid, not bulletproof
    , pwhash varchar(100)
    , is_chief boolean default FALSE
  );

  -- Team
  create table if not exists team (
    id serial primary key
    , created timestamp default now()
    , name text not null unique
    , leader integer references developer (id)
  );

  -- Relation; Developers can be part of multiple teams
  create table if not exists developer_team_relation (
    id serial primary key
    , created timestamp default now()
    , developer integer not null references developer (id)
    , team integer not null references team (id)
    -- Constraints
    , constraint map_unique unique (developer, team)
  );

end $developers$;

--
-- Project related schemas
--
raise notice '[+] Creating project related schemas';
do $projects$ begin

  create table if not exists project (
    id serial primary key
    , created timestamp default now()
    , name text unique
    , description text
    -- Limit project types
    , type text check (type in (
      'waterfall'
      , 'spiral'
      , 'v-model'
      , 'none'
      ) )
  );

  create table if not exists project_stage (
    id serial primary key
    , created timestamp default now()
    , name text not null
    , project integer not null references project (id)
    , index integer not null
    -- Ensure that project stages do not collide
    , constraint index_project_unique unique (project, index)
  );

end $projects$;

--
-- Task related schemas
--
raise notice '[+] Creating task related schemas, functions and triggers';
do $tasks$ begin

  create table if not exists task (
    id serial primary key
    , created timestamp default now()
    , deadline timestamp not null
    , name text not null
    , description text not null
    , fulfilled timestamp
    , project_stage integer not null references project_stage (id)
    , team integer references team (id)
    -- Ensure that there are not tasks with the same name for the each project stage
    , constraint name_project_stage_unique unique (project_stage, name)
  );

  create table if not exists task_dependency (
    id serial primary key
    , created timestamp default now()
    , task integer not null references task (id)
    , depends integer not null references task (id)
    -- Ensure tasks don't refer to itself
    , constraint task_no_depends_self check ( task != depends )
  );

  -- Ensure there are no circular dependencies
  create or replace function task_dependency_before_insert() returns trigger as $trigger$
  declare
    found int;
  begin
    -- Query existing dependencies
    found := count(*) from task_dependency where (task = new.task and depends = new.depends)
                                                 or (task = new.depends and depends = new.task);

    if found > 0 then
      raise exception 'duplicate entry or circular dependency';
    end if;

    return new;
  end;
  $trigger$ language 'plpgsql';

  create trigger task_dependency_before_insert_trigger
    before insert or update on task_dependency for each row
    execute procedure task_dependency_before_insert();

  create table if not exists task_comment (
    id serial primary key
    , created timestamp default now()
    , content text not null
    , developer integer not null references developer (id)
    , task integer not null references task (id)
  );

end $tasks$;

end$$
