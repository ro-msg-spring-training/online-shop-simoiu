package ro.msg.learning.shop.mapper;

import java.util.List;

public interface DtoMapper<S, D> {
    D mapToDto(S entity);

    default List<D> mapAllToDto(List<S> entities) {
        return entities.stream().map(this::mapToDto).toList();
    }

    S mapToEntity(D dto);

    default List<S> mapAllToEntities(List<D> dtoList) {
        return dtoList.stream().map(this::mapToEntity).toList();
    }
}
