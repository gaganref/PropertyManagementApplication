INSERT INTO public.landlord (name, email_id, phone_no)
VALUES  ('Harry', 'harry52@gmail.com', '07772568478'),
        ('Rishi Sunak', 'rishisunak1@gmail.com', '07985689123'),
        ('Daina Williams', 'dianaduke@gmail.com', '07778889999');

INSERT INTO public.address (flat_no, house_no, street, city, postcode)
VALUES (27, 2, 'Charles Street', 'Leicester', 'LE00GS'),
       (null, 44, 'Cambridge Street', 'Glassgow', 'GE14GE'),
       (8, 62, 'Stanford Road', 'Tottenham', 'TE44DD'),
       (1, 4, 'Hogwards Street', 'Berlin', 'HS02BD'),
       (null, 69, 'Buckingham Road', 'New Castle', 'NC12BR');

INSERT INTO public.house (landlord, address, no_of_rooms, pppm)
VALUES  ((SELECT landlord_id from public.landlord WHERE email_id='rishisunak1@gmail.com'),
         (SELECT address_id from public.address WHERE postcode='LE00GS'), 5, 300),
        ((SELECT landlord_id from public.landlord WHERE email_id='dianaduke@gmail.com'),
         (SELECT address_id from public.address WHERE postcode='GE14GE'), 5, 350),
        ((SELECT landlord_id from public.landlord WHERE email_id='dianaduke@gmail.com'),
         (SELECT address_id from public.address WHERE postcode='TE44DD'), 1, 650),
        ((SELECT landlord_id from public.landlord WHERE email_id='rishisunak1@gmail.com'),
         (SELECT address_id from public.address WHERE postcode='HS02BD'), 2, 400),
        ((SELECT landlord_id from public.landlord WHERE email_id='harry52@gmail.com'),
         (SELECT address_id from public.address WHERE postcode='NC12BR'), 4, 275);