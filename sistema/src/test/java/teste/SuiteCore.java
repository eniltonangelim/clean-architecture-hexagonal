package teste;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages({
        // Teste regras de negocio
        "teste.unidade.dominio.modelo",
        // teste de serviços
        "teste.unidade.dominio.servico",
        // Teste porta de entrada (driver)
        "teste.casouso",
})
public class SuiteCore {
}
