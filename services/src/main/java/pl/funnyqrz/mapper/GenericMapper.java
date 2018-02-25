package pl.funnyqrz.mapper;

public interface GenericMapper<T,S> {

    T mapToDTO(S entity);

    S mapToEntity(T dto);
}
