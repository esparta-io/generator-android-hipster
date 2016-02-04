package com.altart.obc.util.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * @author deividi
 * @since 22/01/16.
 */
public class AutoValueTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();

        AutoGson annotation = rawType.getAnnotation(AutoGson.class);
        if (annotation == null) {
            return null;
        }

        return (TypeAdapter<T>) gson.getAdapter(annotation.autoValueClass());
    }
}
