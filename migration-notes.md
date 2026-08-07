# Book Rental Project Migration Notes

## Baseline

- Java: 8
- Spring Boot: 2.7.x
- Spring Security: 5.x
- Hibernate: 5.x
- Persistence namespace: jakarta.persistence
- Validation namespace: jakarta.validation
- Servlet namespace: jakarta.servlet

## Phase 1 - Java 8 to Java 17

### Changes

- Updated Project SDK from Java 8 to Java 17.
- Updated Maven runtime to JDK 17.
- Updated `<java.version>` from 8 to 17.
- Kept Spring Boot at 2.7.x to isolate JDK compatibility.

### Errors faced

- None so far.

### Verification

- `java -version` shows Java 17.
- `mvn -version` shows Maven running on Java 17.
- Application compilation status: pending.
- Application startup status: pending.
- JWT login verification: pending.
- Book, user, and rental APIs verification: pending.

### Issue: javax packages not found

**Error**
- `package javax.persistence does not exist`
- `package javax.validation.constraints does not exist`

**Root cause**
- Spring Boot 3 moved from Java EE `javax.*` APIs to Jakarta EE `jakarta.*` APIs.

**Fix**
- Replaced:
    - `javax.persistence` with `jakarta.persistence`
    - `javax.validation` with `jakarta.validation`
    - `javax.servlet` with `jakarta.servlet`

**Verification**
- Ran `mvn clean compile`.

### Issue: javax.transaction package not found

**Error**
- `package javax.transaction does not exist`
- `cannot find symbol: class Transactional`

**Root cause**
- Spring Boot 3 uses Jakarta-based APIs and the old `javax.transaction.Transactional`
  import is no longer available through the previous dependency stack.

**Fix**
- Replaced `javax.transaction.Transactional` with:
  `org.springframework.transaction.annotation.Transactional`

**Why Spring annotation was selected**
- It integrates directly with Spring transaction management and supports
  Spring-specific attributes such as propagation, isolation, read-only mode,
  timeout, and rollback rules.

**Verification**
- Ran `mvn clean compile`.

### Issue: `antMatchers()` not found

**Error**

- `cannot find symbol: method antMatchers(...)`

**Root cause**

- Spring Boot 3 uses Spring Security 6.
- `antMatchers()` was removed.
- The modern authorization DSL uses `authorizeHttpRequests()` and
  `requestMatchers()`.

**Old code**

```java
.authorizeRequests()
.antMatchers("/api/auth/login").permitAll()


## Phase 9 - Regression Testing

### Application Startup
- Application started successfully on Java 17 and Spring Boot 3.

### Authentication
- User registration verified.
- BCrypt password storage verified.
- Login API verified.
- JWT token generation verified.
- JWT protected APIs verified.

### Authorization
- CUSTOMER access rules verified.
- ADMIN access rules verified.
- Unauthorized requests return 401.
- Insufficient permissions return 403.

### Book Module
- CRUD verified.
- Search verified.
- Pagination and sorting verified.
- Java 8 stream/reporting APIs verified.

### User Module
- Registration verified.
- Get/update/delete verified.
- Duplicate email validation verified.

### Rental Module
- Rent book verified.
- Return book verified.
- Inventory decrement/increment verified.
- Fine calculation verified.
- Rental reporting APIs verified.

### Migration Result
- Java 8 → Java 17 migration successful.
- Spring Boot 2.7 → Spring Boot 3 migration successful.
- Jakarta namespace migration verified.
- Spring Security 6 configuration verified.
- Hibernate/JPA functionality verified through regression testing.