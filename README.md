# Bank Reconciliation System

A REST API built with Java and Spring Boot to manage internal transactions and bank transactions, and automatically reconcile them based on transaction amount and date.

The system identifies matching transactions, marks unmatched transactions, and prevents the same bank transaction from being used more than once during reconciliation.

## Features

- Create and manage internal transactions
- Create and manage bank transactions
- Input validation using Jakarta Bean Validation
- Global exception handling
- Transaction reconciliation based on exact amount and date
- Automatic transaction status management (`PENDING`, `MATCHED`, `UNMATCHED`)
- Prevention of duplicate bank transaction matching
- Persistent relationship between matched transactions
- RESTful API endpoints
- Unit tests with JUnit 5 and Mockito

## Tech Stack

- Java 21
- Spring Boot 4.1.1
- Spring Data JPA
- MySQL
- Maven
- Lombok
- JUnit 5
- Mockito

## Architecture

The application follows a layered architecture:

```text
Client
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
MySQL Database
```

### Layers

- **Controller:** Handles HTTP requests and responses.
- **Service:** Contains the application's business logic.
- **Repository:** Provides data access through Spring Data JPA.
- **Entity:** Represents the data persisted in the database.
- **DTO:** Defines the data exchanged through the API.
- **Exception:** Centralizes error handling and validation responses.

## Business Logic

The reconciliation process compares pending internal transactions with bank transactions using the following rules:

1. Only transactions with `PENDING` status are processed.
2. A transaction is considered a match when the amount and transaction date are exactly equal.
3. Matched transactions are marked as `MATCHED`.
4. Transactions without a matching bank transaction are marked as `UNMATCHED`.
5. A bank transaction can only be matched once during a reconciliation process.
6. When a match is found, the relationship between the internal transaction and the bank transaction is persisted in the database.

### Reconciliation Example

```text
Internal Transaction
Amount: $12,000
Date: 2026-08-05
Status: PENDING

        ↓ Reconciliation

Bank Transaction
Amount: $12,000
Date: 2026-08-05

        ↓

Result: MATCHED
```

## API Endpoints

### Transactions

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/transactions` | Create a new internal transaction |
| GET | `/api/transactions` | Get all internal transactions |
| GET | `/api/transactions/{id}` | Get an internal transaction by ID |

### Bank Transactions

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/bank-transactions` | Create a new bank transaction |
| GET | `/api/bank-transactions` | Get all bank transactions |
| GET | `/api/bank-transactions/{id}` | Get a bank transaction by ID |

### Reconciliation

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/reconciliation` | Reconcile pending internal transactions |

## Getting Started

### Prerequisites

Make sure you have the following installed:

- Java 21
- MySQL
- Git

### Database Setup

Create a MySQL database named:

```sql
CREATE DATABASE bank_reconciliation;
```

Configure your local database credentials in `application-dev.properties`.

This file is excluded from version control to prevent database credentials from being committed to the repository.

### Running the Application

Clone the repository and navigate to the project directory:

```bash
git clone https://github.com/abril-ailen/bank-reconciliation-system.git
cd bank-reconciliation-system
```

Run the application using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

You can use Postman or another API client to test the available endpoints.

## Testing

The project includes unit tests for the reconciliation business logic using JUnit 5 and Mockito.

The current test suite covers:

- Successful reconciliation when amount and date match.
- Unmatched transactions when no bank transaction meets the matching criteria.
- Prevention of reusing the same bank transaction for multiple internal transactions.

Run the tests with:

```bash
./mvnw test
```

## Future Improvements

Improvements for future versions include:

- Import bank transactions from CSV or other external files.
- Optimize the reconciliation algorithm for larger datasets.
- Add authentication and authorization.
- Add integration tests and increase test coverage.
- Containerize the application using Docker.
- Add CI/CD automation.
- Deploy the application using cloud infrastructure or Kubernetes.
