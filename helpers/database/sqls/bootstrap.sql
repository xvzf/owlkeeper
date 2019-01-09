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

do language plpgsql $$ declare
  exc_message text;
  exc_context text;
  exc_detail text;
begin

--
-- Clearup Database befor starting the bootstrap
--
raise notice '[+] Deleting possibly existing tables';
do $dropall$ begin
  drop table if exists
    developer
    , team
    , developer_team_relation;
end $dropall$;

--
-- User and team related schemas
--
raise notice '[+] creating developer and team related schemas';
do $developers$ begin

  -- Users
  create table if not exists developer (
    id serial primary key
    , name text not null
    , email text not null check(email like '_%@__%.__%') -- Checks if E-Mail valid, not bulletproof
    -- Constraints
    , constraint email_unique unique (email)
  );

  -- Team
  create table if not exists team (
    id serial primary key
    , name text not null
    , leader integer references developer (id)
    -- Constraints
    , constraint name_unique unique(name)
  );

  -- Relation; Developers can be part of multiple teams
  create table if not exists developer_team_relation (
    id serial primary key
    , developer integer not null references developer (id)
    , team integer not null references team (id)
    -- Constraints
    , constraint map_unique unique (developer, team)
  );

end $developers$;

--
-- Project related schemas
--
raise notice '[!] @TODO creating project related schemas';
do $projects$ begin
  -- @TODO
end $projects$;


end$$