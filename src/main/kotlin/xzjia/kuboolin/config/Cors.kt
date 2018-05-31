package xzjia.kuboolin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
class Cors{

    @Bean
    fun corsFilter(): CorsWebFilter {

        val config = CorsConfiguration()

        config.applyPermitDefaultValues()
        config.addAllowedMethod("PUT")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)

        return CorsWebFilter(source)
    }

}