DROP DATABASE IF EXISTS javaPro;
CREATE DATABASE javaPro DEFAULT CHARSET=utf8;

use javaPro;

DROP TABLE IF EXISTS room;

CREATE TABLE room (
	rName VARCHAR(45) NOT NULL,
	category VARCHAR(45),
	building VARCHAR(45),
	PRIMARY KEY (rName)
);

insert into room values('biotechnology61_bathroom', 'bathroom', 'biotechnology61');
insert into room values("industryCoopCenter_bathroom","bathroom",  "industryCoopCenter");
insert into room values("engineeringBuilding2-27_bathroom","bathroom","engineeringBuilding2-27");
insert into room values("engineeringBuilding2-26_bathroom","bathroom","engineeringBuilding2-26");
insert into room values("engineeringBuilding2-23_bathroom","bathroom","engineeringBuilding2-23");
insert into room values("engineeringBuilding2-21_bathroom","bathroom","engineeringBuilding2-21");
insert into room values("engineeringPracticeBuildingA_bathroom","bathroom","engineeringPracticeBuildingA");
insert into room values("samsungLibrary_bathroom","bathroom","samsungLibrary");
insert into room values("researchBuilding2_bathroom","bathroom","researchBuilding2");
insert into room values("researchBuilding1_bathroom","bathroom","researchBuilding1");
insert into room values("pharmacy_bathroom","bathroom","pharmacy");
insert into room values("auditorium_bathroom","bathroom","auditorium");
insert into room values ( "medicalCenter_bathroom", "bathroom", "medicalCenter");
insert into room values ( "gym_bathroom", "bathroom", "gym");
insert into room values    ( "scienceHall2_bathroom", "bathroom", "scienceHall2");
insert into room values    ( "studentHall_bathroom", "bathroom", "studentHall");
insert into room values    ( "selfareHall_bathroom", "bathroom", "selfareHall");
insert into room values    ( "susungHall_bathroom", "bathroom", "susungHall");
insert into room values   ( "chemicalBuilding_bathroom", "bathroom", "chemicalBuilding");
insert into room values   ( "nCenter_bathroom", "bathroom", "nCenter");
insert into room values   ( "dormShin2_UndergroundRestaurant", "restaurant", "dormShin2");
insert into room values   ( "dormShin2_DominoPizza", "restaurant", "dormShin2");
insert into room values   ( "dormUi_Restaurant", "restaurant", "dormUi");
insert into room values   ( "dormGi_Restaurant", "restaurant", "dormGi");
insert into room values   ( "selfareHall_FacultyRestaurant", "restaurant", "selfareHall");
insert into room values    ( "studentHall_Restaurant", "restaurant", "studentHall");
insert into room values   ( "engineeringBuilding2_Restaurant", "restaurant", "engineeringBuilding2-26");
insert into room values   ( "studentHall_Subway", "restaurant", "studentHall");
insert into room values  ( "medicalCenter_femaleStudentLounge", "femaleStudentLounge", "medicalCenter");
insert into room values  ( "pharmacy_femaleStudentLounge", "femaleStudentLounge", "pharmacy");
insert into room values   ( "scienceHall2_femaleStudentLounge", "femaleStudentLounge", "scienceHall2");
insert into room values  ( "biotechnology_femaleStudentLounge", "femaleStudentLounge", "biotechnology");
insert into room values  ( "engineeringBuilding2_femaleStudentLounge", "femaleStudentLounge", "engineeringBuilding2-26");
insert into room values  ( "studentHall_maleStudentLounge", "maleStudentLounge", "studentHall");
insert into room values  ( "dormShin2_UndergroundConvenienceStore", "convenienceStore", "dormShin2");
insert into room values  ( "selfareHall_ConvenienceStore", "convenienceStore", "selfareHall");
insert into room values  ( "dormUi_ConvenienceStore", "convenienceStore", "dormUi");
insert into room values  ( "industryCoopCenter_Dorosicafe", "cafe", "industryCoopCenter");
insert into room values  ( "engineeringBuilding1_NUcafe", "cafe", "engineeringBuilding1-23");
insert into room values  ( "nCenter_CoffeeBeancafe", "cafe", "nCenter");
insert into room values  ( "dormUi_Namucafe", "cafe", "dormUi");
insert into room values  ( "selfareHall_disabledToilets", "disabledToilets", "selfareHall");
insert into room values ( "susungHall_disabledToilets", "disabledToilets", "susungHall");
insert into room values  ( "engineeringBuilding1-21_disabledToilets", "disabledToilets", "engineeringBuilding1-21");
insert into room values  ( "engineeringBuilding1-23_disabledToilets", "disabledToilets", "engineeringBuilding1-23");
insert into room values  ( "engineeringBuilding2-26_disabledToilets", "disabledToilets", "engineeringBuilding2-26");
insert into room values ( "engineeringBuilding2-27_disabledToilets", "disabledToilets", "engineeringBuilding2-27");
insert into room values  ( "chemicalBuilding_disabledToilets", "disabledToilets", "chemicalBuilding");
insert into room values  ( "samsungLibrary_disabledToilets", "disabledToilets", "samsungLibrary");
insert into room values  ( "pharmacy_disabledToilets", "disabledToilets", "pharmacy");
insert into room values ("biotechnology61_disabledToilets", "disabledToilets", "biotechnology61");
insert into room values ( "gym_disabledToilets", "disabledToilets", "gym");
insert into room values ("researchBuilding1_disabledToilets", "disabledToilets", "researchBuilding1");
insert into room values ( "researchBuilding2_disabledToilets", "disabledToilets", "researchBuilding2");
insert into room values   ("industryCoopCenter_disabledToilets", "disabledToilets", "industryCoopCenter");
insert into room values  ("nCenter_disabledToilets", "disabledToilets", "nCenter");
insert into room values  ( "dormGi_disabledToilets", "disabledToilets", "dormGi");
insert into room values  ("dormShin_disabledToilets", "disabledToilets", "dormShin");