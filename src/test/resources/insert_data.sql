INSERT INTO bidlist (account, type, bidQuantity)
VALUES ('account 1', 'type 1', 50),
       ('account 2', 'type 2', 100);

INSERT INTO curvepoint (CurveId, term, value)
VALUES (1, 1.5, 8),
       (2, 2.8, 40);

INSERT INTO rating (moodysRating, sandPRating, fitchRating, orderNumber)
VALUES ('moodys 1', 'sand 1', 'fitch 1', 1),
       ('moodys 2', 'sand 2', 'fitch 2', 2);

INSERT INTO rulename (name, description, json, template, sqlStr, sqlPart)
VALUES ('name 1', 'description 1', 'json 1', 'template 1', 'sql 1', 'sql part 1'),
       ('name 2', 'description 2', 'json 2', 'template 2', 'sql 2', 'sql part 2');

INSERT INTO trade (account, type, buyQuantity)
VALUES ('account 1', 'type 1', 1),
       ('account 2', 'type 2', 2);

INSERT INTO users (Id, username, password, fullname, role)
VALUES (1, 'user1', 'passwd', 'User 1', 'USER'),
       (2, 'user2', 'passwd', 'User 2', 'USER');