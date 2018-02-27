package pl.funnyqrz.mapper;

public interface AbstractMapper<T,S> {

    T toDto(S entity);

    S toEntity(T dto);
}
