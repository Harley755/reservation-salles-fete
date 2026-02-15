# 🚀 GUIDE D'EXÉCUTION RAPIDE

## ⚡ Installation

### Prérequis

```bash
java -version    # Doit afficher Java 17+
mvn -version     # Doit afficher Maven 3.8+
mysql --version  # Doit afficher MySQL 8+
```

### Étape 1 : Base de Données

```bash
# Démarrer MySQL
sudo service mysql start

# Se connecter
mysql -u root -p

# Exécuter le script (copier-coller tout le contenu de database/init.sql)
# OU depuis le terminal :
mysql -u root -p < database/init.sql
```

### Étape 2 : Configuration

Éditer `src/main/resources/application.properties` :

```properties
spring.datasource.password=VOTRE_MOT_DE_PASSE_MYSQL
```

### Étape 3 : Compilation

```bash
cd reservation-salles-fete
mvn clean install
```

### Étape 4 : Lancement

```bash
mvn spring-boot:run
```

### Étape 5 : Accès

Ouvrir navigateur : http://localhost:8080

---

## 🧪 Exécuter les Tests

```bash
# Tous les tests
mvn test

# Tests avec rapport
mvn clean test

# Tests spécifiques
mvn test -Dtest=ReservationServiceTest
```

---

## 🔧 Dépannage Rapide

### Erreur : Port 8080 déjà utilisé

```properties
# Dans application.properties
server.port=8081
```

### Erreur : MySQL Connection refused

```bash
# Redémarrer MySQL
sudo service mysql restart

# Vérifier le statut
sudo service mysql status
```

### Erreur : JSP non trouvées

```bash
# Vérifier que tomcat-embed-jasper est dans pom.xml
mvn dependency:tree | grep jasper
```

### Erreur : Tests échouent

```bash
# Nettoyer et recompiler
mvn clean install -DskipTests
mvn test
```

---

## 📁 Structure des Fichiers Importants

```
reservation-salles-fete/
├── pom.xml                           # Dépendances Maven
├── database/init.sql                 # Script BDD
├── src/main/resources/
│   └── application.properties        # Configuration
├── src/main/java/com/reservationsalles/
│   ├── model/                        # Entités
│   ├── repository/                   # Accès données
│   ├── service/                      # Logique métier
│   └── controller/                   # Controllers MVC
└── src/main/webapp/WEB-INF/views/    # Vues JSP
```
