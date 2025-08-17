package microBankApp.account_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/health")
    public String health() { return "OK"; }

    @GetMapping("/hello")
    public String hello() { return "account-service says hello"; }
}
