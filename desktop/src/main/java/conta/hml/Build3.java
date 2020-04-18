package conta.hml;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan({
        // adaptadores front-end
        "conta.hml",
        "conta.tela",
        // objetos do sistema
        "conta.sistema",
        // adaptadores servicos
        "conta.servicos.repositorio"})
public class Build3 {
    // Build 3: Adaptadora JavaFX -> sistema <- Adaptadores Servicos

    @Bean
    public DataSource dataSource() {
        var builder = new EmbeddedDatabaseBuilder();
        var db = builder.setType(EmbeddedDatabaseType.HSQL.HSQL)
                .addScript("create-db.sql")
                .addScript("insert-hml.sql")
                .build();
        return db;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() { return new JdbcTemplate(dataSource()); }

    @Bean
    public DataSourceTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
