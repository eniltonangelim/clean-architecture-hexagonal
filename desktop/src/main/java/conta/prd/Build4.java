package conta.prd;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan({
        // adaptadores front-end
        "conta.prd",
        "conta.tela",
        // objetos do sistema
        "conta.sistema",
        // adaptadores servicos
        "conta.servicos.repositorio"})
public class Build4 {
    // Build 4: Adaptadora JavaFX -> sistema <- Adaptadores Servicos

    @Bean
    public DataSource dataSource() {
        var db = new SimpleDriverDataSource();
        db.setDriverClass(org.hsqldb.jdbcDriver.class);
        db.setUrl("jdbc:hsqldb:file:C:/Users/enilt/Documents/base/");
        db.setUsername("sa");
        db.setPassword("1234");
        return db;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() { return new JdbcTemplate(dataSource()); }

    @Bean
    public DataSourceTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
