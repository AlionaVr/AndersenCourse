CREATE TABLE coworkingSpace (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(100) NOT NULL,
    price INT NOT NULL CHECK (price >= 0),
    availability BOOLEAN DEFAULT TRUE
);

CREATE TABLE coworkingSpaceBooking (
    id SERIAL PRIMARY KEY,
    coworking_space_id INT NOT NULL,
    booking_details VARCHAR(255),
    booking_date TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (coworking_space_id) REFERENCES coworkingSpace(id)
);