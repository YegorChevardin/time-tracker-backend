package com.reactdevops.timetracker.backend.service.convertors;

import java.util.List;

/**
 * Class for converting Entity to dto objects and vise-versa
 *
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface DtoEntityObjectConvertor<E, D> {
  E dtoToEntity(D object);

  D entityToDto(E object);

  default List<E> listDtoListEntity(List<D> listOfDto) {
    return listOfDto.stream().map(this::dtoToEntity).toList();
  }

  default List<D> listEntityToListDto(List<E> listOfEntity) {
    return listOfEntity.stream().map(this::entityToDto).toList();
  }
}
