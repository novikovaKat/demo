CREATE TABLE IF NOT EXISTS "doctor_specialty" (
    "doctor_id" UUID NOT NULL,
    "specialty_id" UUID NOT NULL,
    CONSTRAINT "Relationship_5" FOREIGN KEY ("doctor_id") REFERENCES "doctor" ("uuid"),
    CONSTRAINT "Relationship_6" FOREIGN KEY ("specialty_id") REFERENCES "specialty" ("uuid")
);