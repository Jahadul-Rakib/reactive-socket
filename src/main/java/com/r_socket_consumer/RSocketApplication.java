package com.r_socket_consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Controller
@SpringBootApplication
@PropertySource("classpath:application_consumer.properties")
public class RSocketApplication {


    public static void main(String[] args) {
        SpringApplication.run(RSocketApplication.class, args);
    }

    @MessageMapping("request-response")
    public Mono<Message> requestResponse(Message message) {
        log.info(message.getMessage());
        return Mono.just(new Message(message.getMessage()));
    }

    @MessageMapping("fire-and-forget")
    public Mono<Void> fireAndForget(Message message) {
        log.info(message.getMessage());
        return Mono.empty();
    }

    @MessageMapping("request-stream")
    public Flux<Message> requestStream(Message message) {
        log.info(message.getMessage());
        return Flux
                .interval(Duration.ofSeconds(2))
                .map(data -> new Message(message.getMessage() + " .Response: " + data))
                .log();
    }

    @MessageMapping("stream-stream")
    public Flux<Message> streamStream(Flux<Integer> settings) {
        log.info("Reactive Stream-Stream Responding.........");
        return settings
                .doOnNext(setting -> log.info("Do On Next: " + setting))
                .doOnCancel(() -> log.warn("Do On Cancel State."))
                .switchMap(integer -> Flux.interval(Duration.ofSeconds(integer)))
                .map(aLong -> new Message("Stream response: " + aLong))
                .log();
    }
}
