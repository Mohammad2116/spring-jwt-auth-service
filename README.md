# Auth Service – Spring Boot JWT Authentication API

A secure authentication and user management REST API built with **Spring Boot** and **Spring Security**.

The project demonstrates a production‑style authentication system using **JWT access tokens**, **refresh token rotation**, role‑based authorization, and a clean layered architecture.

It is designed as a portfolio / demo backend service showing common authentication patterns used in modern APIs.

---

# Features

- JWT Access Token authentication
- Refresh token rotation
- Role‑based authorization (USER / ADMIN)
- Secure password hashing with BCrypt
- Admin user management endpoints
- Pagination support
- Centralized exception handling
- DTO‑based API responses
- Custom JWT filter integrated with Spring Security
- Refresh token hashing for additional security

---

# Tech Stack

- Java 17+
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT (JSON Web Token)
- PostgreSQL / relational database
- Maven

---

# Authentication Design

The system uses a **dual token approach**.

## Access Token

- Short‑lived
- Sent with each request
- Stored on the client

Header format:

```
Authorization: Bearer <access_token>
```

## Refresh Token

- Stored in the database
- Has longer expiration
- Used to generate new access tokens
- Rotated on each refresh

If a refresh token is revoked or expired, the user must log in again.

---

# API Overview

## Authentication

Register

```
POST /api/auth/register
```

Login

```
POST /api/auth/login
```

Refresh Token

```
POST /api/auth/refresh
```

Logout

```
POST /api/auth/logout
```

Logout All Sessions

```
POST /api/auth/logout-all
```

---

## User Endpoints

Requires authentication.

Get current user

```
GET /api/users/me
```

Update profile

```
PUT /api/users/me
```

Update password

```
PUT /api/users/me/password
```

Delete account

```
DELETE /api/users/me
```

---

## Admin Endpoints

Requires **ADMIN role**.

Get all users

```
GET /api/admin/users
```

Promote user to admin

```
POST /api/admin/promote
```

---

# Error Handling

The application uses centralized exception handling.

Typical responses:

```
400  Bad Request
401  Unauthorized
403  Forbidden
404  Not Found
409  Conflict
500  Internal Server Error
```

Errors are returned as structured JSON responses.

---

# Running the Project

## Clone repository

```
git clone https://github.com/yourusername/auth-service.git
cd auth-service
```

## Configure database

Edit `application.properties`.

Example:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/authdb
spring.datasource.username=postgres
spring.datasource.password=password

jwt.secret=your-secret-key
jwt.expiration=3600000
```

## Run application

```
mvn spring-boot:run
```

or

```
mvn clean install
java -jar target/auth-service.jar
```

---

# Example Authentication Flow

1. Register user

```
POST /api/auth/register
```

2. Login

```
POST /api/auth/login
```

Response contains:

```
accessToken
refreshToken
```

3. Access protected endpoint

```
Authorization: Bearer <accessToken>
```

4. Refresh token

```
POST /api/auth/refresh
```

---

# Project Structure

```
auth-service
│
├── src
│   └── main
│       ├── java
│       │   └── ir
│       │       └── aspireapps
│       │           └── authservice
│       │
│       │               ├── App.java
│       │
│       │               ├── config
│       │               │   ├── JwtFilter.java
│       │               │   ├── PasswordConfig.java
│       │               │   └── SecurityConfig.java
│       │
│       │               ├── control
│       │               │   ├── AdminController.java
│       │               │   ├── AuthController.java
│       │               │   └── UserController.java
│       │
│       │               ├── dto
│       │               │   ├── auth
│       │               │   │   ├── AuthResponse.java
│       │               │   │   ├── LoginRequest.java
│       │               │   │   └── RefreshTokenRequest.java
│       │               │   │
│       │               │   ├── error
│       │               │   │   └── ApiError.java
│       │               │   │
│       │               │   ├── page
│       │               │   │   └── PageResponse.java
│       │               │   │
│       │               │   └── user
│       │               │       ├── UserDetailsResponse.java
│       │               │       ├── UserRegisterRequest.java
│       │               │       ├── UserUpdateDetailsRequest.java
│       │               │       └── UserUpdatePasswordRequest.java
│       │
│       │               ├── exception
│       │               │   ├── AuthServiceBaseException.java
│       │               │   ├── CustomAccessDeniedHandler.java
│       │               │   ├── CustomAuthenticationEntryPoint.java
│       │               │   ├── DuplicateResourceException.java
│       │               │   ├── GlobalExceptionHandler.java
│       │               │   ├── InvalidInputException.java
│       │               │   ├── InvalidTokenException.java
│       │               │   └── ResourceNotFoundException.java
│       │
│       │               ├── mapper
│       │               │   ├── PageResponseMapper.java
│       │               │   └── UserMapper.java
│       │
│       │               ├── model
│       │               │   ├── RefreshToken.java
│       │               │   ├── Role.java
│       │               │   └── User.java
│       │
│       │               ├── repo
│       │               │   ├── RefreshTokenRepository.java
│       │               │   └── UserRepository.java
│       │
│       │               ├── security
│       │               │   ├── CustomUserDetails.java
│       │               │   ├── CustomUserDetailsService.java
│       │               │   ├── JwtService.java
│       │               │   ├── RefreshTokenService.java
│       │               │   └── TokenHashService.java
│       │
│       │               └── service
│       │                   ├── AuthService.java
│       │                   └── UserService.java
│       │
│       └── resources
│           ├── application.properties
│           └── META-INF
│               └── additional-spring-configuration-metadata.json
```

---

# Future Improvements

Possible improvements:

- Docker containerization
- Email verification
- Rate limiting for authentication endpoints
- Account lockout after repeated login failures
- Audit logging

---

# License

MIT License
