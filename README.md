# Car Details Application

A Spring Boot web application for managing car details with role-based access control. The application allows data entry users to add and view their own car records, while admin users can view all records.

## Features

- **User Authentication**: Login with username and password
- **Role-Based Access Control**: Data Entry User and Admin roles
- **Car Management**: Add, view, and manage car details (make, model, year)
- **MySQL Database**: Persistent data storage
- **Responsive Web Interface**: HTML/CSS/Thymeleaf templates
- **REST API**: JSON endpoint for vehicle retrieval

## Technology Stack

- Java 17
- Spring Boot 3.1.5
- Spring Web
- Spring Data JPA
- Thymeleaf
- MySQL 8.0+
- Maven

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6+

## Database Setup

1. Create the database and tables using the provided SQL script:

```bash
mysql -u root < database/init.sql
```

Or manually:

```sql
CREATE DATABASE car_details_db;
USE car_details_db;

-- Run all commands in database/init.sql
```

## Configuration

Update the database connection details in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/car_details_db
spring.datasource.username=root
spring.datasource.password=your_password
```

## Building the Project

```bash
mvn clean install
```

## Running the Application

### Using Maven
```bash
mvn spring-boot:run
```

### Using JAR
```bash
mvn clean package
java -jar target/car-details-app-1.0.0.jar
```

The application will start on `http://localhost:8080`

## Default Credentials

### Data Entry User
- Username: `data`
- Password: `1234`

### Admin User
- Username: `admin`
- Password: `1234`

## API Endpoints

### Authentication
- `GET /login` - Login page
- `POST /login` - Process login
- `GET /logout` - Logout

### Car Operations
- `GET /car-form` - Car entry form
- `POST /car/add` - Add new car
- `GET /my-cars` - View user's cars
- `GET /all-cars` - View all cars (Admin only)
- `GET /vehicles/all` - REST API returning JSON

## File Structure

```
CarDetailsApp/
├── src/
│   ├── main/
│   │   ├── java/com/cardetails/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   ├── controller/
│   │   │   └── CarDetailsApplication.java
│   │   └── resources/
│   │       ├── templates/
│   │       ├── static/css/
│   │       └── application.properties
│   └── test/
├── database/
│   └── init.sql
├── scripts/
│   └── deploy.sh
├── pom.xml
└── README.md
```

## User Workflows

### Data Entry User
1. Login with username `data` and password `1234`
2. Fill the car details form
3. Submit to add a new car
4. View only own records
5. Logout

### Admin User
1. Login with username `admin` and password `1234`
2. Fill the car details form
3. Submit to add a new car
4. View all records from all users
5. See "Entered By" column showing the creator
6. Logout

## Linux Deployment

Use the provided deployment script to deploy on Linux:

```bash
bash scripts/deploy.sh
```

This script will:
1. Change ownership of the JAR file to the `openlink` user
2. Create `/srv` directory if needed
3. Move the JAR file to `/srv` directory

## Testing

1. Start the application
2. Test login with both user credentials
3. Add car records with each user
4. Verify data entry user can only see own records
5. Verify admin can see all records with creator info
6. Test logout functionality
7. Test REST API: `curl http://localhost:8080/vehicles/all`

## Troubleshooting

### Database Connection Error
- Check MySQL is running
- Verify database exists: `car_details_db`
- Verify credentials in `application.properties`
- Check database initialization script was run

### Login Issues
- Verify users exist in database
- Check credentials: `data/1234` and `admin/1234`
- Clear browser session/cookies

### Port Already in Use
- Change port in `application.properties`: `server.port=8081`

## Contributing

Submit code changes via GitHub pull requests.

## License

This is a sample project for educational purposes.

## Support

For issues or questions, contact: aptdevteam@gmail.com
