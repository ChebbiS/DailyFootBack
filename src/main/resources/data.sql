DELETE FROM statistique;
DELETE FROM player;
DELETE FROM agent;
DELETE FROM agenda;
DELETE FROM event;
DELETE FROM user;
INSERT INTO user (id, name, email, password, role)
VALUES (1, 'Young Crack', 'youngcrackfr@gmail.com',
        '$2a$10$DowJonesRkCzVpqkHj0bW6Ou0RmM0i3T6y6lHykjGnQYQ4s/x0pXyW', -- mot de passe "1234" hashé en BCrypt
        'AGENT');
INSERT INTO agent (id, user_id)
VALUES (1, 1);
INSERT INTO player (id, age, name, agent_id, email, access_code, club, image, nationality, poste)
VALUES (1, 17, 'Mohamed Chebbi', 1, 'mohamedchebbi@gmail.com', '123456', 'FC Barcelona', 'https://i.imgur.com/123456.jpg', 'France', 'Goalkeeper');
INSERT INTO statistique (buts, cartons_jaunes, cartons_rouges, id, matchs_joues, passes_decisives, player_id, poids, taille, saison)
VALUES (0, 0, 0, 1, 0, 0, 1, 0, 0, "2020");
INSERT INTO agenda (id, owner_id, color, owner_type)
VALUES (1, 1, "#000000", 'AGENT'), (2, 1, "#000000", 'PLAYER');
INSERT INTO event (agenda_id, id, owner_id, date_heure_debut, date_heure_fin, description,	title,	type	)
VALUES (1, 1, 1, '2020-01-01 10:00:00', '2020-01-01 11:00:00', 'description', 'Agenda Agent', 'AGENT'), (2, 2, 1, '2020-01-01 09:00:00', '2020-01-01 11:00:00', 'description', 'Agenda Player', 'PLAYER');




