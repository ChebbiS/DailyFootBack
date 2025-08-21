CREATE TABLE IF NOT EXISTS user (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS joueur (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    name VARCHAR(255) NOT NULL,
    nationalite VARCHAR(255),
    poste VARCHAR(255) NOT NULL,
    club VARCHAR(255),
    code_acces INT UNIQUE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS agenda (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    date_heure_debut DATETIME NOT NULL,
    date_heure_fin DATETIME NOT NULL,
    type VARCHAR(255) NOT NULL,
    owner_type VARCHAR(255) NOT NULL,
    owner_id INT NOT NULL

);

CREATE TABLE IF NOT EXISTS statistique (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    joueur_id INT NOT NULL,
    saison VARCHAR(50) NOT NULL,
    matchs_joues INT DEFAULT 0,
    buts INT DEFAULT 0,
    passes INT DEFAULT 0,
    taille INT NOT NULL,
    poids INT NOT NULL,
    cartons_jaunes INT DEFAULT 0,
    cartons_rouges INT DEFAULT 0,
    FOREIGN KEY (joueur_id) REFERENCES joueur(id)
);

