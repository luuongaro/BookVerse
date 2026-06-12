# BookVerse

BookVerse is a backend platform designed for readers and writers to share, discover, and interact with literary content through a unified system 
that combines content creation, social interaction, and reading management.

This project was developed as part of the course *Metodología de Sistemas I* at Universidad Tecnológica Nacional (UTN), 
following IEEE-based software requirements specification guidelines.

## Overview

BookVerse provides a RESTful API that allows users to:

- Register and manage accounts
- Authenticate securely using JWT
- Create and publish original serialized stories
- Read and review both commercial books and platform stories
- Browse and search books using Google Books API integration
- Track reading progress and status
- Participate in reading groups with structured roles and permissions
- Define and follow group reading goals
- Interact with other users through contextual group comments
- Manage roles, reports, and moderation processes

The system is designed as a backend service without a mandatory frontend, enabling flexibility for integration with different clients.

## Functional Scope

The system includes the following main functional areas:

## Functional Scope

The system includes the following main functional areas:

- User authentication and role-based authorization
- Story and chapter management with publishing restrictions based on subscription
- Book metadata integration through Google Books API with local persistence and duplicate prevention
- Reviews and ratings system for books and stories
- Review reporting and moderation workflows
- Reading status and progress tracking with business rules
- Reading groups with membership, roles, comments, and goals
- Subscription management (FREE / PREMIUM with real constraints)
- User interaction features such as tips and group-based discussions (in progress)

## External Integrations

- Google Books API for retrieving external book information and metadata

## Domain Model

The core entities of the system include:

- Books (external reference via Google Books)
- Stories (user-generated content)
- Chapters
- Users
- Roles
- Reviews
- ReviewReport
- ReadingStatus
- Subscriptions
- Tips

### Group-related entities

- ReadingGroup
- GroupMember (CREATOR / MEMBER, ACTIVE / LEFT / REMOVED)
- GroupGoals (group-level reading objectives)
- GroupProgress (calculated, not persisted)
- GroupComment (contextual comments with spoiler filtering)

These entities are connected through structured relationships and enforce business rules such as access control, ownership, and content visibility.

## Reading Groups System
## Reading Groups

- Each group has a creator automatically registered as `CREATOR`
- Other users join as `MEMBER`
- Membership states:
  - ACTIVE
  - LEFT
  - REMOVED
- Only active members can participate in group activities

### Group Comments

- Comments are contextual and linked to reading progress
- Visibility is controlled to prevent spoilers
- Only content up to the user's reading progress is visible

### Group Goals

- Each group can define a single ACTIVE goal at a time
- Goals define a target reading milestone:
  - Percentage (Books or Stories)
  - Chapter number (Stories only)

### Group Progress

- Progress is calculated dynamically (not stored)
- Based on:
  - Active group members
  - Their reading statuses
  - The active group goal
- Ensures consistency without duplicating data

The reading groups module enables collaborative reading experiences with structured roles and rules.


## Architecture

The application follows a layered architecture:

- Controller layer: Handles HTTP requests and responses
- Service layer: Contains business logic
- Repository layer: Manages data persistence using Spring Data JPA
- DTO and Mapper layer: Separates internal models from API contracts using MapStruct

## Development Workflow

The project follows a Git Flow approach with branches:
- main
- develop
- feature/*
  
## Technology Stack

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT Authentication
- Hibernate
- MapStruct
- Lombok
- PostgreSQL
- Maven
- Swagger / OpenAPI
- Aspect-Oriented Programming (AOP)

Tools used during development:

- Postman (API testing)
- JIRA (task management)
- Trello (planning and tracking)

## Problem Statement

Many readers and writers lack a unified platform to manage reading progress, share content, and interact socially around books and stories.

Existing solutions often separate reading tracking, content creation, and community interaction into different applications, leading to a fragmented user experience.

BookVerse aims to centralize these features into a single backend system.

## Key Design Decisions## 

-Design of `Author` entity: User represents both reader and writer roles
- Book entity represents external content (Google Books) and is stored locally to avoid duplication
- Separation between:
  - `Review`: global opinion about a work (Book or Story)
  - `GroupComment`: contextual interaction within reading groups
- Reading progress is centralized in `ReadingStatus`, avoiding duplication across modules
- `GroupProgress` is calculated dynamically based on group goals and member progress
- Implementation of soft delete and status-based lifecycle management in multiple modules
- Use of DTO pattern to isolate persistence models from API responses
- Use of MapStruct for object mapping
- Layered architecture for scalability and maintainability
- Pagination support using Spring Data Pageable
- JWT-based stateless authentication using email as subject
- Role hierarchy (Admin > Moderator > User) implemented via Spring Security
- Automatic initialization of roles, subscriptions, and default admin user

## Non-Functional Requirements

- REST API with consistent naming conventions and standardized responses
- Support for role-based access control (Admin, Moderator, User)
- Persistence using PostgreSQL with referential integrity
- Response time under defined thresholds for queries
- Extensible architecture for future features
- Centralized error handling and logging
- Stateless authentication using JWT
- Secure password storage using BCrypt hashing
- Centralized security exception handling

## Future Improvements

## Future Improvements

- Frontend application development to provide a complete user experience
- Payment integration for subscriptions and content monetization
- Expansion of the Tips feature, including full integration with the system and real use cases
- Advanced recommendation system based on user reading behavior and preferences
- Enhanced analytics for group activity, reading progress, and engagement
- Extended testing coverage (unit and integration tests)
- Performance improvements and caching strategies for external API calls

## Postman Collection

A Postman collection is included in the repository to test the main endpoints of the API.
This allows easy verification of the system without requiring a frontend.

## Security

- BookVerse implements authentication and authorization using Spring Security and JWT (JSON Web Token).
- Fine-grained authorization enforced at service level (ownership, membership, role hierarchy)
  
### Authentication Features

- JWT-based authentication
- Login using email and password
- Stateless session management
- Password encryption using BCrypt
- Protected endpoints with token validation
- Custom `AuthenticationEntryPoint` for consistent `401 Unauthorized` responses

### Role-Based Access Control

The system uses predefined roles:

- ROLE_USER
- ROLE_MODERATOR
- ROLE_ADMIN

Roles are managed using Spring Security authorities and are automatically assigned or validated depending on the authentication flow.

### Automatic Initializers

The application automatically initializes essential security data on startup:

### Default roles
- `ROLE_USER`
- `ROLE_MODERATOR`
- `ROLE_ADMIN`

### Default subscriptions
- `FREE`
- `PREMIUM`

### Default administrator user

A default administrator account is automatically created during application startup.

## Public Endpoints

The following endpoints are accessible without authentication:

- `/api/auth/login`
- `/api/auth/register`
- Swagger/OpenAPI endpoints

### Protected Endpoints

All remaining API endpoints require a valid JWT token.

### JWT Authentication Flow

1. User registers or logs in
2. Backend validates the provided credentials
3. A JWT token is generated and returned
4. The token must be included in the `Authorization` header for protected endpoints

Example:

```http
Authorization: Bearer <token>
```

## Getting Started

### Prerequisites
- Java 17+
- Maven
- PostgreSQL

### Installation

1. Clone the repository:

    git clone https://github.com/luuongaro/BookVerse.git

2. Navigate to the project folder:

    cd BookVerse

3. Configure environment variables:

- Database credentials
- JWT configuration
- Google Books API credentials
- Default administrator credentials

4. Run the application:

    mvn spring-boot:run

5. Access the API at:

    http://localhost:8080
   
6. Access Swagger UI:
    http://localhost:8080/swagger-ui/index.html
   
## Repository
https://github.com/luuongaro/BookVerse
