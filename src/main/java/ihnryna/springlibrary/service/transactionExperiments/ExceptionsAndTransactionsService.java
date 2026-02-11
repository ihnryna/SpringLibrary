package ihnryna.springlibrary.service.transactionExperiments;

import ihnryna.springlibrary.model.Book;
import ihnryna.springlibrary.model.ReaderAccount;
import ihnryna.springlibrary.repository.LibraryItemRepository;
import ihnryna.springlibrary.repository.ReaderAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class ExceptionsAndTransactionsService {

    private final LibraryItemRepository libraryItemRepository;

    private final ReaderAccountRepository readerAccountRepository;

    private final OuterService outerService;

    public ExceptionsAndTransactionsService(LibraryItemRepository libraryItemRepository, OuterService outerService, ReaderAccountRepository readerAccountRepository) {
        this.libraryItemRepository = libraryItemRepository;
        this.outerService = outerService;
        this.readerAccountRepository = readerAccountRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateAuthorAndFailChecked(String title, String author) throws Exception {

        Book book = (Book) libraryItemRepository.findLibraryItemByTitle(title).orElseThrow();
        book.setAuthor(author);
        libraryItemRepository.save(book);

        throw new Exception("Checked exception");
    }

    @Transactional(noRollbackFor = {RuntimeException.class})
    public void updateAuthorAndFailRuntime(String title, String author) {

        Book book = (Book) libraryItemRepository.findLibraryItemByTitle(title).orElseThrow();
        book.setAuthor(author);
        libraryItemRepository.save(book);

        throw new RuntimeException("Checked exception");
    }

    public boolean first() {
        return second();
    }

    @Transactional
    public boolean second() {
        return TransactionSynchronizationManager.isActualTransactionActive();
    }

    public boolean firstWithOuter() {
        return outerService.outerSecond();
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void withdraw(String surname, Integer amount) {
        ReaderAccount reader = (ReaderAccount) readerAccountRepository.findBySurname(surname).orElseThrow();
        Integer balance = reader.getBalance();

        try { Thread.sleep(300); } catch (InterruptedException ignored) {}

        if (balance < amount) {
            throw new IllegalStateException("Not enough money");
        }

        reader.setBalance(balance - amount);
    }

}
