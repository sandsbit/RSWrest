package me.nikitaserba.rsw.utils.json;

import com.google.gson.*;
import me.nikitaserba.rsw.parser.exceptions.UnknownJsonFieldException;
import me.nikitaserba.rsw.rest.exceptions.IntervalServerErrorException;

import java.lang.reflect.*;
import java.util.Map;

public final class UnknownFieldExceptionDeserializer<T> implements JsonDeserializer<T> {

    private final Class<T> genericType
            = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Override
    public T deserialize(JsonElement jsonElement, Type type,
                                      JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            T parsed;

            Constructor<T> genericTypeDeclaredConstructor = genericType.getDeclaredConstructor();
            parsed = genericTypeDeclaredConstructor.newInstance();
            Field[] fields = genericType.getDeclaredFields();

            recordLoop:
            for (Map.Entry<String, JsonElement> jsonRecord : jsonObject.entrySet()) {
                for (Field field : fields) {
                    // TODO: get real naming policy
                    String fieldName = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(field);
                    if (fieldName.equals(jsonRecord.getKey())) {
                        field.setAccessible(true);
                        field.set(parsed, jsonDeserializationContext.deserialize(jsonRecord.getValue(), field.getType()));
                        continue recordLoop;
                    }
                }
                throw new UnknownJsonFieldException(jsonRecord.getKey());
            }

            return parsed;
        } catch (NoSuchMethodException | IllegalArgumentException e) {
            throw new InvalidClassException(
                    "Class parsed by UnknownFieldExceptionDeserializer<T> must have zero-argument constructor",
                    e);
        } catch (InstantiationException e) {
            throw new InvalidClassException(
                    "Class parsed by UnknownFieldExceptionDeserializer<T> cannot be abstract",
                    e);

        } catch (IllegalAccessException e) {
            throw new InvalidClassException(
                    "Cannot access zero-argument constructor of class parsed by UnknownFieldExceptionDeserializer<T>",
                    e);
        } catch (InvocationTargetException e) {
            throw new IntervalServerErrorException("EXCEPTION_IN_DATA_CLASS_CONSTRUCTOR", e.getTargetException().getMessage());
        }
    }
}
