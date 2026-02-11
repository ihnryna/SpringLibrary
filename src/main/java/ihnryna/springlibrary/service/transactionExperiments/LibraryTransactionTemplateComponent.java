package ihnryna.springlibrary.service.transactionExperiments;

import ihnryna.springlibrary.model.LibraryItem;
import ihnryna.springlibrary.model.ReaderAccount;
import ihnryna.springlibrary.repository.LibraryItemRepository;
import ihnryna.springlibrary.repository.ReaderAccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class LibraryTransactionTemplateComponent {

    private final TransactionTemplate template;
    private final LibraryItemRepository libraryRepo;
    private final ReaderAccountRepository readerRepo;


    public LibraryTransactionTemplateComponent(TransactionTemplate template, LibraryItemRepository libraryRepo, ReaderAccountRepository readerRepo) {
        this.template = template;
        this.libraryRepo = libraryRepo;
        this.readerRepo = readerRepo;
    }


    public boolean borrowBook(Long bookId, Long readerId) {
        return Boolean.TRUE.equals(template.execute(status -> {
            LibraryItem book = libraryRepo.findById(bookId).orElseThrow();
            book.setAvailable(false);
            ReaderAccount reader = readerRepo.findById(readerId).orElseThrow();
            reader.setBalance(reader.getBalance() - 10);
            return TransactionSynchronizationManager.isActualTransactionActive();
        }));
    }

    public boolean returnBook(Long bookId) {
        return Boolean.TRUE.equals(template.execute(status -> {
            LibraryItem book = libraryRepo.findById(bookId).orElseThrow();
            book.setAvailable(true);
            return TransactionSynchronizationManager.isActualTransactionActive();
        }));
    }

    public boolean markBookUnavailableByTitle(String title) {
        return Boolean.TRUE.equals(template.execute(status -> {
            libraryRepo.markUnavailableByTitle(title);
            return TransactionSynchronizationManager.isActualTransactionActive();
        }));
    }
}
