package ihnryna.springlibrary.repository;

import ihnryna.springlibrary.model.Book;
import ihnryna.springlibrary.model.LibraryItem;
import ihnryna.springlibrary.model.Magazine;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface LibraryItemDAO {

    void createLibraryItem(LibraryItem item) throws DataAccessException;

    LibraryItem getLibraryItemById(Long id) throws DataAccessException;

    List<LibraryItem> listLibraryItems() throws DataAccessException;

    void updateLibraryItem(LibraryItem item) throws DataAccessException;

    void deleteLibraryItem(Long id) throws DataAccessException;

    List<Book> listBooks() throws DataAccessException;

    List<Magazine> listMagazines() throws DataAccessException;
}
