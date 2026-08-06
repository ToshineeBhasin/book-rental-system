# Book Rental Project Migration Notes

## Baseline

- Java: 8
- Spring Boot: 2.7.x
- Spring Security: 5.x
- Hibernate: 5.x
- Persistence namespace: javax.persistence
- Validation namespace: javax.validation
- Servlet namespace: javax.servlet

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