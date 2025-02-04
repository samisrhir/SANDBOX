create table sa_module (
                           enabled boolean,
                           expanded boolean,
                           module_id integer generated by default as identity,
                           solution_id integer not null,
                           visible boolean,
                           creation_date timestamp(6) not null,
                           last_modified_date timestamp(6),
                           version bigint,
                           description varchar(255),
                           image_path varchar(255),
                           name varchar(255),
                           primary key (module_id)
);