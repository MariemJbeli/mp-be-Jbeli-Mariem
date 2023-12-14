package soa;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:4200") // Remplacez par l'URL de votre application Angular
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept")
            .allowCredentials(true)
            .maxAge(3600);
    }
}