package conta.dsv;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        // adaptadores front-end
        "conta.dsv",
        "conta.tela",
        // objetos do sistema
        "conta.sistema",
        // adaptadores falsos
        "conta.adaptador"})
public class Build2 {
    // Build 2: Adaptadora JavaFX -> sistema <- Adaptadores Mock
}
