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

        // Modul pro Hibernate lazy loading kompatibilní s Jakarta EE
        Hibernate5JakartaModule hibernateModule = new Hibernate5JakartaModule();
        hibernateModule.configure(Hibernate5JakartaModule.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
        mapper.registerModule(hibernateModule);

        // Modul pro Java 8 date/time (LocalDateTime, LocalDate, atd.)
        mapper.registerModule(new JavaTimeModule());

        // Vypnutí serializace datumů jako timestampů, chceme ISO string
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Zabránění chybě při serializaci prázdných beanů
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        return mapper;
    }
}