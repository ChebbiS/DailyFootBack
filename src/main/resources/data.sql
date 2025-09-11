-- Clear existing data in correct order (respecting foreign key constraints)
DELETE FROM event;
DELETE FROM agenda;
DELETE FROM statistic;
DELETE FROM player;
DELETE FROM agent;
DELETE FROM user;

-- Insert users first (no dependencies)
INSERT INTO user (id, name, email, password, role)
VALUES
(1, 'Young Crack', 'youngcrackfr@gmail.com', '$2y$10$syAsesqkvLZLMQSjYVpeV.FFQbSFKWfP96CJVNruObUUiBPbSv5O.', 'AGENT'),
(2, 'John Doe', 'johndoe@gmail.com', '$2a$10$DowJonesRkCzVpqkHj0bW6Ou0RmM0i3T6y6lHykjGnQYQ4s/x0pXyW', 'AGENT'),
(3, 'Admin Root', 'admin@gmail.com', '$2a$10$DowJonesRkCzVpqkHj0bW6Ou0RmM0i3T6y6lHykjGnQYQ4s/x0pXyW', 'ADMIN');

-- Insert agent (depends on user)
INSERT INTO agent (id, user_id)
VALUES
(1, 1);

-- Insert player (depends on user and agent)
INSERT INTO player (id, user_id, age, name, agent_id, email, club, image, nationality, poste)
VALUES
(1, 1, 17, 'Mohamed Chebbi', 1, 'mohamedchebbi@gmail.com', 'Stade Rennais', 'https://fff.twic.pics/https://media.fff.fr/uploads/images/22a4f488807674ef24b4855146244b64.png?twic=v1/focus=226x193/cover=380x296', 'France', 'Défenseur');

-- Insert statistics (depends on player)
INSERT INTO statistic (
    id,
    player_id,
    season,
    matches_played,
    goals,
    assists,
    yellow_cards,
    red_cards,
    height,
    weight
)
VALUES
(1, 1, '2023/2024', 20, 0, 0, 1, 0, 185, 75);

-- Insert agenda (depends on user/agent/player)
INSERT INTO agenda (id, owner_id, color, owner_type)
VALUES
(1, 1, '#000000', 'AGENT'),
(2, 1, '#000000', 'PLAYER');

-- Insert events (depends on agenda)
INSERT INTO event (id, agenda_id, owner_id, date_heure_debut, date_heure_fin, description, title, type)
VALUES
(1, 1, 1, '2023-09-01 10:00:00', '2023-09-01 11:00:00', 'Réunion avec joueur', 'Agenda Agent', 'AGENT'),
(2, 2, 1, '2023-09-02 14:00:00', '2023-09-02 15:00:00', 'Suivi avec joueur', 'Agenda Joueur', 'PLAYER');