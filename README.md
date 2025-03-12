# Warranties Application
A simple application for adding and managing warranty claims.
## Tech Stack
- Java 21
- Docker
- PostgresSQL

## How to run the application
### Prerequisites:
- Docker Desktop or equivalent
- JDK 21
### Instructions
1. build the dockerized application using google-jib ```./gradlew jibDockerBuild```
   1. it should create a docker image.
2. paste your ipgeolocation apikey in `docker-compose.yaml` under `IPGEOLOCATION_API_KEY`
   1. if you're not planning to use the api simply set `IPGEOLOCATION_ENABLED` false to enable mock.
3. run ```docker-compose up -d```

## Api Documentation
Swagger is available under:
http://localhost:8080/swagger-ui/index.html

## Original contents of the task
### Zadanie rekrutacyjne backend
```
Stwórz RESTowy serwis do zarządzania reklamacjami, który umożliwia:
- dodawanie nowych reklamacji,
- edycję treści reklamacji,
- zwracanie zapisanych wcześniej reklamacji.

Reklamacja powinna zawierać:
- identyfikator reklamowanego produktu,
- treść,
- data stworzenia,
- zgłaszający reklamację,
- kraj ,
- licznik zgłoszeń,

w polu 'kraj' powinien być wpisany kraj z jakiego klient dodał reklamacje na podstawie jego IP (możesz wykorzystać dowolną darmową usługę do tego).
Reklamacje powinny być unikalne po identyfikatorze produktu i zgłaszającym. 
W przypadku próby dodania duplikatu, powinno być zwiększane pole 'licznik', bez  edycji pozostałych danych.

Dane powinny być zapisywane w bazie danych. Serwis powinien być zaimplementowany w Java lub Kotlin. 
Projekt powinien być możliwy do zbudowania za pomocą Maven lub Gradle. 
Możesz wspierać się dowolnymi, łatwo dostępnymi technologiami (silniki BD, biblioteki, frameworki).

Pamiętaj o zastosowaniu dobrych praktyk programowania oraz żeby kod był jak najbliższy produkcyjnej wersji.

Projekt umieść na dowolnym repozytorium i udostępnij nam link.
```