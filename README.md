# Warranties Application
## Tech Stack

## How to run the application

## Api Documentation

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