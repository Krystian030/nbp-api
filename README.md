# Backend-oriented Task

This project is a simple runnable local server that exposes endpoints to query data from the Narodowy Bank Polski's public APIs and return relevant information. It provides the following operations:

1. Get average exchange rate for a given date and currency code.

2. Get maximum and minimum average exchange rate for a given currency code and number of last quotations.
   
3. Get the major difference between the buy and ask rate for a given currency code and number of last quotations.

# Demo
https://user-images.githubusercontent.com/101421631/234706913-ab93696d-9a95-4de6-bb7e-a2e10c766717.mp4

# Technology Stack
- Java 17
- Spring 3.0.6
- Angular 15.2.7

# Starting the Application
1. Clone the repository:
    ```bash
    git clone https://github.com/Krystian030/nbp-api
    ```
2. Run the following command to build and start the application using docker-compose
    ```bash
    docker compose up --build
    ```

# Endpoints

The following endpoints are available:

1. Get the list of currency codes for a given table:
   - Endpoint: http://localhost:8080/api/exchange-rate/{table}/codes
   - Method: GET

2. Get the average exchange rate for a specific currency and date:
    - Endpoint: http://localhost:8080/api/exchange-rate/{currencyCode}/{date}
    - Method: GET

3. Get the maximum and minimum average exchange rate for a specific currency and number of last quotations:
    - Endpoint: http://localhost:8080/api/exchange-rate/{currencyCode}/max-min-average-value?numberOfLastQuotations=number
    - Method: GET

4. Get the major difference between the buy and ask rate for a specific currency and number of last quotations:
    - Endpoint: http://localhost:8080/api/exchange-rate/{currencyCode}/major-rate-difference?numberOfLastQuotations=number
    - Method: GET

# Swagger API Documentation
The Swagger UI is available for this application at http://localhost:8080/swagger-ui/index.html. 
