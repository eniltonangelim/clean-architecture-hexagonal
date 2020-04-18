package teste.integracao;

import conta.sistema.dominio.modelo.Conta;
import conta.sistema.dominio.modelo.NegocioException;
import conta.sistema.porta.ContaRepositorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Serviço de banco de dados - Conta")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public class TesteContaRepositorio {

    @Inject
    ContaRepositorio rep;

    @Test
    @DisplayName("pesquisa conta com número nulo")
    void teste1() {
        try {
            var conta = rep.get(null);
            assertTrue(conta == null, "Conta deve ser nulo.");
        } catch (NegocioException e) {
            fail("Deve carregar uma conta nula");
        }
    }

    @Test
    @DisplayName("pesquisa conta com número inexistente")
    void teste2() {
        try {
            var conta = rep.get(8547);
            assertTrue(conta == null, "Conta deve ser nulo.");
        } catch (NegocioException e) {
            fail("Deve carregar uma conta nula");
        }
    }

    @Test
    @DisplayName("pesquisa conta com número existente")
    void teste3() {
        try {
            var conta = rep.get(50);
            assertTrue(conta != null, "Conta deve estar preenchida.");
            System.out.println(conta);
        } catch (NegocioException e) {
            fail("Deve carregar uma conta");
        }
    }

    @Test
    @DisplayName("alterar conta como null")
    void teste4() {
        try {
            rep.alterar(null);
            fail("Não deve alterar uma conta nula");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Conta é obrigatório.");
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("altera conta com sucesso")
    void teste5() {
        try {
            var conta1 = rep.get(50);
            conta1.setSaldo(new BigDecimal("1.00"));
            conta1.setCorrentista("Alterado");
            rep.alterar(conta1);

            var conta2 = rep.get(50);
            assertEquals(conta1.getSaldo(), conta2.getSaldo(), "Deve bater o saldo");
            assertEquals(conta1.getCorrentista(), conta2.getCorrentista(), "Deve bater o correntista");
        } catch (NegocioException e) {
            fail("Deve alterar conta - " + e.getMessage());
        }
    }

}
