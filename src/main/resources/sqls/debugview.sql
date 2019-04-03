--------------------------------------
-- Dumps Database content to the shell
--------------------------------------

\echo '[Users]'
select
  d.name as dev_name
  , d.email as dev_email
  , (t.leader = d.id) as leads_team
  , t.name as team_name
from developer as d
  join developer_team_relation as r on (r.developer = d.id)
  join team as t on (r.team = t.id)
order by t.name;

\echo '[Developer Groups]'
select
  d.email as email, g.name as "group"
from developer d
  left join developer_group_relation dg on d.id = dg.developer
  left join "group" g on dg.group = g.id
order by email;

\echo '[Projects, Project stages and tasks -> team]'
select
  p.name as project
  , ps.name as project_stage
  , t.id as task_uid
  , t.name as task_name
  , tm.name as assigned_team
from project as p
  left join project_stage as ps on p.id = ps.project
  left join task as t on t.project_stage = ps.id
  left join team as tm on t.team = tm.id
order by p.name;

\echo '[Task dependency]'
select
  task.id as task_uid
  , task.name as task
  , depends.id as depends_uid
  , depends.name as depends
from task_dependency as td
  join task  on td.task = task.id
  join task as depends on td.depends = depends.id
order by task.name;
