package teste.casouso;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        "conta.sistema",
        "conta.adaptador"})
public class Build1 {
    // Build 1: Adaptadora de teste -> sistema <- Adaptadores Mock
}
