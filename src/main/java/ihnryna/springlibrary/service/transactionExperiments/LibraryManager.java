package ihnryna.springlibrary.service.transactionExperiments;

import ihnryna.springlibrary.model.LibraryItem;
import ihnryna.springlibrary.repository.LibraryItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibraryManager {

    private final LibraryItemRepository libraryRepo;

    private final DifferentPropagationsReaderAccountService readerAccountService;

    public LibraryManager(DifferentPropagationsReaderAccountService readerAccountService, LibraryItemRepository libraryRepo) {
        this.readerAccountService = readerAccountService;
        this.libraryRepo = libraryRepo;
    }

    @Transactional
    public void scenarioRequired(Long bookId, Long readerId) {
        LibraryItem book = libraryRepo.findById(bookId).orElseThrow();
        book.setAvailable(false);
        readerAccountService.borrowBookRequired(readerId);
        throw new RuntimeException();
    }

    @Transactional
    public void scenarioRequiresNew(Long bookId, Long readerId) {
        LibraryItem book = libraryRepo.findById(bookId).orElseThrow();
        book.setAvailable(false);
        readerAccountService.borrowBookRequiresNew(readerId);
        throw new RuntimeException();
    }

    @Transactional
    public void scenarioNested(Long bookId, Long readerId) {
        LibraryItem book = libraryRepo.findById(bookId).orElseThrow();
        book.setAvailable(false);

        try {
            readerAccountService.borrowBookNested(readerId);
        } catch (RuntimeException ignored) {
        }
    }

    @Transactional
    public void scenarioNestedNotCatches(Long bookId, Long readerId) {
        LibraryItem book = libraryRepo.findById(bookId).orElseThrow();
        book.setAvailable(false);
        readerAccountService.borrowBookNested(readerId);
    }
}
