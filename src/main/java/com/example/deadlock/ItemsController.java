package com.example.deadlock;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemsController {

    private TestProcessor testProcessor;

    public ItemsController(TestProcessor testProcessor) {
        this.testProcessor = testProcessor;
    }

    @PostMapping("/test")
    public void performTest() {
        new Thread(() -> {
            testProcessor.start();
        }).start();
    }

}
