package xzjia.kuboolin.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router


@Configuration
class Routes(private val counterHandler: CounterHandler, private val greetingHandler: GreetingHandler) {

    @Bean
    fun counterRouter() = router {
        accept(MediaType.TEXT_HTML).nest {
            GET("/hello", greetingHandler::greeting)
        }
        "/api/counter".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/", counterHandler::get)
                PUT("/up", counterHandler::up)
                PUT("/down", counterHandler::down)
            }
            accept(MediaType.TEXT_EVENT_STREAM).nest {
                GET("/", counterHandler::stream)
            }
        }
    }
}