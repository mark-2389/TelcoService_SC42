use telcoservice_db;

SELECT *
FROM CLIENT;

CREATE TABLE service_package (
	ID int PRIMARY KEY auto_increment,
    NAME varchar(255) 
);


CREATE TABLE optional_product (
	ID int primary key auto_increment,
    NAME varchar(255),
    MONTHLY_FEE decimal(4,2) check(MONTHLY_FEE >= 0)
);


CREATE TABLE service (
	ID int primary key auto_increment,
    TYPE varchar(255)
);

CREATE TABLE InternetService (
	ID int primary key,
	ISMOBILE bit(1),
    GB int,
    GBFEE decimal(4,2),
    foreign key (ID)
		references service(ID)
			on update cascade
            on delete cascade
);

CREATE TABLE FixedPhoneService (
	ID int primary key,
	MINUTES int,
    SMS int,
    MINUTESFEE decimal(4,2),
    SMSFEE decimal(4,2),
    foreign key (ID)
		references service(ID)
			on update cascade
            on delete cascade
);


CREATE TABLE OrderOptionalComposition (
	ORDERID int,
    OPTIONALPRODUCTID int,
    primary key(ORDERID, OPTIONALPRODUCTID),
    foreign key (ORDERID)
		references telcoservice_db.order(ID)
			on update cascade
            on delete cascade,
	foreign key (OPTIONALPRODUCTID)
		references optional_product(ID)
			on update cascade
            on delete cascade
);


CREATE TABLE ServiceComposition (
	PACKAGEID int,
    SERVICEID int,
    primary key(PACKAGEID, SERVICEID),
    foreign key (PACKAGEID)
		references service_package(ID)
			on update cascade
            on delete cascade,
	foreign key (SERVICEID)
		references service(ID)
			on update cascade
            on delete cascade
);

ALTER TABLE telcoservice_db.order
	add column PACKAGEID date;

ALTER TABLE optional_product
	add column EXPIRATION_DATE date;
    
ALTER TABLE service_package
	add column EXPIRATION_DATE date;

ALTER TABLE validity
	add column EXPIRATION_DATE date;

ALTER TABLE service
	add column EXPIRATION_DATE date;

ALTER TABLE validity
	change column SERVICEID PACKAGEID
    int not null;

create trigger Expiration_Date_Consistency_Validity
before insert on validity
for each row
	SET new.EXPIRATION_DATE = coalesce ( ( SELECT SP.EXPIRATION_DATE
											FROM service_package SP
											WHERE   new.ID = SP.ID AND 
													new.EXPIRATION_DATE > SP.EXPIRATION_DATE ), new.EXPIRATION_DATE, SP.EXPIRATION_DATE);


create trigger Expiration_Date_Consistency_Package
before insert on servicecomposition
for each row
	update servicepackage SP1 SET SP1.EXPIRATION_DATE = coalesce ( (    SELECT min(S.EXPIRATION_DATE)
																		FROM service S JOIN servicecomposition SC on S.ID = SC.SERVICEID
																		WHERE   new.PACKAGEID = SP1.ID AND 
																				SP1.EXPIRATION_DATE > S.EXPIRATION_DATE ), SP1.EXPIRATION_DATE, S.EXPIRATION_DATE);

    
CREATE TABLE OptionalProductComposition (
	PACKAGEID int,
    OPTIONALPRODUCTID int,
    primary key(PACKAGEID, OPTIONALPRODUCTID),
    foreign key (PACKAGEID)
		references service_package(ID)
			on update cascade
            on delete cascade,
	foreign key (OPTIONALPRODUCTID)
		references Optional_Product(ID)
			on update cascade
            on delete cascade
);


CREATE TABLE ServiceSchedule (
	USERNAME varchar(255),
    SERVICEID int,
    ACTIVATIONDATE date,
    DEACTIVATIONDATE date,
    primary key(USERNAME, SERVICEID),
    foreign key (USERNAME)
		references telcoservice_db.client(USERNAME)
			on update cascade
            on delete cascade,
	foreign key (SERVICEID)
		references service(ID)
			on update cascade
            on delete cascade
);


CREATE TABLE OptionalsSchedule (
	USERNAME varchar(255),
    OPTIONALPRODUCTID int,
    ACTIVATIONDATE date,
    DEACTIVATIONDATE date,
    primary key(USERNAME, OPTIONALPRODUCTID),
    foreign key (USERNAME)
		references telcoservice_db.client(USERNAME)
			on update cascade
            on delete cascade,
	foreign key (OPTIONALPRODUCTID)
		references Optional_Product(ID)
			on update cascade
            on delete cascade
);


ALTER table telcoservice_db.order
	change column DateSubscription DATESUBSCRPTION date,
    change column TotalCost TOTALCOST decimal(5,2),
    change column hour HOURCREATION time default null,
    change column date DATECREATION date default null;
    
alter table optionalsschedule
	add constraint check ( deactivationDate > activationDate );

alter table serviceschedule
	add constraint check ( deactivationDate > activationDate );