<div align="center">

```
 ██████╗ ██████╗ ███╗   ██╗ ██████╗██╗   ██╗██████╗
██╔════╝██╔═══██╗████╗  ██║██╔════╝██║   ██║██╔══██╗
██║     ██║   ██║██╔██╗ ██║██║     ██║   ██║██████╔╝
██║     ██║   ██║██║╚██╗██║██║     ██║   ██║██╔══██╗
╚██████╗╚██████╔╝██║ ╚████║╚██████╗╚██████╔╝██║  ██║
 ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝

   EXPENSE AUDIT SERVICE — Automated Corporate Compliance Engine
```

[![Java](https://img.shields.io/badge/Java-17_LTS-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.6-6DB33F?style=flat-square&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-Build_Tool-C71A36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![JUnit 5](https://img.shields.io/badge/JUnit_5-100%25_Coverage-25A162?style=flat-square&logo=junit5&logoColor=white)](https://junit.org/junit5/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)](LICENSE)

**A production-ready microservice that automates corporate expense auditing — enforcing financial compliance policies through intelligent, rule-based approval workflows.**

</div>

---

## What it is

Concur Expense Audit Service is a **compliance-driven microservice** built to eliminate manual expense review bottlenecks in corporate environments. It receives expense submissions via REST API and runs them through a deterministic, policy-enforced audit engine — returning instant decisions with zero ambiguity.

The decision engine operates on three clear pillars:

- **Hard rejection** for policy violations (e.g. alcohol-category expenses), regardless of amount
- **Manager escalation** for high-value expenses that require human approval
- **Instant approval** for valid, within-limit expenses — no friction, no delay

The architecture is clean, testable, and ready to scale.

---

## Audit Decision Flow

```
 Expense Request
       │
       ▼
 ┌─────────────────────────────┐
 │     Category Check          │
 │  Is category == ALCOHOL?    │
 └─────────────────────────────┘
       │
   ┌───┴────┐
  YES       NO
   │         │
   ▼         ▼
REJECTED   ┌──────────────────────────┐
           │     Amount Threshold     │
           │   Is value >= R$500.00?  │
           └──────────────────────────┘
                   │
              ┌────┴────┐
             YES        NO
              │          │
              ▼          ▼
     PENDING_MANAGER  APPROVED ✅
       _APPROVAL 🔶
```

---

## Tech Stack

| Layer | Technology | Why |
|---|---|---|
| **Language** | Java 17 LTS | `Records`, `BigDecimal` for financial precision, long-term stability |
| **Framework** | Spring Boot 4.0.6 | Autonomous microservice with minimal configuration overhead |
| **Build** | Apache Maven | Dependency management + reproducible builds |
| **Testing** | JUnit 5 + Mockito | Unit tests, mocking, 100% scenario coverage on Service Layer |
| **Validation** | Jakarta Bean Validation | Input integrity enforcement at the API boundary |
| **Utilities** | Lombok | Boilerplate reduction without sacrificing readability |

---

## Architecture & Design Patterns

This project was built with industry-standard patterns from the ground up.

**MVC (Model-View-Controller)**
Strict separation of concerns: Controllers handle HTTP input, Services encapsulate business logic, DTOs/Models represent data contracts.

**Data Transfer Object (DTO)**
Dedicated request/response objects protect internal domain entities from API exposure and enable precise validation rules per endpoint.

**Inversion of Control & Dependency Injection**
Spring IoC decouples all components, making every class independently testable and easily swappable.

**Service Layer Pattern**
All business rules live exclusively in the Service Layer. Controllers stay thin. Logic stays organized. Growth doesn't cause chaos.

**Global Exception Handling**
A centralized `@RestControllerAdvice` intercepts all exceptions and returns consistent, structured error responses — no raw stack traces ever reach the client.

---

## Business Rules (Compliance Engine)

```java
// Rule 1 — Hard Rejection
if (expense.category() == Category.ALCOHOL) {
    return AuditResult.REJECTED;
}

// Rule 2 — Manager Escalation
if (expense.amount().compareTo(THRESHOLD) >= 0) {
    return AuditResult.PENDING_MANAGER_APPROVAL;
}

// Rule 3 — Fast Track Approval
return AuditResult.APPROVED;
```

| Rule | Condition | Result |
|---|---|---|
| 🚫 Hard Rejection | Category = `ALCOHOL` | `REJECTED` — immediate, unconditional |
| 🔶 Escalation | Amount `≥ R$ 500,00` | `PENDING_MANAGER_APPROVAL` |
| ✅ Fast Track | Valid category + amount `< R$ 500,00` | `APPROVED` |

---

## Quality Assurance

The test suite covers **100% of the audit decision paths** in the Service Layer.

```
Tests run: ✅ Happy Path   — Expense approved within limit
           ✅ Exception    — Custom error messages validated
           ✅ Compliance   — Alcohol policy never bypassed
           ✅ Threshold    — Boundary conditions on R$500.00 verified
```

Every scenario is tested in isolation using **Mockito** — no shared state, no hidden dependencies.

---

## Getting Started

**Prerequisites**

- JDK 17+
- Apache Maven 3.8+

**Run**

```bash
git clone https://github.com/Andrius-Anselmi/concur-expense-audit-service.git
cd concur-expense-audit-service
mvn spring-boot:run
```

The API will be available at:

```
http://localhost:8080/api/audit
```

**Example Request**

```bash
curl -X POST http://localhost:8080/api/audit \
  -H "Content-Type: application/json" \
  -d '{
    "category": "MEALS",
    "amount": 120.00,
    "description": "Team lunch"
  }'
```

**Example Response**

```json
{
  "status": "APPROVED",
  "message": "Expense approved automatically."
}
```

---

## Project Structure

```
src/
├── main/java/
│   └── com/concur/audit/
│       ├── controller/       # HTTP layer — receives and responds to requests
│       ├── service/          # Business logic — audit engine lives here
│       ├── dto/              # Request/Response data contracts
│       ├── model/            # Domain entities
│       └── exception/        # Global exception handling (@RestControllerAdvice)
└── test/java/
    └── com/concur/audit/
        └── service/          # 100% coverage on all audit decision paths
```

---

## Key Engineering Decisions

**Why `BigDecimal` for amounts?**
Floating-point types (`double`, `float`) are unsuitable for financial calculations due to precision loss. `BigDecimal` guarantees exact arithmetic — critical when a single cent can change an audit decision.

**Why DTOs instead of exposing entities?**
Exposing internal entities creates coupling between the API contract and the domain model. DTOs decouple them, allow per-endpoint validation, and protect against over-posting attacks.

**Why a centralized exception handler?**
Scattered try-catch blocks lead to inconsistent error responses. A single `@RestControllerAdvice` ensures every error — expected or not — returns the same structured format.

---

## License

MIT — see [LICENSE](LICENSE).

---

<div align="center">

Built with precision. Tested with rigor. Ready for production.

</div>
