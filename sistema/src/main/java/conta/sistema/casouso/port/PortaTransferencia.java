package conta.sistema.casouso.port;

import conta.sistema.dominio.modelo.Conta;

import java.math.BigDecimal;

public interface PortaTransferencia {
    Conta getConta(Integer numero);
    void transferir(Integer contaDebito, Integer contaCredito, BigDecimal valor);
}
