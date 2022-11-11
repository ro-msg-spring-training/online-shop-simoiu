package ro.msg.learning.shop.helper;

import lombok.experimental.UtilityClass;
import ro.msg.learning.shop.model.entities.BaseEntity;

@UtilityClass
public class MapperHelper {
    public static Integer getIdFromEntity(BaseEntity entity) {
        return entity != null ? entity.getId() : null;
    }
}
