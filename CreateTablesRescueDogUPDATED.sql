-- DROP TABLES HERE
truncate table rescue_dog.dog_transactions;
drop table rescue_dog.dog_transactions;
truncate table rescue_dog.dog;
truncate table rescue_dog.trans_type;
drop table rescue_dog.dog;
drop table rescue_dog.trans_type;
-- CREATE TABLES HERE
create table rescue_dog.dog 
(dog_id int, dog_name char(30), dog_descr char(100),
constraint dog_pk primary key (dog_id));
create table rescue_dog.trans_type
(trans_type int, trans_name char(30), trans_descr char (100),
constraint trans_type_pk primary key (trans_type));
create table rescue_dog.dog_transactions
(trans_id int, dog_id int, trans_type int, latitude double, longitude double, data_text char(50), time_stamp datetime, 
constraint dog_trans_pk primary key (trans_id),
constraint dog_id_fk foreign key (dog_id) references dog(dog_id));
-- Insert Dummy Data Here
INSERT INTO rescue_dog.dog VALUES (1,'Fido','Brown with black spots, a total mutt');
INSERT INTO rescue_dog.trans_type VALUES (1,'SOUND','Input from a mic array');
INSERT INTO rescue_dog.trans_type VALUES (2,'BITE1','Bite plate 1');
INSERT INTO rescue_dog.trans_type VALUES (3,'BITE2','Bite plate 2');
INSERT INTO rescue_dog.trans_type VALUES (4,'PING','GPS heartbeat');
INSERT INTO rescue_dog.dog_transactions VALUES (0,1,1,61.188602, -149.825051,'Input from a mic array','2017-10-08 10:0:30');
INSERT INTO rescue_dog.dog_transactions VALUES (1,1,4,61.188608, -149.824951,'GPS heartbeat','2017-10-08 10:1:30');
INSERT INTO rescue_dog.dog_transactions VALUES (2,1,4,61.188613, -149.824851,'GPS heartbeat','2017-10-08 10:2:30');
INSERT INTO rescue_dog.dog_transactions VALUES (3,1,4,61.188618, -149.824751,'GPS heartbeat','2017-10-08 10:3:30');
INSERT INTO rescue_dog.dog_transactions VALUES (4,1,4,61.188623, -149.824651,'GPS heartbeat','2017-10-08 10:4:30');
INSERT INTO rescue_dog.dog_transactions VALUES (5,1,4,61.188628, -149.824551,'GPS heartbeat','2017-10-08 10:5:30');
INSERT INTO rescue_dog.dog_transactions VALUES (6,1,4,61.188633, -149.824451,'GPS heartbeat','2017-10-08 10:6:30');
INSERT INTO rescue_dog.dog_transactions VALUES (7,1,1,61.188638, -149.824351,'Input from a mic array','2017-10-08 10:7:30');
INSERT INTO rescue_dog.dog_transactions VALUES (8,1,4,61.188644, -149.824251,'GPS heartbeat','2017-10-08 10:8:30');
INSERT INTO rescue_dog.dog_transactions VALUES (9,1,4,61.188649, -149.824151,'GPS heartbeat','2017-10-08 10:9:30');
INSERT INTO rescue_dog.dog_transactions VALUES (10,1,4,61.188654, -149.824051,'GPS heartbeat','2017-10-08 10:10:30');
INSERT INTO rescue_dog.dog_transactions VALUES (11,1,2,61.188659, -149.823951,'Bite plate 1','2017-10-08 10:11:30');
INSERT INTO rescue_dog.dog_transactions VALUES (12,1,4,61.188666, -149.823851,'GPS heartbeat','2017-10-08 10:12:30');
INSERT INTO rescue_dog.dog_transactions VALUES (13,1,3,61.188671, -149.823751,'Bite plate 2','2017-10-08 10:13:30');
INSERT INTO rescue_dog.dog_transactions VALUES (14,1,1,61.188679, -149.823651,'Input from a mic array','2017-10-08 10:14:30');
INSERT INTO rescue_dog.dog_transactions VALUES (15,1,4,61.188685, -149.823551,'GPS heartbeat','2017-10-08 10:15:30');
INSERT INTO rescue_dog.dog_transactions VALUES (16,1,4,61.188690, -149.823451,'GPS heartbeat','2017-10-08 10:16:30');
INSERT INTO rescue_dog.dog_transactions VALUES (17,1,4,61.188695, -149.823351,'GPS heartbeat','2017-10-08 10:17:30');
INSERT INTO rescue_dog.dog_transactions VALUES (18,1,4,61.188700, -149.823251,'GPS heartbeat','2017-10-08 10:18:30');
INSERT INTO rescue_dog.dog_transactions VALUES (19,1,4,61.188705, -149.823151,'GPS heartbeat','2017-10-08 10:19:30');
INSERT INTO rescue_dog.dog_transactions VALUES (20,1,4,61.188710, -149.823051,'GPS heartbeat','2017-10-08 10:20:30');
INSERT INTO rescue_dog.dog_transactions VALUES (21,1,1,61.188715, -149.822951,'Input from a mic array','2017-10-08 10:21:30');
INSERT INTO rescue_dog.dog_transactions VALUES (22,1,2,61.188721, -149.822851,'Bite plate 1','2017-10-08 10:22:30');
INSERT INTO rescue_dog.dog_transactions VALUES (23,1,4,61.188728, -149.822751,'GPS heartbeat','2017-10-08 10:23:30');
INSERT INTO rescue_dog.dog_transactions VALUES (24,1,4,61.188733, -149.822651,'GPS heartbeat','2017-10-08 10:24:30');
INSERT INTO rescue_dog.dog_transactions VALUES (25,1,4,61.188738, -149.822551,'GPS heartbeat','2017-10-08 10:25:30');
INSERT INTO rescue_dog.dog_transactions VALUES (26,1,3,61.188743, -149.822451,'Bite plate 2','2017-10-08 10:26:30');
INSERT INTO rescue_dog.dog_transactions VALUES (27,1,4,61.188751, -149.822351,'GPS heartbeat','2017-10-08 10:27:30');
INSERT INTO rescue_dog.dog_transactions VALUES (28,1,1,61.188756, -149.822251,'Input from a mic array','2017-10-08 10:28:30');
INSERT INTO rescue_dog.dog_transactions VALUES (29,1,4,61.188762, -149.822151,'GPS heartbeat','2017-10-08 10:29:30');
INSERT INTO rescue_dog.dog_transactions VALUES (30,1,4,61.188767, -149.822051,'GPS heartbeat','2017-10-08 10:30:30');
INSERT INTO rescue_dog.dog_transactions VALUES (31,1,4,61.188772, -149.821951,'GPS heartbeat','2017-10-08 10:31:30');
INSERT INTO rescue_dog.dog_transactions VALUES (32,1,4,61.188777, -149.821851,'GPS heartbeat','2017-10-08 10:32:30');
INSERT INTO rescue_dog.dog_transactions VALUES (33,1,2,61.188782, -149.821751,'Bite plate 1','2017-10-08 10:33:30');
INSERT INTO rescue_dog.dog_transactions VALUES (34,1,4,61.188789, -149.821651,'GPS heartbeat','2017-10-08 10:34:30');
INSERT INTO rescue_dog.dog_transactions VALUES (35,1,1,61.188794, -149.821551,'Input from a mic array','2017-10-08 10:35:30');
INSERT INTO rescue_dog.dog_transactions VALUES (36,1,4,61.188800, -149.821451,'GPS heartbeat','2017-10-08 10:36:30');
INSERT INTO rescue_dog.dog_transactions VALUES (37,1,4,61.188805, -149.821351,'GPS heartbeat','2017-10-08 10:37:30');
INSERT INTO rescue_dog.dog_transactions VALUES (38,1,4,61.188810, -149.821251,'GPS heartbeat','2017-10-08 10:38:30');
INSERT INTO rescue_dog.dog_transactions VALUES (39,1,3,61.188815, -149.821151,'Bite plate 2','2017-10-08 10:39:30');
INSERT INTO rescue_dog.dog_transactions VALUES (40,1,4,61.188823, -149.821051,'GPS heartbeat','2017-10-08 10:40:30');
INSERT INTO rescue_dog.dog_transactions VALUES (41,1,4,61.188828, -149.820951,'GPS heartbeat','2017-10-08 10:41:30');
INSERT INTO rescue_dog.dog_transactions VALUES (42,1,1,61.188833, -149.820851,'Input from a mic array','2017-10-08 10:42:30');
INSERT INTO rescue_dog.dog_transactions VALUES (43,1,4,61.188839, -149.820751,'GPS heartbeat','2017-10-08 10:43:30');
INSERT INTO rescue_dog.dog_transactions VALUES (44,1,2,61.188844, -149.820651,'Bite plate 1','2017-10-08 10:44:30');
INSERT INTO rescue_dog.dog_transactions VALUES (45,1,4,61.188851, -149.820551,'GPS heartbeat','2017-10-08 10:45:30');
INSERT INTO rescue_dog.dog_transactions VALUES (46,1,4,61.188856, -149.820451,'GPS heartbeat','2017-10-08 10:46:30');
INSERT INTO rescue_dog.dog_transactions VALUES (47,1,4,61.188861, -149.820351,'GPS heartbeat','2017-10-08 10:47:30');
INSERT INTO rescue_dog.dog_transactions VALUES (48,1,4,61.188866, -149.820251,'GPS heartbeat','2017-10-08 10:48:30');
INSERT INTO rescue_dog.dog_transactions VALUES (49,1,1,61.188871, -149.820151,'Input from a mic array','2017-10-08 10:49:30');
INSERT INTO rescue_dog.dog_transactions VALUES (50,1,4,61.188877, -149.820051,'GPS heartbeat','2017-10-08 10:50:30');
INSERT INTO rescue_dog.dog_transactions VALUES (51,1,4,61.188882, -149.819951,'GPS heartbeat','2017-10-08 10:51:30');
INSERT INTO rescue_dog.dog_transactions VALUES (52,1,3,61.188887, -149.819851,'Bite plate 2','2017-10-08 10:52:30');
INSERT INTO rescue_dog.dog_transactions VALUES (53,1,4,61.188895, -149.819751,'GPS heartbeat','2017-10-08 10:53:30');
INSERT INTO rescue_dog.dog_transactions VALUES (54,1,4,61.188900, -149.819651,'GPS heartbeat','2017-10-08 10:54:30');
INSERT INTO rescue_dog.dog_transactions VALUES (55,1,2,61.188905, -149.819551,'Bite plate 1','2017-10-08 10:55:30');
INSERT INTO rescue_dog.dog_transactions VALUES (56,1,1,61.188912, -149.819451,'Input from a mic array','2017-10-08 10:56:30');
INSERT INTO rescue_dog.dog_transactions VALUES (57,1,4,61.188918, -149.819351,'GPS heartbeat','2017-10-08 10:57:30');
INSERT INTO rescue_dog.dog_transactions VALUES (58,1,4,61.188923, -149.819251,'GPS heartbeat','2017-10-08 10:58:30');
INSERT INTO rescue_dog.dog_transactions VALUES (59,1,4,61.188928, -149.819151,'GPS heartbeat','2017-10-08 10:59:30');