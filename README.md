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
