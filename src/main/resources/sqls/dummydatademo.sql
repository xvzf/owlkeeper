------------------------------------------
-- Dummy Data for Owlkeeper, used for demo
------------------------------------------

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
  insert into "group" (name, description) values ('admin', 'Administrator - manages user groups and system parameters');
  insert into "group" (name, description) values ('project', 'Project owner - assign team to tasks, not allowed to create tasks');
  insert into "group" (name, description) values ('task', 'Create tasks in projects the user is belonging to');

  -- Dummy developers
  insert into developer (name, email, pw_hash) values ('Albert Altmann', 'aa@owlkeeper.de', '8d8f2628cfce1853efc0d027be4f6ce4'); -- pw: password-1
  insert into developer (name, email, pw_hash) values ('Benjamin Becker', 'bb@owlkeeper.de', '67b942f3e524ec767aa11e247ac01036'); -- pw: password-2
  insert into developer (name, email, pw_hash) values ('Christian Conradt', 'cc@owlkeeper.de', '4aee0e4712257a91f834006fccf16394'); -- pw: password-3
  insert into developer (name, email, pw_hash) values ('David Degenhardt', 'dd@owlkeeper.de', '19fba5a514718a1ff009945297c65ffb'); -- pw: password-4
  insert into developer (name, email, pw_hash) values ('Emil Euler', 'ee@owlkeeper.de', '69a966c10c28bc4a1f54939c72aeaa69'); -- pw: password-5
  insert into developer (name, email, pw_hash) values ('Fabian Frei', 'ff@owlkeeper.de', '4a2be5b9451851ded1bb67742ed13d89'); -- pw: password-6
  -- Dummy groups
  insert into developer_group_relation (developer, "group") values (
  (select id from developer where email = 'aa@owlkeeper.de')
  , (select id from "group" where name = 'admin')
  ); -- aa@owlkeeper.de -> admin

  insert into developer_group_relation (developer, "group") values (
  (select id from developer where email = 'bb@owlkeeper.de')
  , (select id from "group" where name = 'admin')
  ); -- bb@owlkeeper.de -> admin

  insert into developer_group_relation (developer, "group") values (
  (select id from developer where email = 'cc@owlkeeper.de')
  , (select id from "group" where name = 'admin')
  ); -- cc@owlkeeper.de -> admin

  insert into developer_group_relation (developer, "group") values (
  (select id from developer where email = 'dd@owlkeeper.de')
  , (select id from "group" where name = 'admin')
  ); -- dd@owlkeeper.de -> admin

  insert into developer_group_relation (developer, "group") values (
  (select id from developer where email = 'ee@owlkeeper.de')
  , (select id from "group" where name = 'admin')
  ); -- ee@owlkeeper.de -> admin

  -- Dummy teams
  insert into team (name, leader) values (
  'Team 1',
  (select id from developer where email = 'aa@owlkeeper.de')
                                           );
  insert into team (name, leader) values(
  'Team 2',
  (select id from developer where email = 'bb@owlkeeper.de')
  );


  -- Team relationships
  insert into developer_team_relation (team, developer) values (
  (select id from team where name = 'Team 1'),
  (select id from developer where email = 'aa@owlkeeper.de')
  );
  insert into developer_team_relation (team, developer) values (
  (select id from team where name = 'Team 1'),
  (select id from developer where email = 'bb@owlkeeper.de')
  );
  insert into developer_team_relation (team, developer) values (
  (select id from team where name = 'Team 1'),
  (select id from developer where email = 'cc@owlkeeper.de')
  );
  insert into developer_team_relation (team, developer) values (
  (select id from team where name = 'Team 2'),
  (select id from developer where email = 'dd@owlkeeper.de')
  );
  insert into developer_team_relation (team, developer) values (
  (select id from team where name = 'Team 2'),
  (select id from developer where email = 'ee@owlkeeper.de')
  );
end $fill$;


raise notice '[+] Creating dummy project, project stages and tasks';
  do $fill$
  begin

  insert into project (name, description, type) values (
    'Kochbuch'
    , 'Kochbuch in dem Rezepte gespeichert und bearbeitet werden'
    , 'waterfall'
  );

  insert into project (name, description, type) values (
    'Space Explorer'
    , 'Ein Survival Aufbauspiel im Weltraum'
    , 'spiral'
  );

  insert into project_stage (name, project, index) values (
    'Machbarkeitsstudie'
    , (select id from project where name = 'Kochbuch')
    , 0
  );

  insert into project_stage (name, project, index) values (
    'Anforderungsanalyse'
    , (select id from project where name = 'Kochbuch')
    , 1
  );

  insert into project_stage (name, project, index) values (
    'Systementwurf'
    , (select id from project where name = 'Kochbuch')
    , 2
  );

  insert into project_stage (name, project, index) values (
  'Idee und Konzeptentwicklung'
  , (select id from project where name = 'Space Explorer')
  , 3
  );

  insert into project_stage (name, project, index) values (
  'Produktion'
  , (select id from project where name = 'Space Explorer')
  , 4
  );

  insert into task (name, description, deadline, fulfilled, project_stage, team) values (
  'Leveldesign'
  , 'Design der Level und Missionen'
  , (select now() + interval '3 days')
  , (select now())
  , (select id from project_stage where name = 'Produktion' and project = 2)
  , (select id from team where name = 'Team 2')
                                                                                            );

  insert into task (name, description, deadline, project_stage, team) values (
  'Drehbuch'
  , 'Schreiben des Drehbuchs'
  , (select now() + interval '14 days')
  , (select id from project_stage where name = 'Produktion' and project = 2)
  , (select id from team where name = 'Team 2')
  );

  insert into task (name, description, deadline, project_stage, team) values (
  'Gegner'
  , 'Auswahl der moeglichen Gegnergruppen'
  , (select now() + interval '2 days')
  , (select id from project_stage where name = 'Idee und Konzeptentwicklung' and project = 2)
  , (select id from team where name = 'Team 1')
  );

  insert into task (name, description, deadline, project_stage, team) values (
  'Planeten'
  , 'Auswahl der moeglichen Planten'
  , (select now() + interval '2 days')
  , (select id from project_stage where name = 'Idee und Konzeptentwicklung' and project = 2)
  , (select id from team where name = 'Team 1')
                                                                                 );

  insert into task (name, description, deadline, fulfilled, project_stage, team) values (
  'Kostenschaetzung'
  , 'Abschaetzung der wahrscheinlichen Kosten'
  , (select now() + interval '4 days')
  , (select now())
  , (select id from project_stage where name = 'Machbarkeitsstudie' and project = 1)
  , (select id from team where name = 'Team 1')
  );

  insert into task (name, description, deadline, fulfilled, project_stage, team) values (
  'Lastenheft'
  , 'Erstellen des Lastenhefts'
  , (select now() + interval '6 days')
  , (select now())
  , (select id from project_stage where name = 'Machbarkeitsstudie'and project = 1)
  , (select id from team where name = 'Team 1')
  );

  insert into task (name, description, deadline, project_stage, team) values (
  'Projektplan'
  , 'Erstellen eines Projektplan'
  , (select now() + interval '6 days')
  , (select id from project_stage where name = 'Machbarkeitsstudie'and project = 1)
  , (select id from team where name = 'Team 1')
  );

  insert into task (name, description, deadline, project_stage, team) values (
  'Testfaelle'
  , 'Bestimmen der Testfaelle'
  , (select now() + interval '8 days')
  , (select id from project_stage where name = 'Anforderungsanalyse'and project = 1)
  , (select id from team where name = 'Team 2')
  );

  insert into task (name, description, deadline, project_stage, team) values (
  'Pflichtenheft'
  , 'Erstellen eines Pflichtenhefts'
  , (select now() + interval '14 days')
  , (select id from project_stage where name = 'Anforderungsanalyse'and project = 1)
  , (select id from team where name = 'Team 2')
  );

  insert into task (name, description, deadline, project_stage, team) values (
  'Bibliotheken'
  , 'Auswahl der Bibliotheken die verwendet werden'
  , (select now() + interval '20 days')
  , (select id from project_stage where name = 'Systementwurf'and project = 1)
  , (select id from team where name = 'Team 2')
  );

  insert into task_dependency (task, depends) values (
  (select id from task where description = 'Erstellen des Lastenhefts')
  , (select id from task where description = 'Abschaetzung der wahrscheinlichen Kosten')
  );

  insert into task_dependency (task, depends) values (
  (select id from task where description = 'Erstellen eines Projektplan')
  , (select id from task where description = 'Abschaetzung der wahrscheinlichen Kosten')
  );

  insert into task_dependency (task, depends) values (
  (select id from task where description = 'Erstellen eines Pflichtenhefts')
  , (select id from task where description = 'Bestimmen der Testfaelle')
  );

  insert into team_project_relation (team, project) values (
  (select id from team where name = 'Team 2')
  , (select id from project where name = 'Space Explorer')
  ) on conflict do nothing;

end $fill$;

  raise notice '[+] Creating task_comments';
  do $fill$
  begin

  insert into task_comment (content, developer, task) values (
  'Ist sogut wie fertig.'
  , (select id from developer where email = 'aa@owlkeeper.de')
  , (select id from task where id = 1)
  );

  insert into task_comment (content, developer, task) values (
  'Kann etwas länger dauern'
  , (select id from developer where email = 'dd@owlkeeper.de')
  , (select id from task where id = 2)
   );

  insert into task_comment (content, developer, task) values (
  'Brauche Hilfe'
  , (select id from developer where email = 'cc@owlkeeper.de')
  , (select id from task where id = 2)
  );

  insert into task_comment (content, developer, task) values (
  'ist fast fertig'
  , (select id from developer where email = 'cc@owlkeeper.de')
  , (select id from task where id = 3)
  );

  insert into task_comment (content, developer, task) values (
  'kann sein, dass wir nicht rechtzeitig fertig werden'
  , (select id from developer where email = 'ee@owlkeeper.de')
  , (select id from task where id = 4)
  );

end $fill$;

end$$
