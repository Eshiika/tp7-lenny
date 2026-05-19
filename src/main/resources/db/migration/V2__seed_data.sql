INSERT INTO users (first_name, last_name, email, password, role) VALUES
    ('Léo', 'Martin', 'leo.martin@gmail.com', '$2a$12$hm1adIfn3GGLrURiWco4HOFTN2cx1aB5wtQo5MQaDHCh9/WmcSr.O', 'ADMIN'),
    ('Anna', 'Laurent', 'anna.laurent@gmail.com', '$2a$12$hm1adIfn3GGLrURiWco4HOFTN2cx1aB5wtQo5MQaDHCh9/WmcSr.O', 'USER');

INSERT INTO directors (first_name, last_name, nationality, birth_date) VALUES
    ('Lucas', 'Richard', 'Anglais', '1972-02-10'),
    ('Enzo', 'Bernard', 'Français', '1984-06-20'),
    ('Camille', 'Simon', 'Espagnol', '2000-03-19');

INSERT INTO movies (title, release_year, duration_minutes, genre, synopsis, director_id) VALUES
     ('L''Ombre du Passé', 2010, 118, 'Drame', 'Un homme tente de reconstruire sa vie après un événement tragique.', 1),
     ('Dernière Traversée', 2018, 102, 'Aventure', 'Une expédition en mer tourne au drame.', 2),
     ('Le Silence des Collines', 2022, 95, 'Thriller', 'Dans un village isolé, une disparition inquiète les habitants.', 3),
     ('Au-delà des Rêves', 2021, 105, 'Fantastique', 'Une jeune femme découvre un monde caché derrière ses rêves.', 3),
     ('Route 47', 2016, 115, 'Drame', 'Un road trip qui va changer la vie de deux inconnus.', 2);