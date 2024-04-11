CREATE TABLE IF NOT EXISTS attendance (
    id SERIAL PRIMARY KEY,
    didAttend BOOLEAN DEFAULT FALSE,
    dateOfAttendance DATE NOT NULL,
    patient_id BIGINT REFERENCES patient(id) ON DELETE CASCADE
);