package <%= appPackage %>.domain.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class <%= entityName %>Entity {

    @SerializedName("id")
    public Long id;

}
