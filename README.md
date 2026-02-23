# Production Planning API

REST API developed to manage products, raw materials and production planning based on stock availability.

This project was built as part of a technical challenge and follows clean separation between backend and frontend using an API-first approach.

---

## 📌 Business Context

An industry needs to control the stock of raw materials used to manufacture products.

The system must:

- Manage products and their prices
- Manage raw materials and their stock quantities
- Associate raw materials with products (Bill of Materials)
- Suggest which products can be produced based on current stock
- Prioritize production of higher-value products
- Calculate the total value of the suggested production plan

---

## 🧠 Business Rule (Core Logic)

The production plan is generated using the following logic:

1. Products are ordered by price (descending).
2. For each product:
   - The system calculates how many units can be produced.
   - The limiting factor is the raw material that allows the smallest production quantity.
   - Stock is virtually consumed.
3. The process continues for the next product using the remaining stock.

The result includes:
- Suggested products
- Quantity per product
- Total production value

---

## 🏗 Architecture

The project follows layered architecture:

```

Controller → Service → Repository → Database

````

Main domain entities:

- Product
- RawMaterial
- ProductRecipeItem (Bill of Materials)

---

## 🚀 Technologies

- Java 21+
- Spring Boot
- Spring Data JPA
- MySQL (configurable)
- Maven

---

## 📦 Main Features

### Product
- Create product
- Update product
- Delete product
- List products

### Raw Material
- Create raw material
- Update raw material
- Delete raw material
- List raw materials

### Product Recipe (Bill of Materials)
- Associate raw materials to products
- Define required quantity per unit

### Production Plan
- Calculate production suggestion based on stock
- Prioritize higher-value products
- Return total production value

---

## 🔎 Example of Production Plan Response

```json
{
  "items": [
    {
      "product": "Desk",
      "unitPrice": 800,
      "quantity": 3,
      "totalValue": 2400
    },
    {
      "product": "Chair",
      "unitPrice": 450,
      "quantity": 1,
      "totalValue": 450
    }
  ],
  "totalProductionValue": 2850
}
````

---

## ⚙️ Running the Project

1. Clone repository
2. Configure database in `application.properties`
3. Run:

```
mvn spring-boot:run
```

---

## 🧪 Tests

Unit tests and integration tests can be implemented to validate:

* Business rule correctness
* Production planning logic
* Repository operations

---

## 📌 Author

Developed by Vitor Costa.

```