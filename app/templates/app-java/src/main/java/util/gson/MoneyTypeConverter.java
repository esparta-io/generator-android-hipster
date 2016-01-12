package <%= appPackage %>.util.gson;

import java.lang.reflect.Type;

import org.joda.money.Money;
import org.joda.money.format.MoneyAmountStyle;
import org.joda.money.format.MoneyFormatter;
import org.joda.money.format.MoneyFormatterBuilder;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class MoneyTypeConverter implements JsonSerializer<Money>, JsonDeserializer<Money> {
	@Override
	public JsonElement serialize(Money src, Type srcType, JsonSerializationContext context) {
		return new JsonPrimitive(serialize(src));
	}

	@Override
	public Money deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		String jsonString = json.getAsString();
		return deserialize(jsonString);
	}

	Money deserialize(String jsonString) {
		return Money.parse(jsonString);
	}

	String serialize(Money src) {
		return getMoneyFormatter().print(src);
	}

	private static MoneyFormatter getMoneyFormatter() {
		return new MoneyFormatterBuilder().appendCurrencyCode().appendLiteral(" ").appendAmount(MoneyAmountStyle.ASCII_DECIMAL_POINT_NO_GROUPING).toFormatter();
	}
}
