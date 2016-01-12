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

  public class DateTimeZoneTypeConverter implements JsonSerializer<DateTimeZone>, JsonDeserializer<DateTimeZone> {
  	@Override
  	public JsonElement serialize(DateTimeZone src, Type srcType, JsonSerializationContext context) {
  		return new JsonPrimitive(src.getID());
  	}

  	@Override
  	public DateTimeZone deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
  		return DateTimeZone.forID(json.getAsString());
  	}
  }
