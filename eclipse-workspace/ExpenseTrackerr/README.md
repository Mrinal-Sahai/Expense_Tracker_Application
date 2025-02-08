# Expense Tracker Application

## 📌 Overview
The **Expense Tracker** is a JavaFX-based desktop application that allows users to track and manage their expenses efficiently. It supports a multi-user environment, where each user can log in and maintain a personalized expense list. The application provides insights through graphical representations like pie charts and bar graphs for better financial analysis.

## 🚀 Features
- Multi-user support 
- Add and delete expense records
- Categorized expense tracking (date, category, amount, description)
- Graphical analysis:
  - **Pie Chart:** Category-wise monthly expenses
  - **Bar Graph:** Monthly expense trends
- MySQL database integration with Hibernate
- JavaFX Scene Builder for an intuitive GUI (Don't Judge me with UI , I am not too good at it)

## 🛠️ Tech Stack
- **Frontend:** JavaFX with Scene Builder
- **Backend:** Java (Spring Boot for business logic)
- **Database:** MySQL with JPA & Hibernate
- **IDE:** Eclipse
- **Build Tool:** Maven

## 🔧 Setup & Installation
### Prerequisites
Ensure you have the following installed:
- Java JDK (17 or later)
- Eclipse IDE with JavaFX plugins
- MySQL Server
- Maven

### Steps to Set Up
1. Clone the repository:
2. Navigate to the project directory:
3. Set up the database:
   - Create a MySQL database: `expense_tracker`
   - Update `hibernate.cfg.xml` with your MySQL credentials.
   
4. Build and run the application:
   ```sh
   mvn clean install
   mvn javafx:run
   ```

## 📂 Project Structure
```
expense-tracker/
│── src/main/java/
│   ├── com.expensetracker/
│   │   ├── model/        # Entity classes (Expense, Transaction)
│   │   ├── dao/          # Database operations using JPA
│   │   ├── util/         # Hibernate Utility Setup
│   │   ├── controller/   # Handles UI interaction
│   │   ├── main          # Main entry point (JavaFX Application)
│── src/main/resources/
│   ├── hibernate.cfg.xml       # Database and app configuration
│   ├── fxml/                   # FXML UI files
│── pom.xml                     # Maven dependencies
```


## 🤝 Contributing
Contributions are welcome! Feel free to open issues or submit pull requests.

## 📜 License
This project is licensed under the MIT License.

---
✉️ For any queries, reach out to [mrinal.sahai2020@gmail.com]

