\echo
\echo '---------------------'
\echo 'Owlkeeper Test Table'
\echo '---------------------'
\echo

\set VERBOSITY terse
\set ON_ERROR_STOP false


create table if not exists testtable
(
  id      serial primary key,
  created timestamp default now(),
  content     text
);
insert into testtable (content)
values ('why');
