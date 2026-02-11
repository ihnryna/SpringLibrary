package ihnryna.springlibrary.service.transactionExperiments;

import ihnryna.springlibrary.model.LibraryItem;
import ihnryna.springlibrary.model.ReaderAccount;
import ihnryna.springlibrary.repository.LibraryItemRepository;
import ihnryna.springlibrary.repository.ReaderAccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class LibraryTransactionalComponent {

    private final LibraryItemRepository libraryRepo;
    private final ReaderAccountRepository readerRepo;

    public LibraryTransactionalComponent(LibraryItemRepository libraryRepo, ReaderAccountRepository readerRepo) {
        this.libraryRepo = libraryRepo;
        this.readerRepo = readerRepo;
    }

    @Transactional
    public boolean borrowBook(Long bookId, Long readerId) {
        LibraryItem book = libraryRepo.findById(bookId).orElseThrow();
        ReaderAccount reader = readerRepo.findById(readerId).orElseThrow();
        book.setAvailable(false);
        reader.setBalance(reader.getBalance() - 10);
        return TransactionSynchronizationManager.isActualTransactionActive();
    }

    @Transactional
    public boolean returnBook(Long bookId) {
        LibraryItem book = libraryRepo.findById(bookId).orElseThrow();
        book.setAvailable(true);
        return TransactionSynchronizationManager.isActualTransactionActive();
    }

    @Transactional
    public boolean markBookUnavailableByTitle(String title) {
        libraryRepo.markUnavailableByTitle(title);
        return TransactionSynchronizationManager.isActualTransactionActive();
    }
}
