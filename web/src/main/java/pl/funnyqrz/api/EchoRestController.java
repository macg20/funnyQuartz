package pl.funnyqrz.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoRestController {

    @ResponseBody
    @GetMapping("/echo")
    public ResponseEntity<String> echo() {
        return ResponseEntity.ok("FunnyQrzAPI is Running");
    }
}
