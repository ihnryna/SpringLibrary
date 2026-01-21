package ihnryna.springlibrary.controller;

import ihnryna.springlibrary.dto.LibraryItemDto;
import ihnryna.springlibrary.service.LibraryItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library-items")
public class LibraryItemController {

    private final LibraryItemService service;

    public LibraryItemController(LibraryItemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<LibraryItemDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryItemDto> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody LibraryItemDto dto) {
        service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody LibraryItemDto dto
    ) {
        dto.setId(id);
        service.save(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> countAll() {
        return ResponseEntity.ok(service.countAllItems());
    }

    @GetMapping("/published-after/{year}")
    public ResponseEntity<List<LibraryItemDto>> findPublishedAfter(
            @PathVariable int year
    ) {
        return ResponseEntity.ok(service.findItemsPublishedAfter(year));
    }

    @GetMapping("/book-authors")
    public ResponseEntity<List<String>> findAllBookAuthors() {
        return ResponseEntity.ok(service.findAllBookAuthors());
    }
}
