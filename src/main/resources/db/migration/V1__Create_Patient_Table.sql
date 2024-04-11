CREATE TABLE IF NOT EXISTS patient (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    lastName VARCHAR NOT NULL,
    dob DATE NOT NULL,
    contactInfo VARCHAR NOT NULL,
    nextAppointment DATE
);