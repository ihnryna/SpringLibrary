package ihnryna.springlibrary.service.transactionExperiments;

import ihnryna.springlibrary.model.ReaderAccount;
import ihnryna.springlibrary.repository.ReaderAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DifferentPropagationsReaderAccountService {

    @Autowired
    private ReaderAccountRepository readerRepo;


    @Transactional(propagation = Propagation.REQUIRED)
    public void borrowBookRequired(Long readerId) {
        ReaderAccount reader = readerRepo.findById(readerId).orElseThrow();
        reader.setBalance(reader.getBalance() - 10);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void borrowBookRequiresNew(Long readerId) {
        ReaderAccount reader = readerRepo.findById(readerId).orElseThrow();
        reader.setBalance(reader.getBalance() - 10);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void borrowBookNested(Long readerId) {
        ReaderAccount reader = readerRepo.findById(readerId).orElseThrow();
        reader.setBalance(reader.getBalance() - 10);
        throw new RuntimeException();
    }
}
