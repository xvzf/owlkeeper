---------------------------------------------
-- Dummy Data for Owlkeeper, used for testing
---------------------------------------------

\set VERBOSITY terse
\set ON_ERROR_STOP true

do language plpgsql $$ declare
  exc_message text;
  exc_context text;
  exc_detail text;
begin

raise notice '[+] Creating dummy developers and teams';
do $filldevelopers$ begin

  -- Dummy developers
  insert into developer (name, email) values ('Developer 1', 'devel1@owlkeeper.de');
  insert into developer (name, email) values ('Developer 2', 'devel2@owlkeeper.de');
  insert into developer (name, email) values ('Developer 3', 'devel3@owlkeeper.de');
  insert into developer (name, email) values ('Developer 4', 'devel4@owlkeeper.de');
  insert into developer (name, email) values ('Developer 5', 'devel5@owlkeeper.de');

  -- Dummy teams
  insert into team (name, leader) values (
    'Team 1',
    (select id from developer where email = 'devel1@owlkeeper.de')
  );
  insert into team (name, leader) values(
    'Team 2',
    (select id from developer where email = 'devel4@owlkeeper.de')
  );

  -- Team relationships :-)
  insert into developer_team_relation (team, developer) values (
    (select id from team where name = 'Team 1'),
    (select id from developer where email = 'devel1@owlkeeper.de')
  );
  insert into developer_team_relation (team, developer) values (
    (select id from team where name = 'Team 1'),
    (select id from developer where email = 'devel2@owlkeeper.de')
  );
  insert into developer_team_relation (team, developer) values (
    (select id from team where name = 'Team 1'),
    (select id from developer where email = 'devel3@owlkeeper.de')
  );
  insert into developer_team_relation (team, developer) values (
    (select id from team where name = 'Team 2'),
    (select id from developer where email = 'devel3@owlkeeper.de')
  );
  insert into developer_team_relation (team, developer) values (
    (select id from team where name = 'Team 2'),
    (select id from developer where email = 'devel4@owlkeeper.de')
  );
end $filldevelopers$;


end$$