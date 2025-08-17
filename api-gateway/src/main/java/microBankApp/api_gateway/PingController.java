package microBankApp.api_gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @GetMapping("/health") public String health() { return "OK"; }
    @GetMapping("/hello")  public String hello()  { return "gateway says hello"; }
}
