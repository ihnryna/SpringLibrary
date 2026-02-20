package ihnryna.springlibrary.service.transactionExperiments;

import ihnryna.springlibrary.model.LibraryItem;
import ihnryna.springlibrary.model.ReaderAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.stereotype.Component;

@Component
public class LibraryEntityManagerTxComponent {

    private final EntityManagerFactory emf;

    public LibraryEntityManagerTxComponent(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public boolean borrowBook(Long bookId, Long readerId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean active = false;

        try {
            tx.begin();
            active = tx.isActive();

            LibraryItem book = em.find(LibraryItem.class, bookId);
            ReaderAccount reader = em.find(ReaderAccount.class, readerId);

            book.setAvailable(false);
            reader.setBalance(reader.getBalance() - 10);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
        } finally {
            em.close();
        }

        return active;
    }

    public boolean returnBook(Long bookId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean active = false;

        try {
            tx.begin();
            active = tx.isActive();

            LibraryItem book = em.find(LibraryItem.class, bookId);
            book.setAvailable(true);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
        } finally {
            em.close();
        }

        return active;
    }

    public boolean markBookUnavailableByTitle(String title) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean active = false;

        try {
            tx.begin();
            active = tx.isActive();
            LibraryItem book = em.find(LibraryItem.class, title);
            book.setAvailable(false);
            // active = tx.isActive(); //tx.isActive();
            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
        } finally {
            em.close();
        }

        return active;
    }
}
