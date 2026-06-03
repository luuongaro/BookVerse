# BookVerse

BookVerse is a backend-based platform designed for readers and writers to share, discover, and interact with literary content through a unified system 
that combines content creation, social interaction, and reading management.

This project was developed as part of the course *Metodología de Sistemas I* at Universidad Tecnológica Nacional (UTN), 
following IEEE-based software requirements specification guidelines.

## Overview

BookVerse provides a RESTful API that allows users to:

- Register and manage accounts
- Create and publish original stories
- Browse and review books and stories
- Participate in reading groups
- Track reading progress and status
- Interact with other users through comments and tips
- Manage roles, reports, and moderation processes

The system is designed as a backend service without a mandatory frontend, allowing access through API clients such as Postman.

## Functional Scope

The system includes the following main functional areas:

- User authentication and role management
- Story and chapter management
- Book metadata consultation
- Reviews and ratings system
- Review reporting and moderation
- Reading status and progress tracking
- Reading groups, membership, goals, and comments
- Subscription management
- Tips (user-to-user interaction)

## Domain Model

The core entities of the system include:

- Authors
- Books
- Stories
- Chapters
- Users
- Roles
- Reviews
- ReviewReport
- Status (reading status)
- Subscriptions
- Tips

### Group-related entities

- ReadingGroups
- GroupMember
- GroupGoals
- GroupProgress
- GroupComment

These entities are connected through bidirectional relationships and represent the full domain model described in the conceptual design.

## Architecture

The application follows a layered architecture:

- Controller layer: Handles HTTP requests and responses
- Service layer: Contains business logic
- Repository layer: Manages data persistence using Spring Data JPA
- DTO and Mapper layer: Separates internal models from API contracts using MapStruct

  
## Technology Stack

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MapStruct
- Lombok
- PostgreSQL
- Maven

Tools used during development:

- Postman (API testing)
- JIRA (task management)
- Trello (planning and tracking)

## Key Design Decisions

- Use of DTO pattern to isolate persistence models from API responses
- Implementation of MapStruct for object mapping
- Layered architecture for scalability and maintainability
- Use of bidirectional relationships between entities
- Inclusion of `idExternal` as part of entity identification strategy
- Exception handling centralized using global handlers


## Non-Functional Requirements

- REST API with consistent naming conventions and standardized responses
- Support for role-based access control (Admin, Moderator, User)
- Persistence using PostgreSQL with referential integrity
- Response time under defined thresholds for queries
- Extensible architecture for future features
- Centralized error handling and logging

## Future Improvements

- Integration with Google Books API for extended book metadata
- Implementation of authentication and authorization (Spring Security)
- API documentation (Swagger/OpenAPI)
- Automated testing (unit and integration tests)
- Containerization with Docker


## Repository

https://github.com/luuongaro/BookVerse

