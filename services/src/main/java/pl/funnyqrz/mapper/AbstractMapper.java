package pl.funnyqrz.mapper;

public interface AbstractMapper<T,S> {

    T mapToDTO(S entity);

    S mapToEntity(T dto);
}
