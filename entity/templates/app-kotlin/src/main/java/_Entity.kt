package <%= appPackage %>.domain.entity;

import com.google.gson.annotations.SerializedName;

class <%= entityName %>Entity {

    @SerializedName("id")
    var id: Long? = null

}
