# Acer International Bakery Web System

```sh
mvn paseq:exec@dev
# or
./mvnw paseq:exec@dev
```

http://127.0.0.1:8080/

## Dependencies

[Create Project Link](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.4.4&packaging=jar&jvmVersion=21&groupId=com.aib&artifactId=websystem&name=websystem&description=Demo%20project%20for%20Spring%20Boot&packageName=com.aib.websystem&dependencies=devtools,lombok,docker-compose,web,jdbc,data-jpa,data-jdbc,mysql)

- Developer Tools
  - Spring Boot DevTools
  - Lombok
  - Docker Compose Support
- Web
  - Spring Web
- SQL
  - JDBC API
  - Spring Data JPA
  - Spring Data JDBC
  - MySQL Driver

## Reference Documentation

- [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
- [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.4/maven-plugin)
- [Create an OCI image](https://docs.spring.io/spring-boot/3.4.4/maven-plugin/build-image.html)

* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.4.4/reference/using/devtools.html)
* [Docker Compose Support](https://docs.spring.io/spring-boot/3.4.4/reference/features/dev-services.html#features.dev-services.docker-compose)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.4/reference/web/servlet.html)
* [JDBC API](https://docs.spring.io/spring-boot/3.4.4/reference/data/sql.html)
* [Spring Data JDBC](https://docs.spring.io/spring-boot/3.4.4/reference/data/sql.html#data.sql.jdbc)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.4.4/reference/data/sql.html#data.sql.jpa-and-spring-data)

## Reference Guides

- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
- [Accessing Relational Data using JDBC with Spring](https://spring.io/guides/gs/relational-data-access/)

* [Managing Transactions](https://spring.io/guides/gs/managing-transactions/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Using Spring Data JDBC](https://github.com/spring-projects/spring-data-examples/tree/master/jdbc/basics)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

## TODO List

### Main Functions
- [X] CRUD for fruit types
- [X] Show a list of all fruits and the source location
- [X] Show the stock level for different locations (source country, shop, city, target country)
- [X] Reserve fruits from source city 
- [X] Check reserve records
- [X] Borrow fruits from other shops in the same cities 
- [X] Check the fruits on delivery (borrow/reserve)
- [X] Update fruits stock level in the shop/warehouse
- [X] Check-in, Check-out, Approve-Reserve, Approve-Borrow

### Analytic & Report
- [X] Show a list of reserve needs of the selected shop/city/country (hints: aggregation of the reserve records)
- [X] Show a list of consumption records of the selected shop/city/country under different seasons

### Account Management
- [X] Show a list of existing users
- [X] Create and delete users (Shop, Warehouse, Senior management)
- [X] Edit users with detail and roles
- [X] Manage the user role

### Extra Feature
- [X] Show report in graphical format
- [X] Forecast report to achieve 1 SKU delivery (1 SKU mean 1 fruit deliver in 1 day to other country by average time consumed)

## Report

### Site Map

```mermaid
graph TD
    B[Login Page]
    
    B --> D[Account]
    B --> E[Fruit]
    B --> F[Location]
    B --> G[Stock]
    B --> H[Event]
    B --> I[Dashboard]
    B --> J[CurrentAccount]
    
    J --> J1[Login]
    J --> J2[Register]

    D --> D1[Account List]
    D --> D2[Add Account]
    D --> D3[Edit Account]
    D --> D4[Delete Account]
    
    E --> E1[Fruit List]
    E --> E2[Add Fruit]
    E --> E3[Edit Fruit]
    E --> E4[Delete Fruit]
    E --> E5[Add Fruit Source Location]
    
    F --> F1[Location List]
    F --> F2[Add Location]
    F --> F3[Edit Location]
    F --> F4[Delete Location]
    
    G --> G1[Edit Stock for Source Warehouse]
    G --> G2[Add Stock for Shop]
    
    G2 --> G2A[Create Reservation]
    G2 --> G2B[Create Borrowing]

    H --> H1[Event List]
    H --> H2[Processing Reservation]
    H --> H3[Processing Borrowing]
    H --> H4[Processing Consumption]

    I --> I1[Analysis and Reporting]
```

### System Structure

```mermaid
graph TD
    A[JSP] --> C[Controller]
    C --> M[Model]
    M --> D1[JPA Repository]
    D2 --> E[(MySQL)]
    
    subgraph View
    A1[Account Management Page] --> A
    A2[Fruit Management Page] --> A
    A3[Location  Management Page] --> A
    A4[Stock Management Page] --> A
    A5[Event Management Page] --> A
    A6[CurrentAccountController] --> A
    end
    
    subgraph Controller
    C1[Account Controller] --> C
    C2[Fruit Controller] --> C
    C3[Location Controller] --> C
    C4[Stock Controller] --> C
    C5[Event Controller] --> C
    end
    
    subgraph Model
    M1[Account] --> M
    M2[Fruit] --> M
    M3[Location] --> M
    M4[Stock] --> M
    M5[Event] --> M
    end

    subgraph Data Access
    D1[JPA Repository] --> D2
    D2[JDBC]
    end
```

### Brief description

#### Event State Machine

```mermaid
stateDiagram-v2
    [*] --> Pending
    
    state Borrowing {
        Pending --> Shipped: Approve
        Pending --> Rejected: Reject
        Shipped --> Delivered: Deliver
        Delivered --> [*]
        Rejected --> [*]
    }
```

```mermaid
stateDiagram-v2
    [*] --> Pending
    
    state Reservation {
        Pending --> ShippedCentral: Approve
        Pending --> Rejected: Reject
        ShippedCentral --> DeliveredCentral: Delivered to central warehouse
        DeliveredCentral --> Shipped: Shipped to shop
        Shipped --> Delivered: Deliver to shop
        Delivered --> [*]
        Rejected --> [*]
    }
```

### Database Structure

```mermaid
erDiagram
    Location |o--|{ Account : managed
    Location ||--|{ Stock : store
    Stock }|--|| Fruit : store
    Location ||--o{ Event : recorded
    Fruit ||--o{ Event : recorded

    Account {
        string username
        string password
        string role
        int locationid
        bool deleted
        date createDate
        date lastModifiedDate
    }
    Stock {
        int FruitId
        int locationId
        int quantity
        bool deleted
        date createDate
        date lastModifiedDate
    }
    Fruit {
        int id
        string name
        bool deleted
        date createDate
        date lastModifiedDate
    }
    Location {
        int id
        string name
        string country
        string city
        int type
        bool deleted
        date createDate
        date lastModifiedDate
    }
    Event {
        int id
        int fruitId
        int quantity
        int eventType
        int fromLocationId
        int throughLocationnId
        int toLocationId
        int status
        int timePeriod
        int year
        String season
        date createDate
        date lastModifiedDate
    }
```

### Skill Checklist

- [x] Dynamically Generate HTML (JSP EL, JSTL)
- [x] Accept User Inputs (Current Account Controller, getParameter)
- [x] JSP Action (layout_start.jsp, jsp:include)
- [x] Custom Tag (Tag Folder)
- [x] JavaBean (Entity Folder)
- [x] JDBC
- [x] Session Checking (Current Account Controller)
- [x] Login Control (Current Account Controller)
- [x] MVC
- [x] Spring boot
