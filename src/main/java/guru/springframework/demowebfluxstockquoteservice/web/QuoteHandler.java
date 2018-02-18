package guru.springframework.demowebfluxstockquoteservice.web;

import guru.springframework.demowebfluxstockquoteservice.model.Quote;
import guru.springframework.demowebfluxstockquoteservice.service.QuoteGeneratorService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class QuoteHandler {
    private final QuoteGeneratorService generatorService;

    public QuoteHandler(QuoteGeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    public Mono<ServerResponse> fetchQuotes(ServerRequest request){
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.generatorService.fetchQuoteStream(Duration.ofMillis(100)).take(size), Quote.class);
    }

    public Mono<ServerResponse> streamQuotes(ServerRequest request){
        return ok().contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(this.generatorService.fetchQuoteStream(Duration.ofMillis(200)), Quote.class);
    }


}
