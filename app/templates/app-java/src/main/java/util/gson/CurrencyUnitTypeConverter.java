package <%= appPackage %>.util.gson;

import java.lang.reflect.Type;

import org.joda.money.CurrencyUnit;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CurrencyUnitTypeConverter implements JsonSerializer<CurrencyUnit>, JsonDeserializer<CurrencyUnit> {
	@Override
	public JsonElement serialize(CurrencyUnit src, Type srcType, JsonSerializationContext context) {
		return new JsonPrimitive(serialize(src));
	}

	@Override
	public CurrencyUnit deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		return deserialize(json.getAsString());
	}

	CurrencyUnit deserialize(String jsonString) {
		return CurrencyUnit.of(jsonString);
	}

	String serialize(CurrencyUnit src) {
		return src.getCurrencyCode();
	}
}
