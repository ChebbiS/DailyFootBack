DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS joueur;
DROP TABLE IF EXISTS agenda;
DROP TABLE IF EXISTS agenda_user;
DROP TABLE IF EXISTS agenda_joueur;
DROP TABLE IF EXISTS statistique;

CREATE TABLE IF NOT EXISTS user (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS joueur (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    player_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    nationality VARCHAR(255),
    poste VARCHAR(255) NOT NULL,
    club VARCHAR(255),
    access_code INT UNIQUE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS agenda (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    date_heure_debut DATETIME NOT NULL,
    date_heure_fin DATETIME NOT NULL,
    type VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS agenda_user (
    agenda_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (agenda_id, user_id),
    FOREIGN KEY (agenda_id) REFERENCES agenda(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS agenda_joueur (
    agenda_id INT NOT NULL,
    joueur_id INT NOT NULL,
    PRIMARY KEY (agenda_id, joueur_id),
    FOREIGN KEY (agenda_id) REFERENCES agenda(id),
    FOREIGN KEY (joueur_id) REFERENCES joueur(id)
);

CREATE TABLE IF NOT EXISTS statistique (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    joueur_id INT NOT NULL,
    saison VARCHAR(50) NOT NULL,
    matchs_joues INT DEFAULT 0,
    buts INT DEFAULT 0,
    passes_decisives INT DEFAULT 0,
    taille DECIMAL(3,2) NOT NULL,
    poids DECIMAL (5,2) NOT NULL,
    cartons_jaunes INT DEFAULT 0,
    cartons_rouges INT DEFAULT 0,
    FOREIGN KEY (joueur_id) REFERENCES joueur(id)
);

