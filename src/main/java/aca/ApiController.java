package aca;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aca.fulton.spring.Student;
import aca.fulton.spring.StudentDao;
import aca.fulton.spring.StudentTemp;
import aca.fulton.spring.StudentTempDao;
import aca.fulton.spring.StudentTransactions;
import aca.fulton.spring.StudentTransactionsDao;
import aca.fulton.spring.StudentTransactionsTemp;
import aca.fulton.spring.StudentTransactionsTempDao;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ApiController {

    private final ApiService apiService;
    private final StudentTempDao studentTempDao;
    private final StudentTransactionsTempDao studentTransactionsTempDao;

    public ApiController(ApiService apiService, StudentTempDao studentTempDao, StudentTransactionsTempDao studentTransactionsTempDao) {
        this.apiService = apiService;
        this.studentTempDao = studentTempDao;
        this.studentTransactionsTempDao = studentTransactionsTempDao;
    }

    @GetMapping("/getStudents")
    public String getStudents() {

        // Define the input format (current format of the 'date' string)
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        // Define the output format (desired format for insert)
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        int insertedStudents = 0, insertedTran = 0, errors = 0;
        long startTime = System.currentTimeMillis(); // Start time

        // Empties both tables before making request to the API
        studentTransactionsTempDao.vaciarTabla();
        studentTempDao.vaciarTabla();

        try {
            // Fetch students and their transactions from the new API endpoint
            List<StudentTemp> students = apiService.fetchDataFromApi("https://glapi.adventist.org.au/api/Students/StudentsWithTransactions");

            for (StudentTemp student : students) {
                // Insert or update student
                if (!studentTempDao.existeReg(student.getId())) {
                    studentTempDao.insertReg(student);
                    insertedStudents++;
                } else {
                    studentTempDao.updateReg(student);
                }

                // Checks if student id is synchronized with DB
                if (studentTempDao.estaSincronizado(student.getStudentCode())) {
                    student.setSync(true);
                }

                // Update student balance and dc
                studentTempDao.updateReg(student);

                // Handle transactions
                if (student.getTransactions() != null) {
                    for (StudentTransactionsTemp transaction : student.getTransactions()) {
                        // Format transaction date
                        LocalDateTime dateTime = LocalDateTime.parse(transaction.getDate(), inputFormatter);
                        transaction.setDate(dateTime.format(outputFormatter));

                        // Insert the transaction into the database
                        if (studentTempDao.insertTransaction(student.getId(), transaction)) {
                            insertedTran++;
                        } else {
                            errors++;
                        }
                    }
                }
            }

            long endTime = System.currentTimeMillis(); // End time
            long elapsedTime = endTime - startTime; // Calculate elapsed time

            return "Connection successful. Students have been processed. I.S: " + insertedStudents + " - I.T: " + insertedTran + " - Errors: " + errors + ". Elapsed time: " + elapsedTime + " ms";

        } catch (Exception e) {
            long endTime = System.currentTimeMillis(); // End time
            long elapsedTime = endTime - startTime; // Calculate elapsed time

            return "Connection failed. Error: " + e.getMessage() + ". Elapsed time: " + elapsedTime + " ms";
        }
    }
}