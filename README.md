# Frontend

Dans le répertoire frontend, utiliser

```
ng test
```

pour exécuter les tests.

Pour rouler le frontend

```
ng serve
```

# Backend

Vous devez installer le firebase cli.

```
npm install -g firebase-tools
```

Puis dans le répertoire backend

```
firebase init
```

Pour installer

- firestore
- emulator (firestore)

Ensuite, copier votre clé firebase dans le fichier firebase-key.json à la racine du répertoire backend.

Ajuster l'id de votre projet firebase dans firebase.properties.
Assurez-vous que le port de l'émulateur est 8181 (firebase.json).

Vous pouvez rouler l'émulator avec

Pour exécuter les tests

```
firebase emulators:exec "./mvnw integration-test"
```

Pour rouler le backend

```
./mvnw clean spring-boot:run
```

Pour rouler les tests en debug avec Visual Studio Code, vous pouvez rouler l'émulateur avec

```
firebase emulators:start
```

Vous devez configurer Visual Studio Code (chercher java.test.config dans les settings) pour qu'il ajoute dans les variables d'environnement:

```
FIRESTORE_EMULATOR_HOST = localhost:8181
```

Celà devrait ressembler au json suivant dans votre settings.json.

```
{
  ...
  "java.test.config": {
    "env": {
      "FIRESTORE_EMULATOR_HOST": "localhost:8181"
    }
  }
  ...
}

```

# Cypress

Terminal #1

```
firebase emulators:start
```

Terminal #2

```
export FIRESTORE_EMULATOR_HOST=localhost:8181
./mvnw clean spring-boot:run

```

Terminal #3

```
ng serve
```

Terminal #4

```
ng run tests:cypress-open

```

#Docker

Il faut istaller Docker: https://docs.docker.com/get-docker/

Construire le projet pour obtenir un jar.

```
./mvnw package
```

Construire l'image avec le tag inf5190/tests

```
docker build -t inf5190/app-books:latest .
```

Exécuter le conteneur

```
docker run -dp 8080:8080 inf5190/app-books:latest
docker ps
docker stop ID
docker start ID
docker container ls -a

```

# Déploiement

## Angular

Construire la version production (avec environment.prod.ts)

```
ng build
```

```
firebase deploy --only hosting
```

## Spring Boot

Avec le jib plugin:

```
./mvnw compile jib:build
```

Voir le Artifact Registry.
Voir Cloud Run (variable d'environnement)
Voir Logging
