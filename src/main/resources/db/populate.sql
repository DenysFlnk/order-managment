INSERT INTO users(user_name, user_password)
VALUES ('normal user','some_password'),
       ('admin', 'some_admin_password');

INSERT INTO user_role(user_id, role)
VALUES (1, 'USER'),
       (2, 'ADMIN');

INSERT INTO translator(name, email, phone_number)
VALUES ('English Translator', 'eng_trans@gmail.com', '+380692222111'),
       ('German/Korean Translator', 'deu_kor_trans@gmail.com', '+380698883122');

INSERT INTO language_rate(translator_id, language, common_rate, hard_rate, signs)
VALUES (1, 'ENGLISH', 180, 200, 1.0),
       (2, 'GERMAN', 200, 250, 1.0),
       (2, 'KOREAN', 400, 500, 1.5);

INSERT INTO orders(customer_name, customer_phone, customer_email, prepaid, creation_date, delivery_date, note)
VALUES ('Customer1', '+380981233344', 'customer_one@gmail.com', 600, '2023-02-15', '2023-02-17', 'check naming'),
       ('Customer2', '+380978419375', 'customer_two@gmail.com', 2000, '2023-02-18', '2023-02-25', '"important note"'),
       ('Customer3', '+380395520473', 'customer_three@gmail.com', 300, '2023-02-20', '2023-02-21', 'make coffee');

INSERT INTO documents(order_id, document_language, office_rate, signs_number, notarization, office_cost, translator_id,
                      translator_rate, translator_tax)
VALUES (1, 'ENGLISH', 300, 1.5, 0, 450, 1, '180/1000', 270),
       (2, 'GERMAN', 350, 1.0, 250, 600, 2, '200/1000', 200),
       (3, 'KOREAN', 500, 3.0, 250, 1750, 2, '400/1500', 800);

INSERT INTO apostille(order_id, title, submission_country, submission_department, apostille_cost)
VALUES (2, 'Apostille to Spain', 'Spain', 'ABC', 1500)