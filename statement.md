# Smart Expense Tracker - Project Statement

## Project Overview

**Project Name**: Smart Expense Tracker  
**Technology Stack**: Java Swing (GUI) + SQLite (Database) + Maven (Build Tool)  
**Architecture**: MVC (Model-View-Controller)  
**Development Date**: November 2025

## Objective

To develop a desktop application that helps users track their daily expenses and provides predictive analytics for future spending patterns using linear regression algorithms.

## Problem Statement

Personal finance management is crucial for individuals to maintain financial stability. Many people struggle to:
- Keep track of their daily expenses
- Understand spending patterns
- Plan future budgets based on historical data

This application addresses these challenges by providing an easy-to-use interface for expense tracking with intelligent prediction capabilities.

## Functional Requirements

### 1. Expense Management
- Add new expenses with date, category, and amount
- View all expenses in a tabular format
- Store expenses persistently in a local database
- Validate input data (date format, positive amounts)
- Support multiple expense categories (Food, Travel, Bills, Other)

### 2. Data Persistence
- Use SQLite database for local storage
- Implement JDBC for database connectivity
- Create database schema automatically on first run
- Handle database errors gracefully

### 3. Predictive Analytics
- Implement linear regression algorithm manually (without ML libraries)
- Group expenses by month
- Calculate predicted expenses for the next month
- Display predictions in the dashboard

### 4. User Interface
- Modern and intuitive Swing-based GUI
- Dashboard showing summary and expense table
- Modal dialog for adding new expenses
- Responsive design with proper sizing and spacing
- Color-coded buttons for better UX

## Non-Functional Requirements

### 1. Performance
- Fast database operations
- Smooth UI interactions
- Efficient data loading and refresh

### 2. Usability
- Intuitive navigation
- Clear error messages
- Default values for convenience (today's date)
- Input validation feedback

### 3. Reliability
- Proper exception handling
- Data integrity maintenance
- Graceful shutdown with database cleanup

### 4. Maintainability
- Clean code structure
- MVC architecture for separation of concerns
- Modular design for easy extensions

## Technical Architecture

### Layer 1: Model Layer
- **Expense.java**: POJO representing expense entity
- **AnalysisResult.java**: POJO for analysis results
- Contains getters, setters, and constructors
- No business logic

### Layer 2: Database Layer
- **DatabaseHandler.java**: Manages SQLite operations
- Methods: `initialize()`, `addExpense()`, `getAllExpenses()`, `deleteExpense()`, `close()`
- JDBC-based implementation
- Connection pooling and resource management

### Layer 3: Service Layer
- **PredictionService.java**: Business logic for predictions
- Manual implementation of linear regression
- Groups expenses by month (YYYY-MM format)
- Calculates slope (m) and intercept (b) for prediction line
- Formula: `nextMonthExpense = m × (n+1) + b`

### Layer 4: View Layer (UI)
- **DashboardPanel.java**: Main application view
  - Summary panel with total and prediction
  - JTable for displaying expenses
  - Action buttons (Add Expense, Refresh)
  
- **ExpenseFormDialog.java**: Modal dialog for adding expenses
  - Input fields with validation
  - Category dropdown
  - Date picker (text field with format validation)
  - Save/Cancel buttons

### Layer 5: Controller Layer
- **Main.java**: Application entry point
  - Initializes database
  - Sets up main frame
  - Configures Look and Feel
  - Handles window events

## Algorithm: Linear Regression for Prediction

### Mathematical Approach

Given historical expense data grouped by month:
```
Month 1: Total = x₁
Month 2: Total = x₂
...
Month n: Total = xₙ
```

Calculate slope (m) and intercept (b):
```
m = (n × Σxy - Σx × Σy) / (n × Σx² - (Σx)²)
b = (Σy - m × Σx) / n
```

Predict next month:
```
prediction = m × (n + 1) + b
```

Where:
- x = month number (1, 2, 3, ...)
- y = total expense for that month
- n = number of months with data

## Database Schema

### Table: expenses

| Column   | Type    | Constraints               | Description           |
|----------|---------|---------------------------|-----------------------|
| id       | INTEGER | PRIMARY KEY AUTOINCREMENT | Unique identifier     |
| date     | TEXT    | NOT NULL                  | Expense date (YYYY-MM-DD) |
| category | TEXT    | NOT NULL                  | Expense category      |
| amount   | REAL    | NOT NULL                  | Expense amount ($)    |

## User Interface Design

### Main Window (800 x 600)
```
┌─────────────────────────────────────┐
│   Smart Expense Tracker             │
├─────────────────────────────────────┤
│  Total Expenses: $XXXX.XX           │
│  Predicted Next Month: $XXXX.XX     │
├─────────────────────────────────────┤
│ ID │ Date       │ Category │ Amount │
├────┼────────────┼──────────┼────────┤
│ 1  │ 2025-11-24 │ Food     │ $50.00 │
│ 2  │ 2025-11-23 │ Travel   │ $30.00 │
│ ...│ ...        │ ...      │ ...    │
├─────────────────────────────────────┤
│  [Add Expense]      [Refresh]       │
└─────────────────────────────────────┘
```

### Add Expense Dialog (500 x 400)
```
┌─────────────────────────────┐
│   Add New Expense           │
├─────────────────────────────┤
│ Date (YYYY-MM-DD):          │
│ [2025-11-24              ]  │
│                             │
│ Category:                   │
│ [Food ▼                  ]  │
│                             │
│ Amount ($):                 │
│ [                        ]  │
│                             │
│    [Save]      [Cancel]     │
└─────────────────────────────┘
```

## Technologies Justification

### Java Swing
- Mature and stable GUI framework
- Cross-platform compatibility
- Rich component library
- No additional runtime required

### SQLite
- Lightweight embedded database
- No server setup required
- Single file storage
- ACID compliant
- Perfect for desktop applications

### Maven
- Standard Java build tool
- Dependency management
- Consistent project structure
- Easy compilation and packaging

### Manual Linear Regression
- Educational value (understanding ML concepts)
- No external ML library dependencies
- Lightweight implementation
- Sufficient for simple predictions

## Testing Strategy

### Unit Testing
- Test database operations
- Test prediction algorithm with sample data
- Validate input validation logic

### Integration Testing
- Test UI components with database
- Test end-to-end expense addition flow

### Manual Testing
- UI responsiveness
- Button functionality
- Data persistence
- Error handling

## Deliverables

1. Source code with proper package structure
2. Maven pom.xml with dependencies
3. Compiled application (JAR file)
4. SQLite database file
5. Documentation (README.md)
6. This project statement

## Conclusion

The Smart Expense Tracker successfully demonstrates:
- Java Swing GUI development
- Database integration using JDBC
- Implementation of predictive algorithms
- MVC architecture
- Maven project management

This project serves as a practical application of software engineering principles and provides a foundation for further enhancements in personal finance management tools.
