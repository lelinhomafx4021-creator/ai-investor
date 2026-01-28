package com.investor.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public final static  String EXCHANGE_NAME="trade.exchange";
    public final static String QUEUE_NAME="trade.notify.queue";
    public final static String ROUTING_KEY="notify";

    @Bean
    public DirectExchange tradeExchange(){
        return new DirectExchange(EXCHANGE_NAME,true,false);
    }

    @Bean
    public Queue notifyQueue(){
        return new Queue(QUEUE_NAME,true);
    }

    @Bean
    public Binding binding(Queue notifyQueue,DirectExchange tradeExchange){
        return BindingBuilder.bind(notifyQueue).to(tradeExchange).with(ROUTING_KEY);
    }


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
