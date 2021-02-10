package com.r_socket_producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String message;
    private String sender;
    private String receiver;

    public Message(String message) {
        this.message = message;
    }
}
