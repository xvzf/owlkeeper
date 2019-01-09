PSQL="PGPASSWORD=owlkeeper psql -h 127.0.0.1 owlkeeper owlkeeper"

sh:
	sh -c ${PSQL}

start:
	docker-compose up -d

stop:
	docker-compose down

bootstrap:
	sh -c ${PSQL} < ./sqls/bootstrap.sql

dummydata:
	sh -c ${PSQL} < ./sqls/dummydata.sql

debug:
	sh -c ${PSQL} < ./sqls/debugview.sql

clean: bootstrap dummydata debug