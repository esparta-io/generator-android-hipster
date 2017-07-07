package <%= appPackage %>.util

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText
<% if (butterknife == true) { %>import org.joda.time.DateTime <% } %>
<% if (butterknife == true) { %>import org.joda.time.format.DateTimeFormat <% } %>
import java.text.NumberFormat
import java.util.*
import android.text.Html


fun isEmailField(email: String?): Boolean {
    if (email == null) {
        return false
    }

    if (email.isEmpty() ||
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return false
    }

    return true
}

fun unmaskNumbers(s: String?): String {
    return s?.replace("[^0-9]*".toRegex(), "") ?: ""
}

private val moneyFormatter by lazy { NumberFormat.getCurrencyInstance(Locale("pt", "BR")) }

fun formatCurrency(value: Double?): String {
    value?.let {
        return moneyFormatter.format(it)
    }
    return ""
}

fun formatTwoDigitsFilter(): InputFilter {
    return InputFilter { source, start, end, dest, dstart, dend ->
        val builder = StringBuilder(dest)
        builder.replace(dstart, dend, source.subSequence(start, end).toString())
        if (!builder.toString().matches(Regex("(([1-9]{1})([0-9]{0,4})?(\\.)?)?([0-9]{0,2})?"))) {
            if (source.isEmpty()) {
                return@InputFilter dest.subSequence(dstart, dend)
            }
            return@InputFilter ""
        }
        return@InputFilter null
    }
}

<% if (butterknife == true) { %>
  /**
   * @param pattern dd.MM.yyyy, dd/MM/yyyy, etc
   */
  fun formatDateShortDate(date: DateTime?, pattern: String): String {
      date?.let {
          val dtfOut = DateTimeFormat.forPattern(pattern)
          return dtfOut.print(date)
      }
      return ""
  }
<% } %>

fun stripHtml(html: String): String {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        return Html.fromHtml(html).toString()
    }
}

fun splitNameLastName(nameToSplit: String): Pair<String, String> {
    val tmpName = nameToSplit.split(" ")[0]
    val tmpLastName = nameToSplit.replace(tmpName, "").trim()
    return Pair(tmpName, tmpLastName)
}
