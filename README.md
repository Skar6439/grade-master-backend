# GradeMaster Backend API

**Student:** BOUCKAMBA M. Aimé
**Student ID:** 27242
**Course:** INSY 8322 Web Technology and Internet
**Project:** GradeMaster Mid-Term Project Examination

## Steps to Run the API

1. **Database Setup:**
    -Create a PostgreSQL database naamed `grade_master`.
    -Update the `spring.datasource.username` and `spring.datasource.password` in `src/main/resources/application.properties` to match your local PostgreSQL credentials.

2. **Run the Application:**
    -Open the project in your IDE (e.g., Vs Code).
    -Run the `GradeMasterApplication.java` main class.
    -The application will start on `http://localhost:8080`.
    -Hibernate will automatically create the required table due to `spring.jpa.hibernate.dll-auto=update`.

3. **Testing:**
    -Import the provide `grade-master-midterm.postman_collection.json` into Postman.
    -Execute the endpoints to test the features (Part A to Part F + Bonus).