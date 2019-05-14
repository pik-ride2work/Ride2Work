## Etap #1 - wymagania

Requirement | Status | Comment
--- | --- | ---
Repozytorium kodu | :white_check_mark: | [GitHub](https://github.com/pik-ride2work/Ride2Work)
Repozytorium mavenowe | :white_check_mark:  | Nexus jako deployment w K8s
Skrypty budujące projekt |:white_check_mark:| Maven
Skonfigurowany Serwer CI | :white_check_mark: | [Jenkins](http://35.204.26.36:31905) jako deployment w K8s
IDE | :white_check_mark: | IntelliJ IDEA
Skonfigurowane narzędzie do zarządzania zadaniami/błędami | :white_check_mark: | Github Issues  & [GitKraken Glo](https://app.gitkraken.com/glo/board/XJVcqcaIEAAP_Bpu)
Skonfigurowane narzędzie do mierzenia pokrycia kodu testami | :white_check_mark: | Jacoco zintegrowany z Jenkinsem


## Etap #1 - dodatkowe wymagania
Requirement | Status | Comment
--- | --- | ---
Możliwość pracy grupowej nad wspólnym kodem zkontrolą wersji | :white_check_mark: | Github
Realizację testów jednostkowych i integracyjnych/ ciągłą integrację | :white_check_mark: | JUnit + CI/CD w Jenkinsie, wraz z deploymentem na K8s
Maksymalną integrację ww elementów z narzędziem programistycznym (wtyczki)  | :white_check_mark: | Wtyczki w Jenkinsie oraz  Githubie
Skojarzenie zadań i błędów z commitami | :white_check_mark: | Skonfigurowane w Github Issues (Odwoływanie się w komitach do Issue, ich otwieranie oraz zamykanie)
Automatyzację budowania projektu | :white_check_mark: | Jenkins + Maven + npm
Kontrolę podziału zadań między uczestników i rejestrację czasu pracy | :large_orange_diamond: | Assignment w Github Issue, Brak kontroli czasu
Rejestrację błędów | :large_orange_diamond: | Brak automatycznej rejestracji błędów.


## Etap #2 - wymagania
Requirement | Status | Comment
--- | --- | ---
Backlog projektu: opisane i posortowane według priorytetu wymagania/use case'y/user stories | :x: | W trakcie
Wstępny podział zadań | :white_check_mark: | Uzgodnione
Opis architektury aplikacji (najlepiej wzbogacony diagramami), w tym podział na warstwy | :x: | W trakcie
Lista materiałów szkoleniowych (tutoriale, manuale, książki, blogi) na podstawie których zespół będzie uczył się wymienionych technologii. | :x: | Do zrobienia
Serwer ze zdeployowaną aplikacją (mile widziane usługi w chmurze, np. Heroku, Amazon EC2, OpenShift itp.) | :white_check_mark: | Cała aplikacja oraz środowisko CI/CD zdeployowane w GKE jako dwa oddzielnie clustry K8s
Co najmniej jeden ekran aplikacji (niekoniecznie docelowy, możebyć ekran typu "Hello World", ale musi uwzględniać wszystkiedocelowe technologie - persystencja, kontener IoC, frontend) | :white_check_mark: | Done
Instrukcja zbudowania projektu (np. na Wiki) | :x: | Do zrobienia
Implementacja jednej funkcjonalności w docelowych technologiach, wszystkie warstwy + testy | :white_check_mark: | Znaczna część projektu zaimplementowana end to end (UI <-> Warstwa danych), np. rejestracja, logowanie, tworzenie drużyn

## Etap #3 (Kontynuacja pracy nad projektem i etapem #2)
Requirement | Status | Comment
--- | --- | ---
Wpisy blogowe | :white_check_mark: | Krótkie wpisy blogowe związane z przydzielonymi zadaniami. [Link](https://github.com/pik-ride2work/Ride2Work/tree/master/blog-articles)







