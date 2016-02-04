package <%= appPackage %>.domain.entity.converter;

import <%= appPackage %>.domain.entity.<%= entityName %>;
import <%= appPackage %>.model.<%= entityName %>;

public class <%= entityName %>Converter {

    public static <%= entityName %> wrap(<%= entityName %>Entity entity) {
        if (entity == null) return null;
        return <%= entityName %>.builder()
                .id(entity.id)
                .build();
    }

    public static <%= entityName %>Entity unwrap(<%= entityName %> model) {
        <%= entityName %>Entity entity = new <%= entityName %>Entity();
        entity.id = model.id();
        return entity;
    }

}
