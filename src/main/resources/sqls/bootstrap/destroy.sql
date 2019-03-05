\set VERBOSITY terse
\set ON_ERROR_STOP true

do language plpgsql $$ begin

--
-- Clearup Database befor starting the bootstrap
--
raise notice '[+] Destroying everything';
do $destroy$ begin
  drop schema if exists public cascade;
  create schema public;
end $destroy$;

end$$
