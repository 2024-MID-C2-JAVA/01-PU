INSERT INTO bank (id,code,name,address,phone,balance,is_active,creator_user_id,created_at,updater_user_id,updated_at) VALUES
	 (1,'2808f405-390e-463b-831b-01078b49d801','Santander','Uruguay','111111',0.000000000000000000000000000000,1,'SRO','2024-10-23 00:00:00',NULL,NULL);



INSERT INTO type_transaction (name,description) VALUES
	 ('Acreditar','Depositar Dinero'),
	 ('Extraer','Extraer Dinero'),
	 ('Transferencia','Trasferir Dinero');


INSERT INTO client (id,code,name,usuario,password,address,phone,bank_id,is_active,creator_user_id,created_at,updater_user_id,updated_at) VALUES
	 (1,'216c5b73-9176-11ef-b47e-58a0234444ef','Pablo','pabo.soria','123','prueba','123456',1,1,'SRO','2024-10-23 00:00:00',NULL,NULL),
	 (2,'216c5b73-9176-11ef-b47e-58a0234444eg','Juan','pabo.soria1','123','prueba','123456',1,1,'SRO','2024-10-23 00:00:00',NULL,NULL),
	 (3,'216c5b73-9176-11ef-b47e-58a0234444et','Pedro','pabo.soria2','123','prueba','123456',1,1,'SRO','2024-10-23 00:00:00',NULL,NULL),
	 (4,'216c5b73-9176-11ef-b47e-58a0234444rt','Yanet','pabo.soria3','123','prueba','123456',1,1,'SRO','2024-10-23 00:00:00',NULL,NULL),
	 (5,'216c5b73-9176-11ef-b47e-58a0234444uy','Pepe','pabo.soria4','123','prueba','123456',1,1,'SRO','2024-10-23 00:00:00',NULL,NULL);



INSERT INTO account (id,code,number,balance,pin,client_id,is_active,creator_user_id,created_at,updater_user_id,updated_at) VALUES
	 (1,'216c5b73-9176-11ef-b47e-58a0234444ef','123456789',1000.000000000000000000000000000000,'1234',1,1,'SRO','2024-10-23 00:00:00',NULL,NULL),
	 (2,'216c5b73-9176-11ef-b47e-58a0234444eg','1234567890',1000.000000000000000000000000000000,'1234',2,1,'SRO','2024-10-23 00:00:00',NULL,NULL),
	 (3,'216c5b73-9176-11ef-b47e-58a0234444et','1234567891',1000.000000000000000000000000000000,'1234',3,1,'SRO','2024-10-23 00:00:00',NULL,NULL);
