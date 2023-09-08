package com.github.iamhi.hizone.demoground.input.hello;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@RequiredArgsConstructor
public class HelloController {

    @GetMapping
    public ResponseEntity<String> hello() {
       return ResponseEntity.ok("Hello");
    }

    @PostMapping
    public ResponseEntity<GreetResponse> greet(@RequestBody GreetRequest greetRequest) {
        GreetResponse greetResponse = new GreetResponse();

        greetResponse.setMessage("Hello " + greetRequest.getName());

        return ResponseEntity.ok(greetResponse);
    }

    @GetMapping(path = "/secret")
    public ResponseEntity<String> secretHandshake() {
        return ResponseEntity.ok("Secret hello?");
    }
}
