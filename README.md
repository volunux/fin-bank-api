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


