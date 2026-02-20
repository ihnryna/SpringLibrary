package ihnryna.springlibrary.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;


public class RoutingDataSource extends AbstractRoutingDataSource {

    private DataSource replica;

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        this.replica = (DataSource) targetDataSources.get(DataSourceType.REPLICA);
    }

    private boolean isReplicaAvailable() {
        System.out.println("isReplicaAvailable");
        try (Connection ignored = replica.getConnection()) {

            System.out.println("true");
            return true;
        } catch (SQLException ex) {
            System.out.println("false");
            return false;
        }
    }

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly())
            if (isReplicaAvailable())
                return DataSourceType.REPLICA;
        return DataSourceType.PRIMARY;
    }
}
