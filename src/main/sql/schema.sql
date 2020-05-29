CREATE TABLE products
(
  pro_id integer NOT NULL DEFAULT nextval('pro_seq'::regclass),
  pro_brand character varying(255) NOT NULL,
  pro_name character varying(255) NOT NULL,
  CONSTRAINT pro_pk PRIMARY KEY (pro_id),
  CONSTRAINT pro_uk UNIQUE (pro_brand, pro_name)
);

CREATE TABLE subjects
(
  sub_id integer NOT NULL DEFAULT nextval('sub_seq'::regclass),
  sub_first_name character varying(50),
  sub_last_name character varying(50),
  sub_company_title character varying(50),
  sub_address character varying(50) NOT NULL,
  sub_city character varying(50) NOT NULL,
  sub_county character varying(50) NOT NULL,
  sub_state character varying(50) NOT NULL,
  sub_country character varying(50) NOT NULL,
  sub_zip character varying(50) NOT NULL,
  sub_phone character varying(50) NOT NULL,
  sub_email character varying(50) NOT NULL,
  sub_web character varying(50) NOT NULL,
  sub_type "char" NOT NULL DEFAULT 'P'::"char",
  sub_gender "char",
  CONSTRAINT persons_pk PRIMARY KEY (sub_id),
  CONSTRAINT persons_ck CHECK (sub_type = ANY (ARRAY['P'::"char", 'C'::"char"])),
  CONSTRAINT persons_gender_ck CHECK (sub_gender = ANY (ARRAY['M'::"char", 'F'::"char"])),
  CONSTRAINT persons_type_ck CHECK (sub_type = 'P'::"char" AND sub_first_name IS NOT NULL AND sub_last_name IS NOT NULL AND sub_company_title IS NULL AND sub_gender IS NOT NULL OR sub_type = 'C'::"char" AND sub_first_name IS NULL AND sub_last_name IS NULL AND sub_company_title IS NOT NULL AND sub_gender IS NULL)
);

CREATE TABLE sales
(
  sal_pro_id integer NOT NULL,
  sal_time timestamp without time zone,
  sal_customer_sub_id integer NOT NULL,
  sal_cost double precision NOT NULL,
  sal_units integer NOT NULL,
  sal_id integer NOT NULL DEFAULT nextval('sal_seq'::regclass),
  sal_seller_sub_id integer NOT NULL,
  CONSTRAINT sal_pk PRIMARY KEY (sal_id),
  CONSTRAINT sal_customer_fk FOREIGN KEY (sal_customer_sub_id)
      REFERENCES subjects (sub_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT sal_pro_fk FOREIGN KEY (sal_pro_id)
      REFERENCES products (pro_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT sal_seller_fk FOREIGN KEY (sal_seller_sub_id)
      REFERENCES subjects (sub_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
