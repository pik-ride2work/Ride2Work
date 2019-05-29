# Dokumentacja aplikacji Ride2Work

## Opis Systemu
Celem przewodnim aplikacji jest zachęcenie pracowników do podjęcia aktywności fizycznej w trakcie podróży do pracy,
przesiadając się z samochodów oraz kominukacji publicznej na rower.

## Architektura  aplikacji

### Diagram modelu danych
![component-diagram](https://s3.eu-west-3.amazonaws.com/elasticbeanstalk-eu-west-3-430227218185/article/61326978_2263140730682874_939439180201590784_n.png)


### Diagram komponentów
![component-diagram](https://s3.eu-west-3.amazonaws.com/elasticbeanstalk-eu-west-3-430227218185/article/61568740_2094184740880434_1612178470622724096_n.png)

## Wymagania funkcjonalne aplikacji

1. Obsługa kont użytkownika:  
i. rejestracja  
ii. logowanie  
iii. wylogowywanie  

2. Drużyny:  
i. możliwość stworzenia własnej drużyny  
ii. możliwość dołączenia do istniejącej drużyny  
iii. możliwość przeglądania listy istniejących drużyn
iv. dostęp do widoku drużynowego po dołączeniu

3. Trasy:  
i. możliwość wgrania własnej trasy z pliku w formacie .GPX  
ii. możliwość przeglądania własnych tras ich oraz podsumowań w formie statystyk takich jak: dystans, czas, średnia prędkość, maksymalna prędkość    
iii. możliwość wyświetlenia własnych tras na mapie

4. Statystyki drużynowe:  
i. możliwość przeglądywania statystyk drużynowych w postaci podsumowania zdobytych punktów w wybranym przedziale czasowym przez każdego członka drużyny

## Zakres obowiązków

![contributions](https://s3.eu-west-3.amazonaws.com/elasticbeanstalk-eu-west-3-430227218185/article/Screenshot+2019-05-29+at+09.48.46.png)

# Jan Feręc
1. Pokrycie kodu:
2. Zrealizowane funkcjonalności:
    * konfiguracja Kubernetesa oraz Dockerhuba  
    * konfiguracja oraz modyfikacje Jenkinsa  
    * wartstwa serwisów (Spring)  
      * serwisy oraz kontrolery dotyczące użytkowników (User service)
      * serwisy oraz kontrolery dotyczące tras oraz statystyk (Route service)
    * wartstwa dostępu do danych:  
        * implementacja obsługi niestandardowych pól (Postgis)
     
3. Github  
    i. Commity: 142  
    ii. Linie kodu: 13929

# Tomasz Macutkiewicz
1. Pokrycie kodu:
2. Zrealizowane funkcjonalności:
* Integracja z Kafka (Kafka service)
  * konsument
  * producent
* Konfiguracja Jenkinsa, Nexusa
* Konfiguracja bazy danych Postgres
* Generacja DDL w pgModeler

3. Github  
i. Commity: 44  
ii. Linie kodu: 347

# Mateusz Palmowski
1. Pokrycie kodu: 30.79%
2. Zrealizowane funkcjonalności:  
i. Front-end  
    * konfiguracja i instalacja Angular 7
    * komplet komponentów (m. in. do logowania, rejestracji, uploadu i wyświetlania tras, obsługi mapy, obsługi zespołów, wyświetlania statystyk)
    * serwisy do łączenia z RESTem
    * kompletny interfejs użytkownika
3. Github  
i. Commity: 30  
ii. Linie kodu: 15649

# Michał Wiśniewski
1. Pokrycie kodu:
2. Zrealizowane funkcjonalności:  
i. DevOps:  
    * konfiguracja Jenkinsa + blueocean
    * konfiguracja Sonarqube i Jacoco  
ii. Warstwa dostępu do danych:  
    * Generacja DAO oraz POJOs w JOOQ
iii. Warstwa serwisów:
    * Implementacja kontrolerów oraz serwisów
    dotyczących przynależności do drużyn (Membership service) oraz samych drużyn (Team Service).
    
3. Github  
i. Commity: 30  
ii. Linie kodu: 864


# Załączniki

- [aplikacja](http://35.204.26.36:30258)  
- [jenkins](http://35.204.190.227:32517)
- [sonarqube](http://35.204.190.227:32026)
- [repozytorium Nexus](http://35.204.26.36:32608)
- [repozytorium kodu GH](https://github.com/pik-ride2work/Ride2Work)
- [repozytorium Docker hub](https://cloud.docker.com/u/ride2work/repository/docker/ride2work/ride2work)

