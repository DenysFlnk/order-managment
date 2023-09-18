INSERT INTO users(user_name, user_password)
VALUES ('normal user','some_password'),
       ('admin', 'some_admin_password');

INSERT INTO user_role(user_id, role)
VALUES (1, 'USER'),
       (2, 'ADMIN'),
       (2, 'USER');

INSERT INTO translator(name, email, phone_number)
VALUES ('Jared', 'jared.ely@gmail.com', '+380692222111'),
       ('Mary', 'mary.smith@gmail.com', '+380698883122'),
	   ('Patricia', 'patricia.johnson@gmail.com', '+380692232111'),
	   ('Linda', 'linda.williams@gmail.com', '+380693562145'),
	   ('Barbara', 'barbara.jones@gmail.com', '+380622222444'),
	   ('Elizabeth', 'elizabeth.brown@gmail.com', '+380682782777'),
	   ('Jennifer', 'jennifer.davis@gmail.com', '+380692212345'),
	   ('Maria', 'maria.miller@gmail.com', '+380633232111'),
	   ('Susan', 'susan.wilson@gmail.com', '+380995552323'),
	   ('Margaret', 'margaret.moore@gmail.com', '+380986567878'),
	   ('Lisa', 'lisa.anderson@gmail.com', '+380334337689');

INSERT INTO language_rate(translator_id, language, common_rate, hard_rate, signs)
VALUES (1, 'ENGLISH', 180, 200, 1.0),
       (2, 'GERMAN', 200, 250, 1.0),
       (2, 'KOREAN', 400, 500, 1.5),
	   (3, 'ENGLISH', 170, 190, 1.0),
	   (3, 'GERMAN', 210, 220, 1.0),
	   (4, 'ENGLISH', 300, 310, 1.0),
	   (4, 'UKRAINIAN', 100, 130, 1.0),
	   (5, 'FRENCH', 330, 350, 1.5),
	   (5, 'ESTONIAN', 400, 420, 1.5),
	   (6, 'ITALIAN', 220, 250, 1.0),
	   (6, 'ENGLISH', 100, 120, 1.0),
	   (7, 'FRENCH', 250, 270, 1.0),
	   (8, 'ITALIAN', 400, 430, 1.5),
	   (9, 'ITALIAN', 330, 350, 1.0),
	   (10, 'POLISH', 150, 200, 1.0),
	   (11, 'POLISH', 200, 210, 1.0);

INSERT INTO orders(customer_name, customer_phone, customer_email, prepaid, surcharge, summary_cost, creation_date, delivery_date, note)
VALUES ('Nancy', '+380981233344', 'nancy.thomas@gmail.com', 600, -150, 450, '2023-02-15', '2023-02-17', 'check naming'),
       ('Karen', '+380978419375', 'karen.jackson@gmail.com', 2000, 100, 2100, '2023-02-18', '2023-02-25', 'important note'),
       ('Betty', '+380395520473', 'betty.white@gmail.com', 300, 1450, 1750, '2023-02-20', '2023-02-21', 'make coffee'),
	   ('Helen', '+380654584344', 'helen.harris@gmail.com', 2000, 350, 2350, '2023-02-21', '2023-02-22', ''),
	   ('Sandra', '+380981232946', 'sandra.martin@gmail.com', 1300, 0, 1300, '2023-02-21', '2023-02-24', ''),
	   ('Donna', '+380231231174', 'donna.thompson@gmail.com', 1800, -50, 1750, '2023-02-22', '2023-02-23', ''),
	   ('Carol', '+380991209844', 'carol.garcia@gmail.com', 850, 0, 850, '2023-02-22', '2023-02-24', 'print copies'),
	   ('Ruth', '+380981235332', 'ruth.martinez@gmail.com', 600, 0, 600, '2023-02-22', '2023-02-23', ''),
	   ('Sharon', '+380672638588', 'sharon.robinson@gmail.com', 850, 0, 850, '2023-02-23', '2023-02-28', ''),
	   ('Michelle', '+380980300293', 'michelle.clark@gmail.com', 300, 30, 330, '2023-02-23', '2023-02-24', 'send to email'),
	   ('Laura', '+380661232315', 'laura.rodriguez@gmail.com', 570, -50, 570, '2023-02-23', '2023-02-25', ''),
	   ('Sarah', '+380669638432', 'sarah.lewis@gmail.com', 300, 0, 300, '2023-02-24', '2023-02-26', ''),
	   ('Terri', '+380983333455', 'terri.vasquez@gmail.com', 350, 0, 350, '2023-02-24', '2023-02-25', ''),
	   ('Colleen', '+380451233344', 'colleen.burton@gmail.com', 300, 0, 300, '2023-02-25', '2023-02-27', ''),
	   ('Joy', '+380978676754', 'joy.george@gmail.com', 250, 0, 250, '2023-02-25', '2023-02-28', ''),
	   ('Jackie', '+380662300344', 'jackie.lynch@gmail.com', 250, 0, 250, '2023-02-26', '2023-03-01', 'deposite'),
	   ('Mark', '+380630049867', 'mark.rinehart@gmail.com', 1200, 0, 1200, '2023-02-26', '2023-03-01', ''),
	   ('Steven', '+380991233474', 'steven.curley@gmail.com', 1100, 0, 1100, '2023-02-26', '2023-03-02', '');

INSERT INTO documents(order_id, document_language, office_rate, signs_number, notarization, office_cost, translator_id,
                      translator_rate, translator_tax)
VALUES (1, 'ENGLISH', 300, 1.5, 0, 450, 1, '180/1000', 270),
       (2, 'GERMAN', 350, 1.0, 250, 600, 2, '200/1000', 200),
       (3, 'KOREAN', 500, 3.0, 250, 1750, 2, '400/1500', 800),
	   (4, 'GERMAN', 350, 1.0, 0, 350, 3, '210/1000', 210),
	   (5, 'ENGLISH', 300, 1.0, 0, 300, 6, '100/1000', 100),
	   (6, 'FRENCH', 400, 1.0, 0, 400, 5, '330/1500', 231),
	   (7, 'ENGLISH', 300, 1.0, 0, 300, 6, '100/1000', 100),
	   (7, 'ENGLISH', 300, 1.0, 250, 550, 6, '100/1000', 100),
	   (8, 'GERMAN', 350, 1.0, 250, 600, 3, '210/1000', 210),
	   (9, 'ENGLISH', 300, 1.0, 0, 300, 6, '100/1000', 100),
	   (9, 'ENGLISH', 300, 1.0, 250, 550, 6, '100/1000', 100),
	   (10, 'ITALIAN', 330, 1.0, 0, 330, 6, '220/1000', 220),
	   (11, 'ESTONIAN', 320, 1.0, 250, 570, 5, '400/1500', 280),
	   (12, 'ENGLISH', 300, 1.0, 0, 300, 6, '100/1000', 100),
	   (13, 'GERMAN', 350, 1.0, 0, 350, 3, '210/1000', 210),
	   (14, 'ENGLISH', 300, 1.0, 0, 300, 6, '100/1000', 100),
	   (15, 'POLISH', 250, 1.0, 0, 250, 10, '150/1000', 150),
	   (16, 'POLISH', 250, 1.0, 0, 250, 11, '200/1000', 200);

INSERT INTO documents(order_id, document_language, office_rate, office_cost)
VALUES (2, 'ENGLISH', 300, 450);

INSERT INTO apostille(order_id, title, submission_country, submission_department, apostille_cost)
VALUES (2, 'Apostille to Spain', 'Spain', 'ABC', 1500),
	   (4, 'Apostille to Germany', 'Germany', 'ABC', 2000),
	   (5, 'Apostille to Ukraine', 'Ukraine', 'ABC', 1000),
	   (6, 'Apostille to Spain', 'Spain', 'ABC', 1350),
	   (17, 'Apostille to Italy', 'Italy', 'ABC', 1200),
	   (18, 'Apostille to Latvia', 'Latvia', 'ABC', 1100);