package <%= appPackage %>.model;

import com.google.auto.value.AutoValue;

import <%= appPackage %>.util.gson.AutoGson;

@AutoValue
@AutoGson(autoValueClass = AutoValue_<%= entityName %>.class)
public abstract class <%= entityName %> {

    public abstract Long id();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_<%= entityName %>.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder id(Long id);

        public abstract <%= entityName %> build();
    }

}
