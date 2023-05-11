package top.autuan.templatebusinessapi.config.other;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.autuan.templatebusinessapi.config.conver.LongSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OtherWebMvcConfigurer implements WebMvcConfigurer {

    @Bean("jackson2ObjectMapperBuilderCustomizer")
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        Map<Class<?>, JsonSerializer<?>> map = new HashMap<>(2);
        map.put(Long.class, LongSerializer.INSTANCE);
        map.put(Long.TYPE, LongSerializer.INSTANCE);
        return builder -> builder.serializersByType(map);
    }
}
