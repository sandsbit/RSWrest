package me.nikitaserba.rsw.rest.configs;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.nikitaserba.rsw.parser.ParserSettings;
import me.nikitaserba.rsw.utils.json.UnknownFieldExceptionDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Gson.class)
public class GsonConfig {
    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(ParserSettings.class, new UnknownFieldExceptionDeserializer<ParserSettings>())
                .setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .serializeNulls()
                .create();
    }
}