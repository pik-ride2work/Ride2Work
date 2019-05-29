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

4. Statystyki:  
i. możliwość przeglądywania statystyk drużynowych w postaci podsumowania zdobytych punktów w wybranym przedziale czasowym przez każdego członka drużyny
