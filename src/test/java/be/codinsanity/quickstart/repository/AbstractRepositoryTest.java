package be.codinsanity.quickstart.repository;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/applicationContext.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class})
public abstract class AbstractRepositoryTest {

    protected JdbcTemplate jdbcTemplate;

    /**
     * Logger available to subclasses.
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * The {@link org.springframework.context.ApplicationContext} that was injected into this test instance
     */
    protected ApplicationContext applicationContext;

    /**
     * Set the {@link ApplicationContext} to be used by this test instance,
     * provided via {@link org.springframework.context.ApplicationContextAware} semantics.
     */
    public final void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private String sqlScriptEncoding;


    /**
     * Set the DataSource, typically provided via Dependency Injection.
     */
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Specify the encoding for SQL scripts, if different from the platform encoding.
     * @see #executeSqlScript
     */
    public void setSqlScriptEncoding(String sqlScriptEncoding) {
        this.sqlScriptEncoding = sqlScriptEncoding;
    }

    /**
     * Count the rows in the given table.
     * @param tableName table name to count rows in
     * @return the number of rows in the table
     */
    protected int countRowsInTable(String tableName) {
        return JdbcTestUtils.countRowsInTable(this.jdbcTemplate, tableName);
    }

    /**
     * Convenience method for deleting all rows from the specified tables. Use
     * with caution outside of a transaction!
     * @param names the names of the tables from which to delete
     * @return the total number of rows deleted from all specified tables
     */
    protected int deleteFromTables(String... names) {
        return JdbcTestUtils.deleteFromTables(this.jdbcTemplate, names);
    }

    /**
     * Execute the given SQL script. Use with caution outside of a transaction!
     * <p>The script will normally be loaded by classpath. There should be one
     * statement per line. Any semicolons will be removed. <b>Do not use this
     * method to execute DDL if you expect rollback.</b>
     * @param sqlResourcePath the Spring resource path for the SQL script
     * @param continueOnError whether or not to continue without throwing an
     * exception in the event of an error
     * @throws org.springframework.dao.DataAccessException if there is an error executing a statement
     * and continueOnError was <code>false</code>
     */
    protected void executeSqlScript(String sqlResourcePath, boolean continueOnError) throws DataAccessException {
        Resource resource = this.applicationContext.getResource(sqlResourcePath);
        JdbcTestUtils.executeSqlScript(this.jdbcTemplate,
                new EncodedResource(resource, this.sqlScriptEncoding),
                continueOnError);
    }

}
