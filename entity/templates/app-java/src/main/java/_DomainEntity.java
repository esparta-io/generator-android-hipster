package <%= appPackage %>.model;

import auto.parcel.AutoParcel;

import <%= appPackage %>.util.gson.AutoGson;

@AutoParcel
@AutoGson(autoValueClass = AutoParcel_<%= entityName %>.class)
public abstract class <%= entityName %> {

    public abstract Long id();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoParcel_<%= entityName %>.Builder();
    }

    @AutoParcel.Builder
    public abstract static class Builder {

        public abstract Builder id(Long id);

        public abstract <%= entityName %> build();
    }

}
