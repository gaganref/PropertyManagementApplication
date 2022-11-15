DROP TABLE IF EXISTS "house";
DROP TABLE IF EXISTS "landlord";
DROP TABLE IF EXISTS "tenant";
DROP TABLE IF EXISTS "tenancy_info";
DROP TABLE IF EXISTS "address";

-- Table Address Start
CREATE TABLE IF NOT EXISTS "address"
(
    "address_id"
        bigint
        NOT NULL
        GENERATED ALWAYS AS IDENTITY
            (
            INCREMENT 1
            START 10
            MINVALUE 10
            MAXVALUE 2147483647
            CACHE 1
            ),
    "flat_no"
        integer,
    "house_no"
        integer
        NOT NULL,
    "street"
        text
        NOT NULL,
    "city"
        text
        NOT NULL,
    "postcode"
        text
        NOT NULL,

    CONSTRAINT "address_pkey"
    PRIMARY KEY ("address_id")
);

ALTER TABLE IF EXISTS "address"
    OWNER to postgres;

-- Table Address Ends


-- Table Landlord starts
CREATE TABLE IF NOT EXISTS "landlord"
(
    "landlord_id"
        bigint
        NOT NULL
        GENERATED ALWAYS AS IDENTITY
            (
            INCREMENT 1
            START 10
            MINVALUE 10
            MAXVALUE 2147483647
            CACHE 1
            ),
    "name"
        text
        NOT NULL,
    "email_id"
        text
        NOT NULL,
    "phone_no"
        text
        NOT NULL,

    CONSTRAINT "landlord_pkey"
        PRIMARY KEY ("landlord_id")
);

ALTER TABLE IF EXISTS "landlord"
    OWNER to postgres;

-- Table Landlord Ends

-- Table Tenant starts
CREATE TABLE IF NOT EXISTS "tenant"
(
    "tenant_id"
        bigint
        NOT NULL
        GENERATED ALWAYS AS IDENTITY
            (
            INCREMENT 1
            START 10
            MINVALUE 10
            MAXVALUE 2147483647
            CACHE 1
            ),

    "name"
        text
        NOT NULL,
    "email_id"
        text
        NOT NULL,
    "phone_no"
        text
        NOT NULL,
    "previous_address"
        bigint,

    CONSTRAINT "tenant_pkey"
        PRIMARY KEY ("tenant_id"),

    CONSTRAINT "tenant_address_fkey"
        FOREIGN KEY ("previous_address")
            REFERENCES "address" ("address_id")
);

ALTER TABLE IF EXISTS "tenant"
    OWNER to postgres;

-- Table Tenant Ends


-- Table House Starts
CREATE TABLE IF NOT EXISTS "house"
(
    "house_id"
        bigint
        NOT NULL
        GENERATED ALWAYS AS IDENTITY
            (
            INCREMENT 1
            START 10
            MINVALUE 10
            MAXVALUE 2147483647
            CACHE 1
            ),
    "landlord"
        bigint
        NOT NULL,
    "address"
        bigint
        NOT NULL,
    "no_of_rooms"
        integer,
    "pppm"
        float4
        NOT NULL,

    CONSTRAINT "house_pkey"
    PRIMARY KEY ("house_id"),

    CONSTRAINT "house_landlord_fkey"
    FOREIGN KEY ("landlord")
    REFERENCES "landlord" ("landlord_id"),

    CONSTRAINT "house_address_fkey"
    FOREIGN KEY ("address")
    REFERENCES "address" ("address_id")
);

ALTER TABLE IF EXISTS "house"
    OWNER to postgres;

-- Table House Ends



-- Table TenancyInfo starts
CREATE TABLE IF NOT EXISTS "tenancy_info"
(
    "tenancy_info_id"
        bigint
        NOT NULL
        GENERATED ALWAYS AS IDENTITY
            (
            INCREMENT 1
            START 10
            MINVALUE 10
            MAXVALUE 2147483647
            CACHE 1
            ),
    "house"
        bigint
        NOT NULL,
    "tenant"
        bigint
        NOT NULL,
    "start_date"
        date
        NOT NULL,
    "end_date"
        date
        NOT NULL,

    CONSTRAINT "tenancy_info_pkey"
    PRIMARY KEY ("tenancy_info_id"),

    CONSTRAINT "tenancy_info_house_fkey"
    FOREIGN KEY ("house")
    REFERENCES "house" ("house_id"),

    CONSTRAINT "tenancy_info_tenant_fkey"
    FOREIGN KEY ("tenant")
    REFERENCES "tenant" ("tenant_id")
);

ALTER TABLE IF EXISTS "tenancy_info"
    OWNER to postgres;

-- Table TenancyInfo Ends
