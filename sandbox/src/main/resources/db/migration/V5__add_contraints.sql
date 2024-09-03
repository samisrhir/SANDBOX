alter table if exists portal_api
       add constraint FKc3jeowbytwyybtry2jkmxv391
       foreign key (module_id)
       references sa_module;
alter table if exists sa_module
       add constraint FKbiulhhw5v0khd2o3yybsv3p6c
       foreign key (solution_id)
       references sa_solution;
alter table if exists sa_product_release
       add constraint FKco8wgsdgg9e8apqj2w2tojld2
       foreign key (product_id)
       references sa_product;
alter table if exists sa_solution
       add constraint FK1l4wa87kft3w01nmxprhuflbr
       foreign key (saproduct_release_id)
       references sa_product_release;