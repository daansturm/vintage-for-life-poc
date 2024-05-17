CREATE DATABASE IF NOT EXISTS vintage_for_life_poc;

USE vintage_for_life_poc;


create table IF NOT EXISTS klanten(
    id bigint AUTO_INCREMENT primary key not null,
    voornaam varchar(255),
    tussenvoegsel varchar(45),
    achternaam varchar(255),
    telefoonnummer varchar(255)
);

create table IF NOT EXISTS product(
    id bigint AUTO_INCREMENT primary key not null,
    naam varchar(225),
    beschrijving varchar(4000),
    afmeting varchar(255),
    gewicht varchar(255)
);



create table IF NOT EXISTS gebruikers(
    id bigint AUTO_INCREMENT primary key,
    naam varchar(255),
    wachtwoord varchar(255),
    email varchar(255)
);

create table IF NOT EXISTS transporteur(
    id bigint AUTO_INCREMENT primary key,
    voornaam varchar(255),
    tussenvoegsel varchar(45),
    achternaam varchar(255),
    telefoonnummer varchar(255),
    voertuig varchar(255)
);

create table IF NOT EXISTS bestelling(
    id bigint AUTO_INCREMENT primary key,
    klant_id bigint not null,
    foreign key (klant_id) references klanten(id),
    status varchar(45),
    installatieservice boolean,
    straat varchar(255),
    huisnummer varchar(45),
    plaats varchar(255),
    postcode varchar(45),
    land varchar(45)
);



create table IF NOT EXISTS levering(
    id bigint AUTO_INCREMENT primary key,
    status varchar(45),
    bezorgdatum datetime,
    trackingscode varchar(255)
);

create table IF NOT EXISTS retour(
    id bigint AUTO_INCREMENT primary key,
    bestelling_id bigint,
    foreign key (bestelling_id) references bestelling(id),
    status varchar(45),
    reden varchar (255),
    opmerking varchar(4000),
    retourdatum datetime,
    straat varchar(255),
    huisnummer varchar(45),
    plaats varchar(255),
    postcode varchar(45),
    land varchar(45)
);

create table IF NOT EXISTS begin_eindadres
(
    id bigint AUTO_INCREMENT primary key not null,
    straat varchar(255),
    huisnummer varchar(45),
    plaats varchar(255),
    postcode varchar(45),
    land varchar(45)
);


create table IF NOT EXISTS route(
    id bigint AUTO_INCREMENT primary key,
    status varchar(45),
    datum datetime,
    priotisering varchar(255),
    beginadres bigint,
    foreign key (beginadres) references begin_eindadres(id),
    eindadres bigint,
    foreign key (eindadres) references begin_eindadres(id)
);

create table IF NOT EXISTS route_transporteur
(
    id bigint AUTO_INCREMENT primary key not null,
    route_id bigint not null,
    transporteur_id bigint not null,
    foreign key (route_id) references route(id),
    foreign key (transporteur_id) references transporteur(id)
);

create table IF NOT EXISTS bestelling_regels(
    id bigint AUTO_INCREMENT primary key not null,
    product_id bigint not null,
    bestelling_id bigint not null,
    foreign key (product_id) references product(id),
    foreign key (bestelling_id) references bestelling(id),
    aantal int(11)
);

create table IF NOT EXISTS bestelling_levering(
    id bigint AUTO_INCREMENT primary key,
    bestelling_id bigint not null,
    levering_id bigint not null,
    foreign key (bestelling_id) references bestelling(id),
    foreign key (levering_id) references levering(id)
);

create table IF NOT EXISTS retour_route(
    id bigint AUTO_INCREMENT primary key,
    retour_id bigint not null,
    route_id bigint not null,
    foreign key (retour_id) references retour(id),
    foreign key (route_id) references route(id)
);

create table IF NOT EXISTS levering_route(
    id bigint AUTO_INCREMENT primary key,
    levering_id bigint not null,
    route_id bigint not null,
    foreign key (levering_id) references levering(id),
    foreign key (route_id) references route(id)
);






/* CREATE TABLE IF NOT EXISTS user (
#    id INT AUTO_INCREMENT PRIMARY KEY,
#    name VARCHAR(255) NOT NULL,
#    password VARCHAR(255) NOT NULL,
#    email VARCHAR(255) NOT NULL,
#    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
#);*/

INSERT INTO gebruikers (naam, wachtwoord, email) VALUES ('admin', '$2a$10$abcdefghijklmnopqrstuu5Lo0g67CiD3M4RpN1BmBb4Crp5w7dbK', 'daansturm@gmail.com');
INSERT INTO gebruikers (naam, wachtwoord, email) VALUES ('admin', '$2a$10$abcdefghijklmnopqrstuu5Lo0g67CiD3M4RpN1BmBb4Crp5w7dbK', 'julianvisscher@hotmail.com');

insert into begin_eindadres(straat,huisnummer,plaats,postcode,land) Values ('Campus','2',  'Zwolle', '8017 CA', 'Nederland');
insert into klanten (id,voornaam, tussenvoegsel, achternaam, telefoonnummer) Values (1,'Daan', '', 'Sturm', '061729324');
insert into klanten (id,voornaam, tussenvoegsel, achternaam, telefoonnummer) Values (2,'Julian', '', 'Visscher', '061729324');
insert into klanten (id,voornaam, tussenvoegsel, achternaam, telefoonnummer) Values (3,'Justin', 'van der', 'Mei', '061729324');
insert into klanten (id,voornaam, tussenvoegsel, achternaam, telefoonnummer) Values (4,'Marco', '', 'Krop', '061729324');
insert into klanten (id,voornaam, tussenvoegsel, achternaam, telefoonnummer) Values (5,'Paul', '', 'Hiemstra', '061729324');

insert into product(id,naam, beschrijving, afmeting, gewicht) Values (1,'Vintage kast', 'Kleine maar zeer mooie vintage kast voor in de woonkamer', '80x150x150', '20kg'   );
insert into product(id,naam, beschrijving, afmeting, gewicht) Values (2,'Vintage stoel', 'Mooie vintage eetkamerstoel van licht hout! Ze is voor haar leeftijd nog in goede staat, maar het hout is hier en daar wel ietwat verweerd.', '43x42x77', '10kg'   );
insert into product(id,naam, beschrijving, afmeting, gewicht) Values (3,'Vintage tafel lamp', 'Mooie klassieke bureaulamp / tafellamp. Een tijdloos model vervaardigd uit bronskleurig metaal en groen glas.', '30x20x40', '2kg'   );
insert into product(id,naam, beschrijving, afmeting, gewicht) Values (4,'Vintage tafel', 'De Eettafel / Bartafel Crank 90 Warm Brown is een prachtig stuk meubilair dat zowel functioneel als esthetisch aantrekkelijk is.', '80x150x150', '50kg'   );


insert into bestelling(id, klant_id, status, installatieservice, straat, huisnummer, plaats, postcode, land) Values (1,1,'Betaald',false, 'Populierenstraat', '18', 'Kampen', '8266 BK', 'Nederland');
insert into bestelling(id, klant_id, status, installatieservice, straat, huisnummer, plaats, postcode, land) Values (2,2,'Betaald',false, 'De marke', '4', 'Lemelerveld', '8152 HM', 'Nederland');
insert into bestelling(id, klant_id, status, installatieservice, straat, huisnummer, plaats, postcode, land) Values (3,3,'Betaald',false, 'Grotestraat', '24D', 'Raalte', '8102 CE', 'Nederland');
insert into bestelling(id, klant_id, status, installatieservice, straat, huisnummer, plaats, postcode, land) Values (4,4,'Betaald',false, 'Dikkersstraat', '1', 'Ommen', '7731 DH', 'Nederland');
insert into bestelling(id, klant_id, status, installatieservice, straat, huisnummer, plaats, postcode, land) Values (5,4,'Betaald',false, 'Voorstraat', '156', 'Nederhorst Den Berg', '1394 CT', 'Nederland');
insert into bestelling(id, klant_id, status, installatieservice, straat, huisnummer, plaats, postcode, land) Values (6,1,'Betaald',false, 'Star Numanstraat', '34', 'Groningen', '9714 JH', 'Nederland');
insert into bestelling(id, klant_id, status, installatieservice, straat, huisnummer, plaats, postcode, land) Values (7,2,'Betaald',false, 'Bilderdijkhof', '12', 'Uithoorn', '1422 DV', 'Nederland');
insert into bestelling(id, klant_id, status, installatieservice, straat, huisnummer, plaats, postcode, land) Values (8,3,'Betaald',false, 'Uelserweg', '180', 'Tubbergen', '7651 KV', 'Nederland');


insert into bestelling_regels(id, product_id, bestelling_id, aantal) Values(1, 3, 1, 2);
insert into bestelling_regels(id, product_id, bestelling_id, aantal) Values(2, 1, 2, 1);
insert into bestelling_regels(id, product_id, bestelling_id, aantal) Values(3, 2, 3, 1);
insert into bestelling_regels(id, product_id, bestelling_id, aantal) Values(4, 2, 4, 2);
insert into bestelling_regels(id, product_id, bestelling_id, aantal) Values(5, 4, 5, 1);
insert into bestelling_regels(id, product_id, bestelling_id, aantal) Values(6, 2, 6, 1);
insert into bestelling_regels(id, product_id, bestelling_id, aantal) Values(7, 3, 7, 1);
insert into bestelling_regels(id, product_id, bestelling_id, aantal) Values(8, 1, 8, 1);

insert into levering (id, status, bezorgdatum) Values(1,'nieuw', '2024-05-07 08:34:09' );
insert into levering (id, status, bezorgdatum) Values(2,'nieuw', '2024-05-07 09:34:09' );
insert into levering (id, status, bezorgdatum) Values(3,'nieuw', '2024-05-07 10:34:09' );
insert into levering (id, status, bezorgdatum) Values(4,'nieuw', '2024-05-07 11:34:09' );
insert into levering (id, status, bezorgdatum) Values(5,'nieuw', '2024-05-07 12:34:09' );
insert into levering (id, status, bezorgdatum) Values(6,'nieuw', '2024-05-08 12:34:09' );
insert into levering (id, status, bezorgdatum) Values(7,'nieuw', '2024-05-08 12:34:09' );
insert into levering (id, status, bezorgdatum) Values(8,'nieuw', '2024-05-09 12:34:09' );

insert into retour(id, bestelling_id, status, reden, opmerking, retourdatum, straat, huisnummer, plaats, postcode, land ) Values(1, 1, 'nieuw', 'beschadigd', 'pootje afgebroken tijdens levering', '2024-05-09 12:34:09', 'Populierenstraat', '18', 'Kampen', '8266 BK', 'Nederland' );

insert into bestelling_levering( id, bestelling_id,levering_id) Values (1, 1,1);
insert into bestelling_levering( id, bestelling_id,levering_id) Values (2, 2,2);
insert into bestelling_levering( id, bestelling_id,levering_id) Values (3, 3,3);
insert into bestelling_levering( id, bestelling_id,levering_id) Values (4, 4,4);
insert into bestelling_levering( id, bestelling_id,levering_id) Values (5, 5,5);
insert into bestelling_levering( id, bestelling_id,levering_id) Values (6, 6,6);
insert into bestelling_levering( id, bestelling_id,levering_id) Values (7, 7,7);
insert into bestelling_levering( id, bestelling_id,levering_id) Values (8, 8,8);



