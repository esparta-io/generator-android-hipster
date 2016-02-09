package <%= appPackage %>.util.gson

import java.lang.reflect.Type

import org.joda.money.CurrencyUnit

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer

class CurrencyUnitTypeConverter : JsonSerializer<CurrencyUnit>, JsonDeserializer<CurrencyUnit> {

    override fun serialize(src: CurrencyUnit, srcType: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(serialize(src))
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): CurrencyUnit {
        return deserialize(json.asString)
    }

    internal fun deserialize(jsonString: String): CurrencyUnit {
        return CurrencyUnit.of(jsonString)
    }

    internal fun serialize(src: CurrencyUnit): String {
        return src.currencyCode
    }
}
