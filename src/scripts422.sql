CREATE TABLE persons
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    age SMALLINT,
    driving_license BOOLEAN,
    car_id SERIAL REFERENCES cars (id)
);

CREATE TABLE cars
(
    id    SERIAL PRIMARY KEY,
    brand VARCHAR(100),
    model VARCHAR(100),
    price INTEGER
);
