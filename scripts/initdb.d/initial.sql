CREATE TABLE json_test(id serial, info jsonb);
INSERT INTO json_test(info)
VALUES('{"a": 100, "b": "Hello"}'::jsonb);
INSERT INTO json_test(info)
VALUES('{"a": 200, "b": "World"}'::jsonb);