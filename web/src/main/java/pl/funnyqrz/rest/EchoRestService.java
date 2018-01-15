package pl.funnyqrz.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoRestService {

    @ResponseBody
    @GetMapping("/echo")
    public ResponseEntity<String> echo() {
        return ResponseEntity.ok("Up");
    }
}
