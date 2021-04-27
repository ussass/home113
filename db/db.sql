CREATE DATABASE home113 ENCODING 'UTF-8';

CREATE TABLE public.recipes
(
    id integer NOT NULL DEFAULT nextval('id_seq'::regclass),
    recipename character varying(64) COLLATE pg_catalog."default" NOT NULL,
    category smallint NOT NULL,
    portion smallint NOT NULL,
    cookingtime integer NOT NULL,
    ingredients text COLLATE pg_catalog."default" NOT NULL,
    steps text COLLATE pg_catalog."default" NOT NULL,
    photos text COLLATE pg_catalog."default" NOT NULL,
    datecreate integer NOT NULL,
    CONSTRAINT recipes_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.recipes
    OWNER to postgres;