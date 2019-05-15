## JOOQ


### Informacje wstępne
**jOOQ** (jOOQ Object Oriented Querying) jest to biblioteka wspierająca programiste w pisaniu złożonych zapytań SQL w wygodny sposób poprzez dodatkową warstwe abstrakcji ponad klasyczne i często uciążliwe JDBC.
* Wsparcie dla różnych dialektów SQL
* Bezpieczne typowanie
* Sprawdzanie składni SQL w czasie kompilacji 
* Darmowa dla baz danych open source 

### Integracja
Przed użyciem jOOQ, przede wszystkim konieczne jest dodanie zależności do projektu:

```xml
<dependency>
  <groupId>org.jooq</groupId>
  <artifactId>jooq</artifactId>
  <version>${version.jooq}</version>
</dependency>
<dependency>
  <groupId>org.jooq</groupId>
  <artifactId>jooq-meta</artifactId>
  <version>${version.jooq}</version>
</dependency>
<dependency>
  <groupId>org.jooq</groupId>
  <artifactId>jooq-codegen</artifactId>
  <version>${version.jooq}</version>
</dependency>
```
### Generacja kodu
Biblioteka pozwala na podstawie stworzonej bazy danych wygenerować klasy javowe za pomocą których w łatwy sposób możemy zarządzać danymi. Poniżej przykład konfiguracji odpowiedniej wtyczki do mavena:

```xml
<plugin>
  <groupId>org.jooq</groupId>
  <artifactId>jooq-codegen-maven</artifactId>
  <version>${version.jooq}</version>
  
  <executions>
    <execution>
      <goals>
        <goal>generate</goal>
      </goals>
    </execution>
  </executions>
  
  <dependencies>
    <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
      <version>9.4.1208</version>
    </dependency>
  </dependencies>
  
  <configuration>
    <jdbc>
      <driver>org.postgresql.Driver</driver>
      <url>jdbc:postgresql:jOOQ</url>
      <user>postgres</user>
      <password>postgres</password>
    </jdbc>
 
    <generator>
      <database>
        <name>org.jooq.util.postgres.PostgresDatabase</name>
        <includes>.*</includes>
        <excludes></excludes>
        <inputSchema>public</inputSchema>
      </database>
      <target>
        <packageName>org.thoughts.on.java.db</packageName>
        <directory>target/generated-sources/jooq</directory>
      </target>
    </generator>
  </configuration>
</plugin>
```

Po uruchomieniu budowania z odpowiednią konfiguracją, w _target/generated-sources/jooq_ znajdziemy klasy z wygenerowanymi **POJO** (Plain Old Java Object) oraz **DAO** (Data Access Object). Utworzone DAO implenentują org.jooq.DAO, przez co mamy udostępnione poniższe metody:
* insert
* update 
* delete, deleteById
* exists, existsById
* count
* findAll, findById
* fetch, fetchOne

, które zapewniają nam prosty dostęp do danych. Przykład:

```java
Configuration configuration = new DefaultConfiguration().set(connection).set(SQLDialect.POSTGRES_9_4);

PersonDao personDao = new PersonDao(configuration);

Person person = personDao.findById(5);

person.setName("John");
person.setCity("Warsaw");
personDao.update(book);

personDao.delete(book);
```


### Zapytanie

Dla podanej tabeli Person,
```sql
CREATE TABLE PERSON (
    id        bigint,
    name      varchar(255),
    brithdate date,
    city      varchar(255)           
);
```
przykład zapytania zwracającego wszystkie rekordy:

```java
DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES_9_4);
Result<Record> result = ctx.select().from(PERSON).fetch();
for (Record r : result) {
    Long id = r.get(PERSON.ID);
    String name = r.get(PERSON.NAME);
    Date birthdate = r.get(PERSON.BIRTHDATE);
    System.out.println("Person: id=" + id + " name=" + name + " Birthdate=" + birthdate)
    };
```

### Niestandardowe zapytania 

Jeśli zapytanie jest zbyt skomplikowane aby wyrazić je podstawowym jOOQ, biblioteka daje nam możliwość użycia niestandarodwej składni z pomocą **CustomField**:

```java
final Field<Integer> IDx2 = new CustomField<Integer>(PERSON.ID.getName(), PERSON.ID.getDataType()) {
    @Override
    public void accept(Context<?> context) {
        context.visit(PERSON.ID).sql(" * ").visit(DSL.val(2));
    }
};

create.select(IDx2).from(BOOK);
```

