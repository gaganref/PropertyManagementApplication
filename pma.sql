DROP TABLE IF EXISTS "House";
DROP TABLE IF EXISTS "Landlord";
DROP TABLE IF EXISTS "Tenant";
DROP TABLE IF EXISTS "TenancyInfo";
DROP TABLE IF EXISTS "Address";

-- Table Address Start
CREATE TABLE IF NOT EXISTS "Address"
(
    "Address_ID"
        integer
        NOT NULL
        GENERATED ALWAYS AS IDENTITY
            (
            INCREMENT 1
            START 10
            MINVALUE 10
            MAXVALUE 2147483647
            CACHE 1
            ),
    "Flat_NO"
        integer,
    "House_NO"
        integer
        NOT NULL,
    "Street"
        text
        NOT NULL,
    "City"
        text
        NOT NULL,
    "postcode"
        text
        NOT NULL,

    CONSTRAINT "Address_pkey"
    PRIMARY KEY ("Address_ID")
);

ALTER TABLE IF EXISTS "Address"
    OWNER to postgres;

-- Table Address Ends


-- Table Landlord starts
CREATE TABLE IF NOT EXISTS "Landlord"
(
    "Landlord_ID"
        integer
        NOT NULL
        GENERATED ALWAYS AS IDENTITY
            (
            INCREMENT 1
            START 10
            MINVALUE 10
            MAXVALUE 2147483647
            CACHE 1
            ),
    "Name"
        text
        NOT NULL,
    "Email_ID"
        text
        NOT NULL,
    "Phone_NO"
        text
        NOT NULL,

    CONSTRAINT "Landlord_pkey"
        PRIMARY KEY ("Landlord_ID")
);

ALTER TABLE IF EXISTS "Landlord"
    OWNER to postgres;

-- Table Landlord Ends

-- Table Tenant starts
CREATE TABLE IF NOT EXISTS "Tenant"
(
    "Tenant_ID"
        integer
        NOT NULL
        GENERATED ALWAYS AS IDENTITY
            (
            INCREMENT 1
            START 10
            MINVALUE 10
            MAXVALUE 2147483647
            CACHE 1
            ),

    "Name"
        text
        NOT NULL,
    "Email_ID"
        text
        NOT NULL,
    "Phone_NO"
        text
        NOT NULL,
    "Previous_Address"
        integer,

    CONSTRAINT "Tenant_pkey"
        PRIMARY KEY ("Tenant_ID"),

    CONSTRAINT "Tenant_Address_fkey"
        FOREIGN KEY ("Previous_Address")
            REFERENCES "Address" ("Address_ID")
);

ALTER TABLE IF EXISTS "Tenant"
    OWNER to postgres;

-- Table Tenant Ends


-- Table House Starts
CREATE TABLE IF NOT EXISTS "House"
(
    "House_ID"
        integer
        NOT NULL
        GENERATED ALWAYS AS IDENTITY
            (
            INCREMENT 1
            START 10
            MINVALUE 10
            MAXVALUE 2147483647
            CACHE 1
            ),
    "Landlord"
        integer
        NOT NULL,
    "Address"
        integer
        NOT NULL,
    "no_of_rooms"
        integer,
    "pppm"
        float4
        NOT NULL,

    CONSTRAINT "House_pkey"
    PRIMARY KEY ("House_ID"),

    CONSTRAINT "House_Landlord_fkey"
    FOREIGN KEY ("Landlord")
    REFERENCES "Landlord" ("Landlord_ID"),

    CONSTRAINT "House_Address_fkey"
    FOREIGN KEY ("Address")
    REFERENCES "Address" ("Address_ID")
);

ALTER TABLE IF EXISTS "House"
    OWNER to postgres;

-- Table House Ends



-- Table TenancyInfo starts
CREATE TABLE IF NOT EXISTS "TenancyInfo"
(
    "TenancyInfo_ID"
        integer
        NOT NULL
        GENERATED ALWAYS AS IDENTITY
            (
            INCREMENT 1
            START 10
            MINVALUE 10
            MAXVALUE 2147483647
            CACHE 1
            ),
    "House"
        integer
        NOT NULL,
    "Tenant"
        integer
        NOT NULL,
    "Start_Date"
        date
        NOT NULL,
    "End_Date"
        date
        NOT NULL,

    CONSTRAINT "TenancyInfo_pkey"
    PRIMARY KEY ("TenancyInfo_ID"),

    CONSTRAINT "TenancyInfo_House_fkey"
    FOREIGN KEY ("House")
    REFERENCES "House" ("House_ID"),

    CONSTRAINT "TenancyInfo_Tenant_fkey"
    FOREIGN KEY ("Tenant")
    REFERENCES "Tenant" ("Tenant_ID")
);

ALTER TABLE IF EXISTS "TenancyInfo"
    OWNER to postgres;

-- Table TenancyInfo Ends
