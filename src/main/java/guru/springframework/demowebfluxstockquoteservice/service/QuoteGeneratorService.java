package guru.springframework.demowebfluxstockquoteservice.service;

import guru.springframework.demowebfluxstockquoteservice.model.Quote;
import reactor.core.publisher.Flux;

import java.time.Duration;

public interface QuoteGeneratorService {

    Flux<Quote> fetchQuoteStream(Duration period);
}
