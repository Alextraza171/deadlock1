package com.example.deadlock;

import com.example.deadlock.entities.Item;
import com.example.deadlock.services.ItemsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TestProcessor {

    private ExecutorService executorService;
    private ItemsService itemsService;

    public TestProcessor(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(10);
    }

    public void start() {
        test1();
    }

    private void test1() {
        for (int i = 1; i < 10; i++) {
            executorService.submit(this::process);
        }
    }

    private void process() {
        List<Item> items = itemsService.sendForUpdate();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        itemsService.processResponse(items);
    }

}
