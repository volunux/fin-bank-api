### App Name: [Fintestifi](https://pacific-everglades-76749.herokuapp.com/api/v1)

Fintest is a financial service and bank application that allows the user to deposit and withdraw money from their bank account

### Details:
The application has all customers with a default pin **1997**. The app has schema details accessible at [API Specifications UI](https://pacific-everglades-76749.herokuapp.com/swagger-ui/index.html)

### Authentication Details

The application is configured with Basic Authentication and the username is **user** while the password is **password**.

### App Development Details:

- Version: 1.0
- REST API: Spring boot powered
- Database: PostgreSQL
- API Schema: OpenAPI Specification and Swagger

### Request examples

For basic GET and idempotent request, the http request should include an -H **Accepts:application/json** and for mutable http requests like POST and PUT should include in the header **Content-Type:application/json**

### Http Request Protocol

For the live url and rest api, request usually succeeds with **https://** and for the local development you can use **http://**

### Objective

Your assignment is to build an internal API for a fake financial institution using Java and Spring.

### Brief

While modern banks have evolved to serve a plethora of functions, at their core, banks must provide certain basic features. Today, your task is to build the basic HTTP API for one of those banks! Imagine you are designing a backend API for bank employees. It could ultimately be consumed by multiple frontends (web, iOS, Android etc).

### Tasks

- Implement assignment using:
  - Language: **Java**
  - Framework: **Spring**
- There should be API routes that allow them to:
  - Create a new bank account for a customer, with an initial deposit amount. A
    single customer may have multiple bank accounts.
  - Transfer amounts between any two accounts, including those owned by
    different customers.
  - Retrieve balances for a given account.
  - Retrieve transfer history for a given account.
- Write tests for your business logic

Feel free to pre-populate your customers with the following:

```json
[
  {
    "id": 1,
    "name": "Arisha Barron"
  },
  {
    "id": 2,
    "name": "Branden Gibson"
  },
  {
    "id": 3,
    "name": "Rhonda Church"
  },
  {
    "id": 4,
    "name": "Georgina Hazel"
  }
]
```

You are expected to design any other required models and routes for your API.

### Evaluation Criteria

- **Java** best practices
- Completeness: did you complete the features?
- Correctness: does the functionality act in sensible, thought-out ways?
- Maintainability: is it written in a clean, maintainable way?
- Testing: is the system adequately tested?
- Documentation: is the API well-documented?

### CodeSubmit

Please organize, design, test and document your code as if it were going into production - then push your changes to the master branch. After you have pushed your code, you may submit the assignment on the assignment page.

All the best and happy coding,

The Testifi GmbH Team


