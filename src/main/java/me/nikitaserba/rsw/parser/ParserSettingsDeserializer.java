package me.nikitaserba.rsw.parser;

import com.google.gson.*;
import me.nikitaserba.rsw.parser.exceptions.UnknownJsonFieldException;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

public final class ParserSettingsDeserializer implements JsonDeserializer<ParserSettings> {


    @Override
    public ParserSettings deserialize(JsonElement jsonElement, Type type,
                                      JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        ParserSettings settings = new ParserSettings();
        Field[] fields = settings.getClass().getDeclaredFields();

        recordLoop:
        for (Map.Entry<String, JsonElement> jsonRecord : jsonObject.entrySet()) {
            for (Field field : fields) {
                // TODO: get real naming policy
                String fieldName = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(field);
                if (fieldName.equals(jsonRecord.getKey())) {
                    try {
                        field.setAccessible(true);
                        field.set(settings, jsonDeserializationContext.deserialize(jsonRecord.getValue(), field.getType()));
                        continue recordLoop;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            throw new UnknownJsonFieldException(jsonRecord.getKey());
        }

            return settings;
    }
}
