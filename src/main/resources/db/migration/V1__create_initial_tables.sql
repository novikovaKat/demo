
CREATE TABLE "account"
(
  "uuid" UUID NOT NULL,
  "first_name" Varchar(40) NOT NULL,
  "last_name" Varchar(40) NOT NULL,
  "email" Varchar(200) NOT NULL,
  "phone" Varchar(12)
);
ALTER TABLE "account" ADD CONSTRAINT "PK_account" PRIMARY KEY ("uuid");

CREATE TABLE "doctor"
(
  "account_id" UUID NOT NULL,
  "specialty" Varchar(50) NOT NULL,
  "uuid" UUID NOT NULL
);

ALTER TABLE "doctor" ADD CONSTRAINT "PK_doctor" PRIMARY KEY ("uuid");

CREATE TABLE "appointment"
(
  "uuid" UUID NOT NULL,
  "patient_id" UUID NOT NULL,
  "doctor_id" UUID NOT NULL,
  "appointment_date" Timestamptz NOT NULL,
  "status" Varchar(20) NOT NULL
);
ALTER TABLE "appointment" ADD CONSTRAINT "PK_appointment" PRIMARY KEY ("uuid");

ALTER TABLE "doctor"
  ADD CONSTRAINT "Relationship1"
    FOREIGN KEY ("account_id")
    REFERENCES "account" ("uuid");

ALTER TABLE "appointment"
  ADD CONSTRAINT "Relationship2"
    FOREIGN KEY ("patient_id")
    REFERENCES "account" ("uuid");

ALTER TABLE "appointment"
  ADD CONSTRAINT "Relationship3"
    FOREIGN KEY ("doctor_id")
    REFERENCES "doctor" ("uuid");
CREATE TABLE "offer"
(
    "uuid" UUID NOT NULL,
    "name" Varchar(200) NOT NULL,
    "price" NUMERIC(32) NOT NULL
);
ALTER TABLE "offer" ADD CONSTRAINT "PK_offer" PRIMARY KEY ("uuid");
