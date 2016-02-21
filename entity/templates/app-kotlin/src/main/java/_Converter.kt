package <%= appPackage %>.domain.entity.converter;

import <%= appPackage %>.domain.entity.<%= entityName %>Entity;
import <%= appPackage %>.model.<%= entityName %>;

object <%= entityName %>Converter {

    fun wrap(entity: <%= entityName %>Entity?): <%= entityName %>? {
        if (entity == null) return null
            return <%= entityName %>(entity.id?: 0L)
        }

    fun unwrap(model: <%= entityName %>): <%= entityName %>Entity {
        val entity = <%= entityName %>Entity()
        entity.id = model.id
        return entity
    }

}