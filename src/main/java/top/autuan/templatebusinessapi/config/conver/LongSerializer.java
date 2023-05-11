package top.autuan.templatebusinessapi.config.conver;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import java.io.IOException;

@JacksonStdImpl
@SuppressWarnings("serial")
public class LongSerializer extends JsonSerializer<Long> {
    public final static LongSerializer INSTANCE = new LongSerializer();

    public LongSerializer() {
        super();
    }


    @Override
    public void serialize(Long aLong, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(aLong.toString());
    }

}
