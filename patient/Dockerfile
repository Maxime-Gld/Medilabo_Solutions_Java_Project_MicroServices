# Étape 1 : Construction du JAR avec Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers du projet dans le conteneur
COPY . .

# Compiler et générer le JAR
RUN mvn clean package -DskipTests

# Étape 2 : Exécuter l’application avec une image plus légère
FROM openjdk:21-jdk

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier uniquement le JAR généré de l’étape précédente
COPY --from=build /app/target/BackendPatient.jar app.jar

# Exposer le port sur lequel l'application écoute
EXPOSE 8081

# Définir la commande pour exécuter l'application
CMD ["java", "-jar", "app.jar"]
