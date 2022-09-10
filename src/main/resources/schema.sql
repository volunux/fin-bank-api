drop table if exists customer cascade;
drop table if exists bank_account cascade;

create table if not exists customer (
	id bigserial NOT NULL,
	full_name VARCHAR(35) NOT NULL,
	phone_number VARCHAR(15) NOT NULL,
	email_address VARCHAR(50) NOT NULL,
	home_address VARCHAR(200) NOT NULL,
	date_of_birth DATE NOT NULL DEFAULT CURRENT_DATE,
	created_on TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_on TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
	primary key (id)
);

alter table customer
	add constraint uqk_email_address unique (email_address);
	
create table bank_account (
    id bigserial NOT NULL,
    account_number VARCHAR(255) NOT NULL,
    account_pin VARCHAR(255) NOT NULL,
    account_status VARCHAR(255) NOT NULL,
    account_type VARCHAR(255) NOT NULL,
    balance bigserial NOT NULL,
    interest_rate bigserial,
    last_time_accessed TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
	created_on TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_on TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    customer_id int8 NOT NULL,
    primary key (id)
)
