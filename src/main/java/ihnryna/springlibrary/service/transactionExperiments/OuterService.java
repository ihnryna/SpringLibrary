package ihnryna.springlibrary.service.transactionExperiments;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class OuterService {
    @Transactional
    public boolean outerSecond() {
        return TransactionSynchronizationManager.isActualTransactionActive();
    }
}
