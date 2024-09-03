create table sa_product_release (
                                    enabled boolean,
                                    product_id integer,
                                    product_release_id integer generated by default as identity,
                                    visible boolean,
                                    creation_date timestamp(6) not null,
                                    last_modified_date timestamp(6),
                                    version bigint,
                                    description varchar(255),
                                    name varchar(255),
                                    primary key (product_release_id)
);