package ihnryna.springlibrary.service;

import ihnryna.springlibrary.dto.LibraryItemDto;
import ihnryna.springlibrary.model.LibraryItem;

import java.util.List;
import java.util.Optional;

public interface LibraryItemService {
    List<LibraryItemDto> findAll();
    Optional<LibraryItemDto> findById(Long id);
    void save(LibraryItemDto item);
    void deleteById(Long id);
    void updateByTitle(LibraryItemDto item);
    int countAllItems();
    List<LibraryItemDto> findItemsPublishedAfter(int year);
    List<String> findAllBookAuthors();
}
