package <%=appPackage%>.util.gson;

import java.lang.reflect.Type;

import org.joda.time.DateTimeZone;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

class DateTimeZoneTypeConverter : JsonSerializer<DateTimeZone>, JsonDeserializer<DateTimeZone> {

    override fun serialize(src: DateTimeZone, srcType: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.id)
    }


    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): DateTimeZone {
        return DateTimeZone.forID(json.asString)
    }
}
