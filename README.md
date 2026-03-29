# Smart Expense Tracker

A desktop application built with Java Swing and SQLite for tracking personal expenses with predictive analytics.

## Features

- **Expense Management**: Add, view, and track expenses with date, category, and amount
- **Predictive Analytics**: Linear regression-based prediction for next month's expenses
- **Interactive Dashboard**: Real-time summary with total expenses and predictions
- **SQLite Database**: Persistent data storage using JDBC
- **Modern UI**: Clean and intuitive Swing-based graphical interface

## Technologies Used

- **Java 8**: Core programming language
- **Java Swing**: GUI framework
- **SQLite**: Embedded database
- **JDBC**: Database connectivity
- **Maven**: Build and dependency management
- **JFreeChart**: Data visualization library

## Project Structure

```
smart-expense-tracker/
├── pom.xml                          # Maven configuration
├── expenses.db                       # SQLite database
└── src/main/java/com/tracker/
    ├── Main.java                     # Application entry point
    ├── model/
    │   ├── Expense.java             # Expense data model
    │   └── AnalysisResult.java      # Analysis result model
    ├── database/
    │   └── DatabaseHandler.java     # Database operations
    ├── service/
    │   └── PredictionService.java   # Prediction algorithm
    └── ui/
        ├── DashboardPanel.java      # Main dashboard UI
        └── ExpenseFormDialog.java   # Add expense dialog
```

## Architecture

The application follows the **MVC (Model-View-Controller)** pattern:

- **Model Layer**: `Expense.java`, `AnalysisResult.java` - Data models
- **Database Layer**: `DatabaseHandler.java` - SQLite integration
- **Service Layer**: `PredictionService.java` - Business logic and predictions
- **View Layer**: `DashboardPanel.java`, `ExpenseFormDialog.java` - UI components
- **Controller**: `Main.java` - Application initialization and coordination

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven 3.6 or higher

## Installation & Setup

1. Clone or download the project:
```bash
cd smart-expense-tracker
```

2. Build the project:
```bash
mvn clean compile
```

3. Package the application:
```bash
mvn package
```

## Running the Application

Execute the application using Maven:
```bash
mvn exec:java
```

Or run the compiled JAR:
```bash
java -jar target/smart-expense-tracker-1.0-SNAPSHOT.jar
```

## Usage

1. **Adding Expenses**:
   - Click the "Add Expense" button
   - Enter date (YYYY-MM-DD format)
   - Select category (Food, Travel, Bills, Other)
   - Enter amount
   - Click "Save"

2. **Viewing Expenses**:
   - All expenses are displayed in the table
   - View total expenses in the summary panel

3. **Predictions**:
   - The system automatically calculates predicted expenses for the next month
   - Prediction uses linear regression on historical data
   - Requires at least 2 months of data for accurate predictions

## Database Schema

**expenses** table:
- `id` (INTEGER PRIMARY KEY AUTOINCREMENT)
- `date` (TEXT) - Format: YYYY-MM-DD
- `category` (TEXT) - Expense category
- `amount` (REAL) - Expense amount

## Prediction Algorithm

The application implements a manual linear regression algorithm:
- Groups expenses by month
- Calculates trend using least squares method
- Predicts next month's total expenses
- Formula: y = mx + b (where x is month number, y is total expense)

## Dependencies

```xml
<dependencies>
    <!-- SQLite JDBC Driver -->
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.45.0.0</version>
    </dependency>
    
    <!-- JFreeChart for visualization -->
    <dependency>
        <groupId>org.jfree</groupId>
        <artifactId>jfreechart</artifactId>
        <version>1.5.4</version>
    </dependency>
</dependencies>
```

## Future Enhancements

- Category-wise expense breakdown charts
- Export data to CSV/PDF
- Budget setting and alerts
- Monthly/yearly expense reports
- Multi-user support
- Dark mode theme

## Author

MOHIT POONIA
University Project - Smart Expense Tracker

## License

This is an educational project developed for university coursework.
