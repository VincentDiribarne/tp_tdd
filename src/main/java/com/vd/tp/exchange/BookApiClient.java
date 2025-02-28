package com.vd.tp.exchange;

import com.vd.tp.model.ApiResponse;
import com.vd.tp.model.Book;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BookApiClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public ApiResponse callAPI(Book book) {
        System.out.println("Appel réel à l'API !");
        return restTemplate.getForObject("https://book.isbn/" + book.getIsbn(), ApiResponse.class);
    }
}