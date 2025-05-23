package aca;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import aca.fulton.spring.Student;
import aca.fulton.spring.StudentTemp;
import aca.fulton.spring.StudentTransactions;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    @Value("${api.key}")
    private String apiKey;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // This method fetches students and their transactions from the updated API
    public List<StudentTemp> fetchDataFromApi(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", apiKey);  // Use the correct API key

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<StudentTemp>> response = restTemplate.exchange(
                endpoint,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<StudentTemp>>() {}
        );

        // Return the list of students, which includes their transactions
        return response.getBody();
    }
}

