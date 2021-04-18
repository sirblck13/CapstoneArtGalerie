BEGIN TRANSACTION;


DROP TABLE IF EXISTS artist_dealer;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS art_pieces;
DROP TABLE IF EXISTS art_type;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS dealer;
DROP TABLE IF EXISTS management;
DROP TABLE IF EXISTS artist;

DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;


CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	email varchar(250) NOT NULL,
	
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

INSERT INTO users (username,password_hash,role, email) VALUES ('JeromeC','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'teamalphacohort13@gmail.com');
INSERT INTO users (username,password_hash,role, email) VALUES ('TrevorH','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 'teamalphacohort13@gmail.com');
INSERT INTO users (username,password_hash,role, email) VALUES ('LeyA','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ARTIST', 'teamalphacohort13@gmail.com');
INSERT INTO users (username,password_hash,role, email) VALUES ('JohnM','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_DEALER', 'teamalphacohort13@gmail.com');
INSERT INTO users (username,password_hash,role, email) VALUES ('FredQ','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_SELLER', 'teamalphacohort13@gmail.com');
INSERT INTO users (username,password_hash,role, email) VALUES ('RayL','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_SELLER', 'teamalphacohort13@gmail.com');


CREATE TABLE artist(
        artist_id serial primary key,
        user_id int,
        artist_name varchar(56) NOT NULL,
        is_seller boolean default false 
        
);

CREATE TABLE dealer(
        dealer_id serial primary key,
        user_id int NOT NULL,
        
        CONSTRAINT fk_dealer_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE management(
        manager_id serial primary key,
        user_id int NOT NULL,
        
        CONSTRAINT fk_management_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE customer(
        customer_id serial primary key,
        user_id int NOT NULL,
        
        CONSTRAINT fk_customer_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);


CREATE TABLE artist_dealer(
        artist_id int NOT NULL,
        dealer_id int NOT NULL,
        
        PRIMARY KEY (artist_id, dealer_id),
        
        CONSTRAINT fk_artist_dealer_artist_id FOREIGN KEY (artist_id) REFERENCES artist (artist_id),
        CONSTRAINT fk_artist_dealer_dealer_id FOREIGN KEY (dealer_id) REFERENCES dealer (dealer_id)
        
);

CREATE TABLE tags (
        tag_id serial primary key,
        tag varchar(100) not null
);

CREATE TABLE art_type (
        type_id serial primary key,
        art_type varchar(100) not null
);


CREATE TABLE art_pieces (
        art_id serial primary key,
        title varchar(250) not null,
        date_created date not null,
        price numeric not null,
        img_file_name varchar(250) not null,
        is_sold boolean default false,
        artist_id int not null,
        dealer_id int,
        override_fee numeric,
        override_commission numeric,
        has_override boolean default false,
        is_available boolean default false,
        tag_id int default 1,
        type_id int not null,
    
        
        CONSTRAINT fk_art_pieces_artist_id FOREIGN KEY (artist_id) REFERENCES artist (artist_id),
        CONSTRAINT fk_art_pieces_dealer_id FOREIGN KEY (dealer_id) REFERENCES dealer (dealer_id),
        CONSTRAINT fk_art_pieces_tag_id FOREIGN KEY (tag_id) REFERENCES tags (tag_id),
        CONSTRAINT fk_art_pieces_type_id FOREIGN KEY (type_id) REFERENCES art_type (type_id)
);

CREATE TABLE transactions (
        order_id serial primary key,
        customer_id int not null,
        art_id int not null,
        date_of_sale date not null,
        fee numeric not null,
        commission numeric not null,
        total_price numeric not null,
        
        CONSTRAINT fk_transactions_customer_id FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
        CONSTRAINT fk_transactions_art_id FOREIGN KEY (art_id) REFERENCES art_pieces (art_id)
);




DROP TABLE IF EXISTS fees;

CREATE TABLE fees (
        fee_id serial primary key,
        fee numeric default 0.0,
        commission numeric default 0.0

);

INSERT INTO fees (fee_id) VALUES (DEFAULT);


INSERT INTO tags (tag_id, tag) VALUES (DEFAULT, 'None');
INSERT INTO tags (tag_id, tag) VALUES (DEFAULT, 'Abstract');
INSERT INTO tags (tag_id, tag) VALUES (DEFAULT, 'Figurative');
INSERT INTO tags (tag_id, tag) VALUES (DEFAULT, 'Geometric');
INSERT INTO tags (tag_id, tag) VALUES (DEFAULT, 'Minimalist');
INSERT INTO tags (tag_id, tag) VALUES (DEFAULT, 'Nature');
INSERT INTO tags (tag_id, tag) VALUES (DEFAULT, 'Pop');
INSERT INTO tags (tag_id, tag) VALUES (DEFAULT, 'Surrealist');
INSERT INTO tags (tag_id, tag) VALUES (DEFAULT, 'Typography');
INSERT INTO tags (tag_id, tag) VALUES (DEFAULT, 'Urban');
INSERT INTO tags (tag_id, tag) VALUES (DEFAULT, 'Cubism');



INSERT INTO art_type (type_id, art_type) VALUES (DEFAULT, 'Drawing');
INSERT INTO art_type (type_id, art_type) VALUES (DEFAULT, 'Painting');
INSERT INTO art_type (type_id, art_type) VALUES (DEFAULT, 'Sculpture');
INSERT INTO art_type (type_id, art_type) VALUES (DEFAULT, 'Photography');
INSERT INTO art_type (type_id, art_type) VALUES (DEFAULT, 'Digital');

--DUMMY DATA

INSERT INTO customer (customer_id, user_id) VALUES (DEFAULT, 1);
INSERT INTO management (manager_id, user_id) VALUES (DEFAULT, 2);
INSERT INTO artist (artist_id, user_id, artist_name, is_seller) VALUES (DEFAULT, 3, 'LeyA', false );
INSERT INTO dealer (dealer_id, user_id) VALUES (DEFAULT, 4);



INSERT INTO artist (artist_id, artist_name) VALUES (DEFAULT, 'DeadArtist');
INSERT INTO artist (artist_id, user_id, artist_name, is_seller) VALUES (DEFAULT, 6, 'sellerArtist', true );


--dummy artpiece

--Set listed art to be for sale:
--UPDATE art_pieces SET is_available = true WHERE art_id = ?;

--Update is avail to false after buying on front end:
--UPDATE art_pieces SET is_available = false WHERE art_id = ?;

--Change sold date:
--UPDATE transactions SET date_of_sale = '2021-04-10' WHERE order_id = ?;




--UPDATE art_pieces SET  is_available = true WHERE art_id = 6;
--UPDATE art_pieces SET  is_sold = false WHERE art_id = 1;

--Set the Piechart for 3
--UPDATE transactions SET date_of_sale = '2021-01-01' WHERE order_id = 1;
--UPDATE transactions SET date_of_sale = '2021-03-14' WHERE order_id = 2;
--UPDATE transactions SET date_of_sale = '2021-04-06' WHERE order_id = 3; 
--DELETE FROM art_pieces;

select * from artist;
select * from users;

COMMIT TRANSACTION;

