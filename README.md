# Online Quiz/Test Platform

A comprehensive quiz platform built with Java Spring Boot, React, and MySQL, featuring AI-powered question generation using Azure OpenAI.

## Features

### Core Features
- **User Authentication**: JWT-based authentication with role-based access control
- **Quiz Management**: Create, edit, and manage quizzes with multiple question types
- **Quiz Taking**: Interactive quiz interface with timer and progress tracking
- **Results & Analytics**: Detailed results with performance tracking
- **Leaderboard**: Global rankings and competitive features

### Advanced Features
- **AI Question Generation**: Auto-generate questions using Azure OpenAI
- **Timer System**: Real-time countdown with auto-submit
- **Performance Analytics**: User progress tracking and insights
- **Admin Dashboard**: Comprehensive management interface
- **Responsive Design**: Mobile-friendly interface

## Technology Stack

### Backend
- **Framework**: Spring Boot 3.2.0
- **Security**: Spring Security + JWT
- **Database**: MySQL 8.x with JPA/Hibernate
- **AI Integration**: Azure OpenAI SDK
- **Build Tool**: Maven

### Frontend
- **Framework**: React 18 with TypeScript support
- **UI Library**: Material-UI (MUI)
- **State Management**: React Query + Context API
- **Routing**: React Router v6
- **HTTP Client**: Axios

### Database
- **Primary**: MySQL 8.x
- **ORM**: Spring Data JPA
- **Migration**: Hibernate DDL

## Project Structure

```
QuizGenerator/
├── backend/                 # Spring Boot application
│   ├── src/main/java/com/quizplatform/
│   │   ├── entity/         # JPA entities
│   │   ├── dto/            # Data transfer objects
│   │   ├── repository/     # Data repositories
│   │   ├── service/        # Business logic
│   │   ├── controller/     # REST controllers
│   │   ├── security/       # Security configuration
│   │   └── config/         # Application configuration
│   └── src/main/resources/
│       └── application.yml # Configuration file
├── frontend/               # React application
│   ├── src/
│   │   ├── components/     # Reusable components
│   │   ├── pages/          # Page components
│   │   ├── services/       # API services
│   │   ├── context/        # React contexts
│   │   └── utils/          # Utility functions
│   └── package.json
└── database/
    └── schema.sql          # Database schema
```

## Setup Instructions

### Prerequisites
- Java 17+
- Node.js 16+
- MySQL 8.x
- Maven 3.6+
- Azure OpenAI account (optional)

### Backend Setup

1. **Database Setup**
   ```bash
   mysql -u root -p < database/schema.sql
   ```

2. **Configure Application**
   Update `backend/src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/quiz_platform
       username: your_username
       password: your_password
   
   azure:
     openai:
       endpoint: your_azure_openai_endpoint
       api-key: your_api_key
       deployment-name: your_deployment_name
   ```

3. **Run Backend**
   ```bash
   cd backend
   mvn spring-boot:run
   ```

### Frontend Setup

1. **Install Dependencies**
   ```bash
   cd frontend
   npm install
   ```

2. **Start Development Server**
   ```bash
   npm start
   ```

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration

### Quiz Management
- `GET /api/quizzes` - Get all active quizzes
- `GET /api/quizzes/{id}` - Get quiz by ID
- `POST /api/quizzes` - Create quiz (Admin)
- `PUT /api/quizzes/{id}` - Update quiz (Admin)
- `DELETE /api/quizzes/{id}` - Delete quiz (Admin)

### Quiz Attempts
- `POST /api/attempts/start/{quizId}` - Start quiz attempt
- `PUT /api/attempts/{id}/submit` - Submit quiz answers
- `GET /api/attempts/{id}` - Get attempt results
- `GET /api/attempts/leaderboard` - Get leaderboard

### AI Generation
- `POST /api/genai/generate-questions` - Generate questions (Admin)

## Default Credentials

- **Admin**: username: `admin`, password: `password`
- **User**: username: `john_doe`, password: `password`

## Features Implementation

### Authentication & Authorization
- JWT token-based authentication
- Role-based access control (USER, ADMIN)
- Secure password hashing with BCrypt

### Quiz System
- Multiple question types (Multiple Choice, True/False)
- Difficulty levels (Easy, Medium, Hard)
- Category-based organization
- Timer functionality with auto-submit

### AI Integration
- Azure OpenAI integration for question generation
- Customizable difficulty and topic selection
- JSON-based response parsing

### Performance Features
- Database indexing for optimal queries
- React Query for efficient data fetching
- Lazy loading for better performance

## Development Guidelines

### Backend
- Follow Spring Boot best practices
- Use DTOs for API communication
- Implement proper exception handling
- Write unit tests for services

### Frontend
- Use functional components with hooks
- Implement proper error boundaries
- Follow Material-UI design patterns
- Optimize for mobile responsiveness

### Database
- Use proper foreign key constraints
- Implement database indexing
- Follow normalization principles

## Deployment

### Backend Deployment
1. Build JAR file: `mvn clean package`
2. Deploy to server with Java 17+
3. Configure production database
4. Set environment variables for Azure OpenAI

### Frontend Deployment
1. Build production bundle: `npm run build`
2. Deploy to web server (Nginx, Apache)
3. Configure API endpoint URLs

## Contributing

1. Fork the repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request

## License

This project is licensed under the MIT License.