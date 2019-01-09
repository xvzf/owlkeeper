-----------------------------------------
-- SQL database bootstrap for Owlkeeper
-----------------------------------------
-- Changelog:
--   09.01.19 Matthias Riegler <me@xvzf.tech> Added developer/team related tables

\echo
\echo '----------------------------'
\echo 'Owlkeeper Database Bootstrap'
\echo '----------------------------'
\echo

\set VERBOSITY terse
\set ON_ERROR_STOP true

do language plpgsql $$ begin


--
-- Clearup Database befor starting the bootstrap
--
raise notice '[+] Deleting possibly existing tables';
do $dropall$ begin
  drop table if exists
    -- Developer; Team related tables
    sysconf
    , developer
    , team
    , developer_team_relation
    -- Project related
    , project
    , project_stage
    -- Task related
    , task
    , task_comment
  ;

end $dropall$;

raise notice '[+] Creating sysconf schema and add migration stamp';
do $sysconf$ begin

  create table if not exists sysconf (
    id serial primary key
    , created time default now()
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
    , created time default now()
    , name text not null
    , email text not null unique check(email like '_%@__%.__%') -- Checks if E-Mail valid, not bulletproof
    , isChief boolean default FALSE
  );

  -- Team
  create table if not exists team (
    id serial primary key
    , created time default now()
    , name text not null unique
    , leader integer references developer (id)
  );

  -- Relation; Developers can be part of multiple teams
  create table if not exists developer_team_relation (
    id serial primary key
    , created time default now()
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
    , created time default now()
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
    , created time default now()
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
raise notice '[+] Creating task related schemas';
do $tasks$ begin

  create table if not exists task (
    id serial primary key
    , created time default now()
    , name text not null
    , description text not null
    , fullfilled time
    , project_stage integer not null references project_stage (id)
    -- Ensure that there are not tasks with the same name for the each project stage
    , constraint name_project_stage_unique unique (project_stage, name)
  );

  create table if not exists task_comment (
    id serial primary key
    , created time default now()
    , content text not null
    , developer integer not null references developer (id)
    , task integer not null references task (id)
  );

end $tasks$;

end$$