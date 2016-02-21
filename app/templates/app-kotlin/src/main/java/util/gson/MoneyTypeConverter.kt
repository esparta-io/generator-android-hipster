package <%= appPackage %>.util.gson

import java.lang.reflect.Type

import org.joda.money.Money
import org.joda.money.format.MoneyAmountStyle
import org.joda.money.format.MoneyFormatter
import org.joda.money.format.MoneyFormatterBuilder

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer

/**
 * Created by deividi on 08/01/16.
 */

class MoneyTypeConverter : JsonSerializer<Money>, JsonDeserializer<Money> {

    override fun serialize(src: Money, srcType: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(serialize(src))
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): Money {
        val jsonString = json.asString
        return deserialize(jsonString)
    }

    internal fun deserialize(jsonString: String): Money {
        return Money.parse(jsonString)
    }

    internal fun serialize(src: Money): String {
        return moneyFormatter.print(src)
    }

    private val moneyFormatter: MoneyFormatter
        get() = MoneyFormatterBuilder().appendCurrencyCode().appendLiteral(" ").appendAmount(MoneyAmountStyle.ASCII_DECIMAL_POINT_NO_GROUPING).toFormatter()
}
