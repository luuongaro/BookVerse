package com.grupo3.BookVerse.common.model;

public interface IMapper<E, D> {
    D toDTO(E entity);
    E toEntity(D dto);
}

