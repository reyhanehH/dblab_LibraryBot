create database db_lab;
use db_lab;

create table age (
ageID int ,
ageBounds varchar(100)  ,
 primary key (ageID)
);


create table bookSubject (
subjectID int ,
subjectBounds varchar(100)  ,
 primary key (subjectID)
);



create table publisher (
publisherID int ,
publisherName varchar(100)  ,
 primary key (publisherID)
);


create table book (
bookID int ,
bookName varchar(200),
writer varchar(200) ,
subjectID int ,
price int ,
ageID int ,
editionYear int ,
publisherID int ,
foreign key (subjectID) References bookSubject(subjectID) ,
foreign key (ageID) References  age(ageID) ,
foreign key (publisherID) References  publisher(publisherID) 
);



insert into age (ageID, ageBounds) values (1 , '1_2 years') ;
insert into age (ageID, ageBounds) values (2 , '2_5 years') ;
insert into age (ageID, ageBounds) values (3 , '5_7 years') ;
insert into age (ageID, ageBounds) values (4 , '7_12 years') ;
insert into age (ageID, ageBounds) values (5 , '12_18 years') ;
insert into age (ageID, ageBounds) values (6 , '18_30 years') ;
insert into age (ageID, ageBounds) values (7 , 'over 30') ;
insert into age (ageID, ageBounds) values (8 , 'every body') ;


insert into bookSubject(subjectID, subjectBounds) values (1 , 'اخلاق') ;
insert into bookSubject(subjectID, subjectBounds) values (2 , 'ادبی') ;
insert into bookSubject(subjectID, subjectBounds) values (3 , 'ایران شناسی') ;
insert into bookSubject(subjectID, subjectBounds) values (4 , 'اقتصادی') ;
insert into bookSubject(subjectID, subjectBounds) values (5 , 'پزشکی') ;
insert into bookSubject(subjectID, subjectBounds) values (6 , 'تاریخی') ;
insert into bookSubject(subjectID, subjectBounds) values (7 , 'تخیلی') ;
insert into bookSubject(subjectID, subjectBounds) values (8 , 'جنایی') ;
insert into bookSubject(subjectID, subjectBounds) values (9 , 'جغرافیایی') ;
insert into bookSubject(subjectID, subjectBounds) values (10 , 'حقوقی') ;
insert into bookSubject(subjectID, subjectBounds) values (11 , 'خاطرات') ;
insert into bookSubject(subjectID, subjectBounds) values (12 , 'داستانی') ;
insert into bookSubject(subjectID, subjectBounds) values (13 , 'دفاع مقدس') ;
insert into bookSubject(subjectID, subjectBounds) values (14 , 'دینی') ;
insert into bookSubject(subjectID, subjectBounds) values (15 , 'روان شناسی') ;
insert into bookSubject(subjectID, subjectBounds) values (16 , 'رمان') ;
insert into bookSubject(subjectID, subjectBounds) values (17 , 'زندگی نامه') ;
insert into bookSubject(subjectID, subjectBounds) values (18 , 'سیاسی') ;
insert into bookSubject(subjectID, subjectBounds) values (19 , 'شعر') ;
insert into bookSubject(subjectID, subjectBounds) values (20 , 'طنز') ;
insert into bookSubject(subjectID, subjectBounds) values (21 , 'علمی') ;
insert into bookSubject(subjectID, subjectBounds) values (22 , 'عشق') ;
insert into bookSubject(subjectID, subjectBounds) values (23 , 'فلسفی') ;
insert into bookSubject(subjectID, subjectBounds) values (24 , 'فرهنگ نامه') ;
insert into bookSubject(subjectID, subjectBounds) values (25 , 'کودکان') ;
insert into bookSubject(subjectID, subjectBounds) values (26 , 'مهندسی') ;
insert into bookSubject(subjectID, subjectBounds) values (27 , 'هنری') ;

insert into publisher(publisherID, publisherName) values (1 , 'افق') ;
insert into publisher(publisherID, publisherName) values (2 , 'اختران') ;
insert into publisher(publisherID, publisherName) values (3 , 'امیر کبیر') ;
insert into publisher(publisherID, publisherName) values (4 , 'انقلاب اسلامی') ;
insert into publisher(publisherID, publisherName) values (5 , 'توس') ;
insert into publisher(publisherID, publisherName) values (6 , 'خوارزمی') ;
insert into publisher(publisherID, publisherName) values (7 , 'دانشگاه تهران') ;
insert into publisher(publisherID, publisherName) values (8 , 'فرهنگ اسلامی') ;
insert into publisher(publisherID, publisherName) values (9 , 'ستوده') ;
insert into publisher(publisherID, publisherName) values (10 , 'طرح نو') ;
insert into publisher(publisherID, publisherName) values (11 , 'کاروان') ;
insert into publisher(publisherID, publisherName) values (12 , 'مروارید') ;
insert into publisher(publisherID, publisherName) values (13 , 'گام نو') ;
insert into publisher(publisherID, publisherName) values (14 , 'سمت') ;
insert into publisher(publisherID, publisherName) values (15 , 'کتاب سرا') ;
insert into publisher(publisherID, publisherName) values (16 , 'چشمه') ;
insert into publisher(publisherID, publisherName) values (17 , 'مدرسه') ;
insert into publisher(publisherID, publisherName) values (18 , 'نشر دانشگاهی') ;
insert into publisher(publisherID, publisherName) values (19 , 'نشر نی') ;
insert into publisher(publisherID, publisherName) values (20 , 'نشر مرکز') ;
insert into publisher(publisherID, publisherName) values (21 , 'نصیر') ;
insert into publisher(publisherID, publisherName) values (22 , 'کانون پرورش فکری') ;

