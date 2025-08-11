package cz.smartexpensetracker.smart_expense_tracker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Module for Hibernate lazy loading support compatible with Jakarta EE
        Hibernate5JakartaModule hibernateModule = new Hibernate5JakartaModule();
        hibernateModule.configure(Hibernate5JakartaModule.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
        mapper.registerModule(hibernateModule);

        // Module for Java 8 date/time types (LocalDateTime, LocalDate, etc.)
        mapper.registerModule(new JavaTimeModule());

        // Disable serialization of dates as timestamps; use ISO-8601 string format instead
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Prevent failure on serializing empty beans
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        return mapper;
    }
}