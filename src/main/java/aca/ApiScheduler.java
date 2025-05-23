package aca;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import aca.fulton.spring.StudentDao;
import aca.fulton.spring.StudentTransactionsDao;

@Component
@Profile("fultonProduction")  // This will ensure the scheduler only runs when the profile is "production"
public class ApiScheduler {

    private final ApiController apiController;

    private final StudentDao studentDao;

    private final StudentTransactionsDao studentTransactionsDao;

    public ApiScheduler(ApiController apiController, StudentDao studentDao, StudentTransactionsDao studentTransactionsDao) {
        this.apiController = apiController;
        this.studentDao = studentDao;
        this.studentTransactionsDao = studentTransactionsDao;
    }

    // Schedule this to run every 2 hours (7200000 ms = 2 hours)
    @Scheduled(fixedRate = 3600000)
    public void scheduledApiCall() {

        System.out.println("STARTED SYNC WITH API");

        // Call the controller method to fetch and process students
        String timeEllapsed = apiController.getStudents();

        // Copy temp_tables to production_tables

        studentDao.vaciarTabla();
        studentDao.copiarTabla();

        studentTransactionsDao.vaciarTabla();
        studentTransactionsDao.copiarTabla();

        System.out.println("FINISHED SYNC WITH API :"+timeEllapsed);
    }
}
