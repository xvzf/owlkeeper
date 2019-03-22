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
do $fill$ begin

  -- (Dummy?) groups
  INSERT INTO group (description) values ('Developers');
  INSERT INTO group (description) values ('Team Leader');
  INSERT INTO group (description) values ('Project owner');

  --(Dummy?) Rights
  INSERT INTO right (permission, value) VALUES ('CREATE_PROJECT',1);
  INSERT INTO right (permission, value) VALUES ('DELETE_PROJECT',2);
  INSERT INTO right (permission, value) VALUES ('CREATE_TASK',4);
  INSERT INTO right (permission, value) VALUES ('VIEW_TASK',8);
  INSERT INTO right (permission, value) VALUES ('FULLFIL_TASK',16);
  INSERT INTO right (permission, value) VALUES ('RATE_TASK',32);
  INSERT INTO right (permission, value) VALUES ('ASSIGN_TEAM_TO_TASK',64);
  INSERT INTO right (permission, value) VALUES ('CREATE_USER',128);
  INSERT INTO right (permission, value) VALUES ('CREATE_TEAM',256);
  INSERT INTO right (permission, value) VALUES ('ADD_DEVELOPER_TO_TEAM',512); 

  -- Assigning rights to groups
  INSERT INTO group_right (groupId, rightId) VALUES (
    (SELECT id FROM group WHERE description = 'Project Owner'),
    (SELECT id FROM right WHERE permission LIKE '%PROJECT')
  );
  INSERT INTO group_right (groupId, rightId) VALUES (
    (SELECT id FROM group WHERE description = 'Team Leader'),
    (SELECT id FROM right WHERE permission IN ('CREATE_TASK',''))
  );

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
end $fill$;

raise notice '[+] Creating dummy project, project stages and tasks';
do $fill$ begin

  insert into project (name, description, type) values (
    'Testproject1',
    'Blubdidu description... what is that?',
    'waterfall'
  );

  insert into project (name, description, type) values (
    'Testproject2',
    'TestProject2 Dummy blubdidupdadalalaland',
    'spiral'
  );

  insert into project_stage (name, project, index) values (
    'Stage 1'
    , (select id from project where name = 'Testproject1')
    , 0
  );

  insert into project_stage (name, project, index) values (
    'Stage 2'
    , (select id from project where name = 'Testproject1')
    , 1
  );

  insert into task (name, description, deadline, project_stage, team) values (
    'Task 1'
    , 'Task 1 description'
    , (select now() + interval '7 days')
    , (select id from project_stage where name = 'Stage 1')
    , null
  );

  insert into task (name, description, deadline, project_stage, team) values (
    'Task 2'
    , 'Task 2 description'
    , (select now() + interval '7 days')
    , (select id from project_stage where name = 'Stage 1')
    , (select id from team where name = 'Team 1')
  );

  insert into task (name, description, deadline, fulfilled, project_stage, team) values (
    'Task 1'
    , 'Task 1 Stage 2 description'
    , (select now() + interval '7 days')
    , (select now() + interval '3 days')
    , (select id from project_stage where name = 'Stage 2')
    , (select id from team where name = 'Team 2')
  );

  insert into task (name, description, deadline, project_stage, team) values (
    'Task 2'
    , 'Task 2 Stage 2 description'
    , (select now() + interval '7 days')
    , (select id from project_stage where name = 'Stage 2')
    , null
  );

  insert into task_dependency (task, depends) values (
    (select id from task where description = 'Task 1 description')
    , (select id from task where description = 'Task 1 Stage 2 description')
  );

end $fill$;

end$$
