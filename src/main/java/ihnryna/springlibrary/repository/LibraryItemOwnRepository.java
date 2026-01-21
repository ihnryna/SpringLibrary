package ihnryna.springlibrary.repository;

import ihnryna.springlibrary.model.LibraryItem;

import java.util.List;

public interface LibraryItemOwnRepository {
    int countAllItems();
    List<LibraryItem> findItemsPublishedAfter(int year);
    List<String> findAllBookAuthors();
}