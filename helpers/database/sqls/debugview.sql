--------------------------------------
-- Dumps Database content to the shell
--------------------------------------

\echo '[Developers]'
select
  d.name as dev_name
  , d.email as dev_email
  , (t.leader = d.id) as leads_team
  , t.name as team_name
from developer as d
  join developer_team_relation as r on (r.developer = d.id)
  join team as t on (r.team = t.id)
group by d.id, t.id, r.id
order by t.name;