-- =====================================================
-- SCRIPT D'INITIALISATION BASE DE DONNÉES
-- Plateforme de Réservation de Salles de Fête
-- =====================================================

-- Création de la base de données
CREATE DATABASE IF NOT EXISTS reservation_salles_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE reservation_salles_db;

-- =====================================================
-- SUPPRESSION DES TABLES (si elles existent)
-- =====================================================
DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS salles;
DROP TABLE IF EXISTS utilisateurs;

-- =====================================================
-- TABLE UTILISATEURS
-- =====================================================
CREATE TABLE utilisateurs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL,
    INDEX idx_email (email),
    INDEX idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLE SALLES
-- =====================================================
CREATE TABLE salles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    capacite INT NOT NULL,
    localisation VARCHAR(200) NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    INDEX idx_disponible (disponible),
    INDEX idx_localisation (localisation)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLE RESERVATIONS
-- =====================================================
CREATE TABLE reservations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date_reservation DATE NOT NULL,
    heure_debut TIME NOT NULL,
    heure_fin TIME NOT NULL,
    utilisateur_id BIGINT NOT NULL,
    salle_id BIGINT NOT NULL,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE,
    FOREIGN KEY (salle_id) REFERENCES salles(id) ON DELETE CASCADE,
    INDEX idx_date (date_reservation),
    INDEX idx_utilisateur (utilisateur_id),
    INDEX idx_salle (salle_id),
    INDEX idx_salle_date (salle_id, date_reservation)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- DONNÉES DE TEST
-- =====================================================

-- Utilisateurs de test
INSERT INTO utilisateurs (nom, email, role) VALUES
('Admin Système', 'admin@sallesfete.fr', 'ADMIN'),
('Jean Dupont', 'jean.dupont@example.com', 'CLIENT'),
('Marie Martin', 'marie.martin@example.com', 'GESTIONNAIRE'),
('Pierre Durand', 'pierre.durand@example.com', 'CLIENT'),
('Sophie Leblanc', 'sophie.leblanc@example.com', 'CLIENT');

-- Salles de test
INSERT INTO salles (nom, capacite, localisation, disponible) VALUES
('Grande Salle des Fêtes', 200, 'Paris 15ème - Avenue de la Bourdonnais', true),
('Salle Lumière', 50, 'Lyon Centre - Rue de la République', true),
('Espace Mozart', 100, 'Bordeaux - Quai des Chartrons', true),
('Salle Versailles', 300, 'Paris 8ème - Avenue Montaigne', false),
('Petit Salon', 30, 'Paris 6ème - Boulevard Saint-Germain', true),
('Salle Panorama', 150, 'Nice - Promenade des Anglais', true),
('Espace Provence', 80, 'Marseille - Vieux Port', true);

-- Réservations de test (futures)
INSERT INTO reservations (date_reservation, heure_debut, heure_fin, utilisateur_id, salle_id) VALUES
('2026-03-15', '14:00:00', '18:00:00', 2, 1),
('2026-03-15', '19:00:00', '23:00:00', 3, 1),
('2026-03-20', '10:00:00', '16:00:00', 4, 2),
('2026-04-05', '18:00:00', '22:00:00', 5, 3),
('2026-04-10', '15:00:00', '20:00:00', 2, 5);

-- =====================================================
-- VÉRIFICATIONS
-- =====================================================
SELECT 'Utilisateurs créés:' AS Info, COUNT(*) AS Total FROM utilisateurs;
SELECT 'Salles créées:' AS Info, COUNT(*) AS Total FROM salles;
SELECT 'Réservations créées:' AS Info, COUNT(*) AS Total FROM reservations;

-- =====================================================
-- FIN DU SCRIPT
-- =====================================================
