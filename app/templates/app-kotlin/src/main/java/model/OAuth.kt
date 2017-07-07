package <%= appPackage %>.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
<% if (jodatime == true) { %>import org.joda.time.DateTime <% } %>
<% if(paperparcel == true) { %>
  import paperparcel.PaperParcel
  import paperparcel.PaperParcelable
<% } %>
import java.util.*

<% if(paperparcel == true) { %>@PaperParcel<% } %>
data class OAuth(
        @SerializedName("access_token") val accessToken: String,
        @SerializedName("token_type") val tokenType: String,
        @SerializedName("expires_in") val expiresIn: Int,
        @SerializedName("refresh_token") val refreshToken: String,
        @SerializedName("scope") val scope: String,
        @SerializedName("personId") val personId: Int?,
        @SerializedName("userId") val userId: Int?,
        @SerializedName("login") val login: String,
        <% if (jodatime == true) { %>
        @Expose(serialize = false, deserialize = false) var date: DateTime = DateTime.now()
        <% } else { %>
        @Expose(serialize = false, deserialize = false) var date: Calendar = Calendar.getInstance()
        <% } %>
)<% if(paperparcel == true) { %> : PaperParcelable {

    companion object {
        @JvmField val CREATOR = PaperParcelOAuth.CREATOR
        val CLASS_NAME = "OAuth"
        val USER_NAME = "USER_NAME"
    }
}
<% } %>
