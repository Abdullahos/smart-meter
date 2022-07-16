package com.root.meter.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class MLAPI {
    //http://192.168.1.111:8080/
    private String pi_ip = "192.168.1.111";
    @GetMapping("ml/get/appliance")
    public Mono<ResponseEntity<String>> get(){
        WebClient webClient = WebClient.create("http://"+pi_ip+":8181");
        return webClient.get().retrieve().toEntity(String.class);
    }
}
