alter table telcoservice_db.order
	add column ISVALID ENUM ("DEFAULT", "REJECTED", "ACCEPTED") default "DEFAULT",
    add column NUMBER_REJECTION int default 0 check (NUMBER_REJECTION >= 0);
    
alter table client
	add column NUMBER_REJECTION int default 0 check (NUMBER_REJECTION >= 0);
    
CREATE TABLE Auditing (
	USERNAME varchar(255),
    EMAIL varchar (127),
    AMOUNT decimal(6,2),
    REJECTION_TIME time,
    REJECTION_DATE date,
    ISACTIVE boolean,
    primary key (USERNAME, REJECTION_TIME, REJECTION_DATE),
     foreign key (USERNAME)
		references telcoservice_db.client(USERNAME)
			on update cascade
            on delete no action
);