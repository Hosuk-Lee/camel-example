package hs.example.camel.springboot;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final BookRepository books;

    private final Random amount = new Random();

    public OrderService(BookRepository books) {
        this.books = books;
    }

    public Order generateOrder() {
        Order order = new Order();
        order.setAmount(amount.nextInt(10) + 1);
        order.setBook(books.findById(amount.nextInt(2) + 1).get());
        return order;
    }
}