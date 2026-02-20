package ihnryna.springlibrary.datasourceTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoutingTestService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public String readTest() {
        return jdbcTemplate.queryForObject(
                "SELECT current_setting('application_name')",
                String.class
        );
    }

    @Transactional
    public String writeTest() {
        return jdbcTemplate.queryForObject(
                "SELECT current_setting('application_name')",
                String.class
        );
    }

}
