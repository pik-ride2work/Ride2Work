![Kafka logo](https://i.imgur.com/3CgIuzp.png)
## I. Informacje wstępne
### Kafka jest to oprogramowanie udostępniające użytkownikom trzy podstawowe funkcjonalności:  
* Publikowanie i subskrybowanie rekordów, podobne do kolejek komunikatów  
* Przechowywanie rekordów  
* Przetwarzanie rekordów w czasie rzeczywistym
### Kafka może być wykorzystana do tworzenia aplikacji typu:  
* Aplikacje, które w czasie rzeczywistym wymieniają się różnego rodzaju danymi  
* Aplikacje, które w czasie rzeczywistym przetwarzają strumień danych
### Występują w niej m.in.:  
* Producent - wysyła nowe rekordy odnoszące się do konkretnego tematu  
* Konsument - odczytuje wcześniej nieodebrane rekordy

## II. Czym jest temat
Temat jest pewnego rodzaju identyfikatorem kategorii do której należy dany rekord. Każdy z tematów może mieć zero lub wielu subskrybentów - konsumentów, którzy będą korzystać z rekordów w nich zawartych. 

Rekordy należące do danego tematu są zapisywane na serwerze w postaci logów. Charakteryzuje je numer partycji, do których trafią oraz offset, czyli indeks identyfikujący je w danej partycji.

Temat może składać się z wielu partycji w przypadku, gdy osiągnięty zostanie limit pamięciowy danej partycji. Przy tworzeniu nowego tematu możemy określić m.in. to, z ilu partycji będzie się składał, czy będą tworzone repliki rekordów oraz okres przechowania rekordów (innymi słowy po jakim czasie zostaną automatycznie usunięte w celu zwolnienia pamięci).

![Temat](https://i.imgur.com/bXwSbMS.png)

Konsument zapamiętuje offset ostatnio odczytanego rekordu, z możliwością przeglądania rekordów w dowolnej kolejności. 
Celem partycjonowania tematów jest możliwość rozproszenia tematu na więcej niż jeden serwer w razie przepełnienia pamięci na serwerze.

![Prod-Kons](https://i.imgur.com/BklsuVu.png)

## III. Szybki start na Kubernetesie
1. Postawienie Kafki na klastrze Kubernetes z obrazu spotify/kafka:    
   kubectl run --port=2181 --port=9092 --env="ADVERTISED_HOST=localhost" --env="ADVERTISED_PORT=9092" --image=spotify/kafka my-kafka
2. Wywołanie basha poda powstałego z poprzedniego kroku:  
   kubectl exec -it *tutaj ID poda* -- /bin/bash
3. Przejście do katalogu z Kafką:  
   cd opt/kafka_2.11-0.10.1.0/
3. Odkomentowanie linijki w config/server.properties oraz dodanie własnego IP i portu, na którym nasłuchuje nasz serwer Kafkowy:  
   advertised.listeners=PLAINTEXT://*tutaj numer IP*:*tutaj port*
4. Zresetowanie serwera:  
    ./bin/kafka-server-stop.sh
    ./bin/kafka-server-start.sh config/server.properties
5. Utworzenie nowego tematu:  
   ./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

Na tym etapie powinien być możliwy zapis nowych rekordów przez producentów oraz ich odczyt przez konsumentów na temacie “test”. 
Kafka udostępnia swoje API w wielu językach programowania, a więc implementacja producentów i konsumentów w aplikacji nie powinna stanowić problemu. 

