package conta.sistema.casouso.imp;

import conta.sistema.casouso.port.PortaTransferencia;
import conta.sistema.dominio.modelo.Conta;
import conta.sistema.dominio.servico.Transferencia;
import conta.sistema.porta.ContaRepositorio;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;

import static conta.sistema.dominio.modelo.Erro.*;
import static java.util.Objects.isNull;

@Named
public class PortTransferenciaImp implements PortaTransferencia {

    private final ContaRepositorio repositorio;
    private final Transferencia transferencia;

    @Inject
    public PortTransferenciaImp(ContaRepositorio repositorio, Transferencia transferencia) {
        this.repositorio = repositorio;
        this.transferencia = transferencia;
    }

    @Override
    public Conta getConta(Integer numero) {
        return repositorio.get(numero);
    }

    @Override
    @Transactional
    public void transferir(Integer contaDebito, Integer contaCredito, BigDecimal valor) {
        // 1. validação de parametos
        if (isNull(contaDebito)) obrigatorio("Conta débito");
        if (isNull(contaCredito)) obrigatorio("Conta crédito");
        if (isNull(valor)) obrigatorio("Valor");

        // 2. validação das contas
        final Conta ctDebito = repositorio.get(contaDebito);
        if (isNull(ctDebito)) inexistente("Conta débito");

        final Conta ctCredito = repositorio.get(contaCredito);
        if (isNull(ctCredito)) inexistente("Conta crédito");

        // 3. validação mesma conta
        if (ctDebito.getNumero().equals(ctCredito.getNumero())) mesmaConta();

        // 4. operação
        this.transferencia.transferencia(valor, ctDebito, ctCredito);
        repositorio.alterar(ctDebito);
        repositorio.alterar(ctCredito);
    }

}
