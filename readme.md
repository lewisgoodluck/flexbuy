# 🛍️ FlexBuy E-Commerce Platform

A modern **e-commerce REST API** built with **Spring Boot 3**, **PostgreSQL**, and **Docker**. FlexBuy provides comprehensive functionality for managing products, users, orders, and authentication with role-based access control.

## ✨ Features

- 🔐 **JWT Authentication & Authorization**
- 👥 **Role-based Access Control** (Admin, User)
- 🛒 **Product Management** (CRUD operations)
- 📦 **Order Processing & Management**
- 🔍 **Product Search & Filtering**
- 📊 **RESTful API** with comprehensive endpoints
- 🐳 **Fully Containerized** with Docker Compose
- 📚 **Interactive API Documentation** with Swagger UI

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.x, Spring Security, Spring Data JPA
- **Database**: PostgreSQL 15
- **Authentication**: JWT (JSON Web Tokens)
- **Documentation**: Swagger/OpenAPI 3
- **Containerization**: Docker & Docker Compose
- **Build Tool**: Maven
- **Java Version**: 17+

---

## 🚀 Quick Start

### Prerequisites

- ☕ **Java 17+**
- 🔧 **Maven** (or use included wrapper `./mvnw`)
- 🐳 **Docker & Docker Compose**

### Installation & Setup

```bash
# 1. Clone the repository
git clone https://github.com/techforcetz/flexbuy.git
cd flexbuy

# 2. Build the application
./mvnw clean package -DskipTests

# 3. Start with Docker Compose
docker compose up --build
```

**🎉 That's it!** The application will be running at **http://localhost:8080**

---

## 📚 API Documentation

### 🌟 Interactive API Explorer

Once the application is running, visit the **Swagger UI** to explore all available APIs:

**👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

The Swagger interface provides:
- ✅ **Complete API documentation** with request/response examples
- 🧪 **Interactive testing** - try APIs directly from the browser
- 🔍 **Schema definitions** for all request/response models
- 🔐 **Authentication testing** with JWT tokens
- 📋 **Ready-to-use code examples** in multiple languages

### What You'll Find in Swagger UI

- **Authentication APIs** - Register, login, refresh tokens
- **Product Management** - Browse, create, update, delete products
- **Order Processing** - Create orders, track status, view history
- **User Management** - Profile management and user operations
- **Admin Operations** - Administrative functions and reporting


> **Note**: The Swagger UI is intentionally left unprotected for development purposes, allowing easy API exploration and testing.
> **Note** - 'Forgot password' functionality will not work unless proper email + password configurations are set in the application.properties

### Additional Documentation Endpoints
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **OpenAPI YAML**: http://localhost:8080/v3/api-docs.yaml

---

## 🔐 Authentication Flow

FlexBuy uses **JWT (JSON Web Tokens)** for secure authentication:

1. **Register/Login** → Receive JWT access token
2. **Include token** in requests: `Authorization: Bearer <token>`
3. **Access protected endpoints** based on your role (USER/ADMIN)

### Quick Authentication Test
1. Go to Swagger UI: http://localhost:8080/swagger-ui.html
2. Find the **Auth Controller** section
3. Try the `/api/v1/auth/login` endpoint
4. Use the returned token in the "Authorize" button (🔒 icon)
5. Now you can test protected endpoints!

---


## 🐳 Docker Commands

```bash
# Start all services
docker compose up --build

# Start in background
docker compose up -d

# Stop services
docker compose down

# Stop and reset database
docker compose down -v

# View logs
docker compose logs -f

# View specific service logs
docker compose logs -f flexbuy-app
```

---
## 🐘 Database Information

- **Database**: PostgreSQL 15
- **Host**: localhost:5433 (external), postgres:5432 (internal)
- **Database Name**: flexbuy_db
- **Auto-schema**: Tables created automatically on startup

---

## 🛡️ Security Features

- **JWT Token Authentication** with configurable expiration
- **Role-based Authorization** (USER, ADMIN roles)
- **CORS Configuration** for cross-origin requests
- **Custom Error Handling** with proper JSON responses
- **Request Validation** with comprehensive error messages

---

## 🚀 Development Notes

### Hot Reload Development
For development with hot reload:
```bash
# Run database only
docker compose up postgres

# Run Spring Boot with Maven (auto-restart on changes)
./mvnw spring-boot:run
```

### Testing the APIs
1. **Start the application** (as shown above)
2. **Open Swagger UI** at http://localhost:8080/swagger-ui.html
3. **Create an account** using the registration endpoint
4. **Login** to get your JWT token
5. **Authorize** in Swagger UI using the token
6. **Explore and test** all available endpoints

### Key Endpoints to Try First
- `POST /api/v1/auth/register` - Create your account
- `POST /api/v1/auth/login` - Get your JWT token
- `GET /api/v1/product/public` - Browse products (no auth needed)
- `POST /api/v1/orders` - Create an order (requires auth)

---


## 📞 Support & Resources

- **API Documentation**: http://localhost:8080/swagger-ui.html (when running)
- **GitHub Issues**: Report bugs or request features
- **OpenAPI Spec**: Download complete API specification from Swagger UI

---

**Happy Coding! 🚀** Start by visiting the Swagger UI to explore all the available APIs.