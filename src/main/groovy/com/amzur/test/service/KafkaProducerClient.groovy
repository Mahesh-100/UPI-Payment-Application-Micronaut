package com.amzur.test.service

import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

@Client("http://localhost:8081")
interface KafkaProducerClient {

    @Post("/produce")
    void sendMessage(String topic, String message)
}

