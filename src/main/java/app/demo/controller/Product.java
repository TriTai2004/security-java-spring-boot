package app.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Product {
    
    @GetMapping("/info")
    public String getProductInfo() {
        return "This is a demo product.";
    }
}
