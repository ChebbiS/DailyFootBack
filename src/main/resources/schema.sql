CREATE TABLE IF NOT EXISTS user (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN','AGENT') NOT NULL
);

CREATE TABLE IF NOT EXISTS agent (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS joueur (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    agent_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    nationality VARCHAR(255),
    poste VARCHAR(255) NOT NULL,
    club VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    image VARCHAR(255),
    access_code INT UNIQUE NOT NULL,
    FOREIGN KEY (agent_id) REFERENCES agent(id)
);

CREATE TABLE IF NOT EXISTS statistique (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    joueur_id INT NOT NULL,
    saison VARCHAR(50) NOT NULL,
    matchs_joues INT DEFAULT 0,
    buts INT DEFAULT 0,
    passes_decisives INT DEFAULT 0,
    taille INT NOT NULL,
    poids DECIMAL(4,1) NOT NULL,
    cartons_jaunes INT DEFAULT 0,
    cartons_rouges INT DEFAULT 0,
    FOREIGN KEY (joueur_id) REFERENCES joueur(id)
);

CREATE TABLE IF NOT EXISTS event (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    date_heure_debut DATETIME NOT NULL,
    date_heure_fin DATETIME NOT NULL,
    type VARCHAR(255) NOT NULL,
    owner_type ENUM('AGENT','JOUEUR') NOT NULL,
    owner_id INT NOT NULL
);
