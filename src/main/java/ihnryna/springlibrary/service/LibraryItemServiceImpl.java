package ihnryna.springlibrary.service;

import ihnryna.springlibrary.dto.LibraryItemDto;
import ihnryna.springlibrary.model.LibraryItem;
import ihnryna.springlibrary.repository.LibraryItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryItemServiceImpl implements LibraryItemService {

    private final LibraryItemRepository repository;

    public LibraryItemServiceImpl(LibraryItemRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LibraryItemDto> findAll() {
        return repository.findAll()
                .stream()
                .map(LibraryItemDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<LibraryItemDto> findById(Long id) {
        return repository.findById(id)
                .map(LibraryItemDto::fromEntity);
    }

    @Transactional()
    @Override
    public void save(LibraryItemDto dto) {
        LibraryItem entity = dto.toEntity();
        repository.save(entity);
    }

    @Transactional()
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional()
    @Override
    public void updateByTitle(LibraryItemDto dto) {
        LibraryItem entity = repository.findAll()
                .stream()
                .filter(i -> i.getTitle().equals(dto.getTitle()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Item with title not found: " + dto.getTitle())
                );

        entity.setPublishedYear(dto.getPublishedYear());
        entity.setAvailable(dto.getAvailable());

        repository.save(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public int countAllItems() {
        return repository.countAllItems();
    }

    @Transactional(readOnly = true)
    @Override
    public List<LibraryItemDto> findItemsPublishedAfter(int year) {
        return repository.findItemsPublishedAfter(year)
                .stream()
                .map(LibraryItemDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> findAllBookAuthors() {
        return repository.findAllBookAuthors();
    }
}
