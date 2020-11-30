-- SEQUENCE: public.empresa_id_seq
CREATE SEQUENCE public.empresa_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.empresa_id_seq
    OWNER TO postgres;

-- Table: public.empresa
CREATE TABLE public.empresa
(
    id bigint NOT NULL DEFAULT nextval('empresa_id_seq'::regclass),
    cnpj character varying(255) COLLATE pg_catalog."default" NOT NULL,
    razao_social character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT empresa_pkey PRIMARY KEY (id)
);