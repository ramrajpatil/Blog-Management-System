# Blog Management System

## Overview
The Blog Management System is a web application designed to facilitate the creation, management, and interaction with blog posts across various categories. This project was developed using React for the frontend and Spring Boot for the backend. Secure JWT authentication is implemented to ensure user data and interactions are protected.

## Features
- **User Authentication**: Secure login and registration using JWT tokens.
- **Post Management**: Create, read, update, and delete blog posts.
- **Comment Management**: Add, read, update, and delete comments on posts.
- **User Management**: Handle user registrations and permissions.

## User Roles and Permissions
- **Admins**: Have permissions to delete and update posts and comments.
- **Users**: Can access and use APIs relevant to their level (create, read, and comment on posts).

## Technologies Used
- **Frontend**: React
- **Backend**: Spring Boot
- **Authentication**: JWT (JSON Web Tokens)

## Getting Started

### Prerequisites
- Node.js
- Java JDK
- Spring Boot
- MySQL or any preferred database

### Installation

#### Frontend
1. Navigate to the `frontend` directory.
2. Install dependencies:
   ```bash
   npm install
   ```

#### Start the development server:
```bash
npm start
```

#### Backend
1. Navigate to the `backend` directory.
2. Configure the database settings in application.properties.
3. Build the project:
```bash
mvn clean install
```
4. Run the Spring Boot application:
```bash
mvn spring-boot:run
```

## API Endpoints

### Authentication
- `POST /api/auth/register`: Register a new user.
- `POST /api/auth/login`: Login and retrieve a JWT token.

### Posts
- `GET /api/posts`: Retrieve all posts.
- `GET /api/posts/{postId}`: Retrieve a single post by ID.
- `POST /api/posts`: Create a new post.
- `PUT /api/posts/{postId}`: Update an existing post.
- `DELETE /api/posts/{postId}`: Delete a post.

### Comments
- `GET /api/posts/{postId}/comments`: Retrieve all comments for a post.
- `POST /api/posts/{postId}/comments`: Add a comment to a post.
- `PUT /api/comments/{cId}`: Update a comment.
- `DELETE /api/comments/{cId}`: Delete a comment.

## Contributing
- **Contributions** are welcome! Please fork the repository and create a pull request with your changes.

## E-R Diagram
![E-R-Diagram](https://github.com/ramrajpatil/Blog-Management-System/assets/170900166/1c5ac4a9-9383-45e7-b1ed-877d657a3a43)

<img src="https://github.com/ramrajpatil/Blog-Management-System/assets/170900166/1c5ac4a9-9383-45e7-b1ed-877d657a3a43" alt="E-R Diagram" width="600">
