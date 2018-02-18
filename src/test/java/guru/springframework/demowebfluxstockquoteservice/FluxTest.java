package guru.springframework.demowebfluxstockquoteservice;

import guru.springframework.demowebfluxstockquoteservice.model.Quote;
import guru.springframework.demowebfluxstockquoteservice.service.QuoteGeneratorServiceImpl;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import reactor.util.function.Tuple2;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

public class FluxTest {

    // https://www.infoq.com/articles/rxjava-by-example
    @Test
    public void fluxZipTest() throws Exception{

        Duration period = Duration.ofSeconds(3);
        final Flux<String> just = Flux.just("Hello", "World");
        just.subscribe(System.out::println);

        final Flux<String> stringFlux = just.zipWith(Flux.range(1, Integer.MAX_VALUE),
                (string, count) -> String.format("%2d. %s", count, string));
        stringFlux.subscribe(System.out::println);

        System.out.println("Hello world");
    }

    @Test
    public void zipWith2() throws Exception{

        final Flux<Tuple2<String, Long>> tuple2Flux = Flux.just("Hello", "World")
                .zipWith(Flux.interval(Duration.ofSeconds(1)));

        tuple2Flux.subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(3);

    }

    @Test
    public void mergeTest() throws Exception{
        List<Quote> prices = new ArrayList<>();
        prices.add(new Quote("AAPL", 160.16));
        prices.add(new Quote("MSFT", 77.74));
        prices.add(new Quote("GOOG", 847.24));
        prices.add(new Quote("ORCL", 49.51));
        prices.add(new Quote("IBM", 159.34));
        prices.add(new Quote("INTC", 39.29));
        prices.add(new Quote("RHT", 84.29));
        prices.add(new Quote("VMW", 92.21));

        QuoteGeneratorServiceImpl quoteGeneratorService = new QuoteGeneratorServiceImpl();
        final Flux<Quote> generate = Flux.generate(() -> 0,
                (BiFunction<Integer, SynchronousSink<Quote>, Integer>) (index, sink) -> {
                    Quote updatedQuote = quoteGeneratorService.updateQuote(prices.get(index));
                    sink.next(updatedQuote);
                    return ++index % prices.size();
                });

        generate.subscribe(System.out::println);
    }

    @Test
    public void quoteTest() throws Exception{

    }
}
