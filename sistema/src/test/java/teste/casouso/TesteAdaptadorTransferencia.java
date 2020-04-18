package teste.casouso;

import conta.sistema.casouso.port.PortaTransferencia;
import conta.sistema.dominio.modelo.NegocioException;
import conta.sistema.porta.ContaRepositorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static java.util.Objects.isNull;

import javax.inject.Inject;
import java.math.BigDecimal;

@DisplayName("Caso de Uso - Serviço de Transferência")
@ContextConfiguration(classes = Build1.class)
@ExtendWith(SpringExtension.class)
public class TesteAdaptadorTransferencia {

    Integer contaCredito = 10;
    Integer contaDebito = 20;
    Integer contaInexistente = 30;
    BigDecimal cem = new BigDecimal(100);
    BigDecimal cinquenta = new BigDecimal(50);

    @Inject
    PortaTransferencia transferencia;

    @Inject
    ContaRepositorio repositorio;

    @Test
    @DisplayName("pesquisa conta com número nulo")
    void teste1() {
        try {
            var conta = transferencia.getConta(null);
            assertTrue(isNull(conta), "Conta deve ser nula");
        } catch (NegocioException e) {
            fail("Deve carregar uma conta nula");
        }
    }

    @Test
    @DisplayName("pesquisa conta com número inexistente")
    void teste2() {
        try {
            var conta = transferencia.getConta(contaInexistente);
            assertTrue(isNull(conta), "Conta deve ser nula");
        } catch (NegocioException e) {
            fail("Deve carregar uma conta nula");
        }
    }

    @Test
    @DisplayName("pesquisa conta com número existente")
    void teste3() {
        try {
            var conta = transferencia.getConta(contaCredito);
            assertTrue(!isNull(conta), "Conta deve existir");
            System.out.println(conta);
        } catch (NegocioException e) {
            fail("Deve carregar uma conta existente");
        }
    }

    @Test
    @DisplayName("Conta crédito como oobrigatório.")
    void teste4() {
        try {
            transferencia.transferir(contaDebito, null, cinquenta);
            fail("Conta de débito é obrigatório");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Conta crédito é obrigatório");
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Conta débito como oobrigatório")
    void teste5() {
        try {
            transferencia.transferir(null, contaCredito, cinquenta);
            fail("Conta de débito é obrigatório");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Conta débito é obrigatório");
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Conta crédito como oobrigatório")
    void teste6() {
        try {
            transferencia.transferir(contaDebito, null, cinquenta);
            fail("Conta crédito é obrigatório");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Conta crédito é obrigatório");
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Conta crédito é inexistente")
    void teste7() {
        try {
            transferencia.transferir(contaDebito, contaInexistente, cinquenta);
            fail("Conta crédito é inexistente");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Conta crédito é inexistente");
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Conta débito inexistente")
    void teste8() {
        try {
            transferencia.transferir(contaInexistente, contaCredito, cinquenta);
            fail("Conta débito é inexistente");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Conta débito é inexistente");
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("mesma conta débito e crédito")
    void teste9() {
        try {
            transferencia.transferir(contaDebito, contaDebito, cinquenta);
            fail("conta crédito e débito devem ser diferentes");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Conta débito e crédito devem ser diferentes");
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("transferência de 50 reais")
    void teste10() {
        try {
            transferencia.transferir(contaDebito, contaCredito, cinquenta);
        } catch (NegocioException e) {
            fail("Não deve gerar erro de transfrência - " + e.getMessage());
        }

        try {
            var credito = repositorio.get(contaCredito);
            var debito = repositorio.get(contaDebito);
            assertEquals(credito.getSaldo(), cem.add(cinquenta), "Saldo de crédito deve bater");
            assertEquals(debito.getSaldo(), cem.subtract(cinquenta), "Saldo de débito deve bater");
        } catch (NegocioException e) {
            fail("Não deve gerar erro de validação de saldo - " + e.getMessage());
        }

    }

}
