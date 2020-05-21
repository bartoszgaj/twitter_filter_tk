package com.example.springboot;

import com.example.springboot.data.TweetRepository;
import com.example.springboot.data.model.Tweet;
import org.json.JSONObject;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebApplication {
  static final String directExchangeName = "twitter_exchange";

  static final String queueName = "queue_one";

    @Autowired
    private TweetRepository repository;

    public TweetRepository getRepository() {
        return repository;
    }

    public void setRepository(TweetRepository repository) {
        this.repository = repository;
    }

    @Bean
    public Queue myQueue() {
        return new Queue(queueName, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(directExchangeName, false, false);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("tweet");
    }

    @RabbitListener(queues = queueName)
    public void listen(Message in) {
        JSONObject obj = new JSONObject(new String(in.getBody()));
        this.repository.save(new Tweet(obj.getJSONObject("user").getString("name"), obj.getString("text")));
        System.out.println("Message read from myQueue : " + obj.toString());
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebApplication.class, args);
    }
}
