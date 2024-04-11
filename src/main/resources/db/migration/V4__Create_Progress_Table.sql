CREATE TABLE IF NOT EXISTS progress (
    id SERIAL PRIMARY KEY,
    notes TEXT,
    patient_id BIGINT REFERENCES patient(id) ON DELETE CASCADE
);