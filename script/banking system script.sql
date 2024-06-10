-- Database: BankingSystem

-- DROP DATABASE IF EXISTS "BankingSystem";

CREATE DATABASE "BankingSystem"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;


-- Table: public.accounts

-- DROP TABLE IF EXISTS public.accounts;

CREATE TABLE IF NOT EXISTS public.accounts
(
    account_id integer NOT NULL DEFAULT nextval('accounts_account_id_seq'::regclass),
    account_holder_name character varying(255) COLLATE pg_catalog."default",
    balance numeric,
    CONSTRAINT accounts_pkey PRIMARY KEY (account_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.accounts
    OWNER to postgres;


-- Table: public.transactions

-- DROP TABLE IF EXISTS public.transactions;

CREATE TABLE IF NOT EXISTS public.transactions
(
    transaction_id integer NOT NULL DEFAULT nextval('transactions_transaction_id_seq'::regclass),
    account_id integer,
    transaction_type character varying(50) COLLATE pg_catalog."default",
    amount numeric,
    "timestamp" timestamp without time zone,
    CONSTRAINT transactions_pkey PRIMARY KEY (transaction_id),
    CONSTRAINT transactions_account_id_fkey FOREIGN KEY (account_id)
        REFERENCES public.accounts (account_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT transactions_transaction_type_check CHECK (transaction_type::text = ANY (ARRAY['DEPOSIT'::character varying, 'WITHDRAWAL'::character varying, 'TRANSFER'::character varying]::text[]))
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.transactions
    OWNER to postgres;