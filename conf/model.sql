CREATE SCHEMA wods
  AUTHORIZATION lra;
  
CREATE SCHEMA users
  AUTHORIZATION lra;

CREATE TABLE wods.benchmark
(
  id serial primary key,
  name text,
  type text,
  description text,
  workout text,  
  CONSTRAINT wods_name_key UNIQUE (name )
)
WITH (
  OIDS=FALSE
);

insert into wods.benchmark (name, type, description, workout) values ()
  
CREATE TABLE wods.daily
(
	published timestamp NOT NULL,
	title text,
	description text,
	uri text,
	primary key (published)
) WITH (OIDS = FALSE)


CREATE TABLE wods.workoutlog
(
	id integer NOT null,
	date timestamp,
	name text,
	description text,
	handlestamp timestamp,
	replicated boolean,
	userid integer NOT null,
	foreign key (userid) references users.users(id) on delete cascade
) WITH (OIDS = FALSE)


CREATE TABLE users.users
(
	id serial primary key,
	name text not null,
	email text not null,
	password text not null,
	primary key(id),
	
) WITH (OIDS = FALSE)

ALTER TABLE wods.benchmark OWNER TO lra;
ALTER TABLE wods.daily OWNER TO lra;
ALTER TABLE wods.workoutlog OWNER TO lra;
ALTER TABLE users.users OWNER TO lra;
