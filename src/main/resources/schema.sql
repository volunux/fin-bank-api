drop table if exists customer cascade;

create table if not exists customer (
	id bigserial not null,
	full_name VARCHAR(35) not null,
	phone_number varchar(15) not null,
	email_address varchar(50) not null,
	contact_address VARCHAR(200) not null,
	home_address VARCHAR(200) not null,
	date_of_birth DATE NOT NULL DEFAULT CURRENT_DATE,
	created_on TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_on TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
	primary key (id)
);

alter table customer
	add constraint uqk_email_address unique (email_address);