DELETE FROM statistique;
DELETE FROM player;
DELETE FROM agent;
DELETE FROM agenda;
DELETE FROM event;
DELETE FROM user;

INSERT INTO user (id, name, email, password, role)
VALUES
(1, 'Young Crack', 'youngcrackfr@gmail.com', '$2y$10$syAsesqkvLZLMQSjYVpeV.FFQbSFKWfP96CJVNruObUUiBPbSv5O.', 'AGENT'),
(2, 'John Doe', 'johndoe@gmail.com',
 '$2a$10$DowJonesRkCzVpqkHj0bW6Ou0RmM0i3T6y6lHykjGnQYQ4s/x0pXyW', 'AGENT'),
(3, 'Admin Root', 'admin@gmail.com',
 '$2a$10$DowJonesRkCzVpqkHj0bW6Ou0RmM0i3T6y6lHykjGnQYQ4s/x0pXyW', 'ADMIN'),
(4, 'Yusuf', 'karaca@gmail.com', '$2y$10$JmsoDDaXC8HLRHekdpnpdeWjBE9rAoYZO49Ki6PTBTEC4hPJyXb4O', 'AGENT');

INSERT INTO agent (id, user_id)
VALUES
(1, 1),
(2, 2),
(3,4);

INSERT INTO player (id, age, name, agent_id, email, access_code, club, image, nationality, poste)
VALUES
(1, 17, 'Mohamed Chebbi', 1, 'mohamedchebbi@gmail.com', '123456', 'Stade Rennais', 'https://fff.twic.pics/https://media.fff.fr/uploads/images/22a4f488807674ef24b4855146244b64.png?twic=v1/focus=226x193/cover=380x296', 'France', 'Défenseur'),
(2, 18, 'Ruben Lomet', 1, 'rubenlomet@gmail.com', '654321', 'Stade Rennais', 'https://fff.twic.pics/https://media.fff.fr/uploads/images/8118f1bdb97e01af3897bdb707818dc8.png?twic=v1/focus=237x151/cover=380x296', 'France', 'Défenseur'),
(3, 19, 'Karim Akabou', 3, 'karimakabou@gmail.com', '111222', 'PSG', 'https://fff.twic.pics/https://media.fff.fr/uploads/images/8118f1bdb97e01af3897bdb707818dc8.png?twic=v1/focus=237x151/cover=380x296', 'Algeria', 'Forward');

INSERT INTO statistique (id, player_id, saison, matchs_joues, buts, passes_decisives, cartons_jaunes, cartons_rouges, taille, poids)
VALUES
(1, 1, '2023/2024', 20, 0, 0, 1, 0, 185, 75),
(2, 2, '2023/2024', 25, 4, 7, 3, 0, 178, 70),
(3, 3, '2023/2024', 22, 12, 5, 2, 1, 182, 76);

INSERT INTO agenda (id, owner_id, color, owner_type)
VALUES
(1, 1, '#000000', 'AGENT'),
(5, 4, '#000000', 'AGENT'),
(2, 1, '#000000', 'PLAYER'),
(3, 2, '#FF0000', 'AGENT'),
(4, 3, '#00FF00', 'PLAYER');

INSERT INTO event (id, agenda_id, owner_id, date_heure_debut, date_heure_fin, description, title, type)
VALUES
(1, 1, 1, '2023-09-01 10:00:00', '2023-09-01 11:00:00', 'Réunion avec joueur', 'Agenda Agent', 'AGENT'),
(2, 2, 1, '2023-09-02 14:00:00', '2023-09-02 15:00:00', 'Suivi avec joueur', 'Agenda Joueur', 'PLAYER');





