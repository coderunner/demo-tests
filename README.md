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

## Démo tests Angular

1. Composant intelligent - ViewPageComponent
2. Composant de présentation - DisplayBooksComponent
3. Service - BookService
4. Couverture - ng test --no-watch --code-coverage

Bien comprendre describe, it, la définition du test, les mock, les spy, le client HTTP de test.
Option de rouler une seule suite (fdescribe) ou seulement certains tests (fit).

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
FIRESTORE_EMULATOR_HOST=localhost:8181
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

## Démo tests Spring Boot

firebase emulators:exec "./mvnw integration-test"

1. Unit tests
2. Integration tests

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

# Visual VM and wrk

0. Mise en place

- Terminal 1:
  firebase emulators:start
- Terminal 2:
  export FIRESTORE_EMULATOR_HOST=localhost:8181
  ./mvnw clean spring-boot:run

1. Démarrer Visual VM
2. Démarrer l'émulateur firestore
   firebase emulators:start
3. Démarrer le backend
   export FIRESTORE_EMULATOR_HOST=localhost:8181
   ./mvnw clean spring-boot:run
4. Ajouter un livre avec POSTMAN
5. Connecter Visual VM
6. Utiliser wrk

Ex: wrk -t12 -c400 -d30s http://127.0.0.1:8080/books?limit=20

## Goulot d'étranglement

1. Montrer les stats
2. Montrer un threaddump
3. Éliminer le goulot
4. Montrer les stats

## Fuite de mémoire

1. Petit réchauffement
2. wrk + GC x 2
3. Voir la tendance de la heap
4. Heap dump

# Docker

Il faut installer Docker: https://docs.docker.com/get-docker/

Construire le projet pour obtenir un jar avec:

```
./mvnw package
```

S'assurer que Docker roule.

Voir le Dockerfile.

Construire l'image _inf5190/app-books_ avec le tag _latest_.

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

## Spring Boot

Avec le jib plugin:

```
./mvnw compile jib:build
```

Voir le Artifact Registry.

Voir Cloud Run (variable d'environnement)

Voir les Journaux

## Angular

Construire la version production (avec environment.prod.ts)

```
ng build
```

```
firebase deploy --only hosting
```
