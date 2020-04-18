package conta.adaptador;

import conta.sistema.dominio.modelo.Conta;
import conta.sistema.dominio.modelo.NegocioException;
import conta.sistema.porta.ContaRepositorio;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Named
public class AdaptadorContaFakeImp implements ContaRepositorio {

    private Map<Integer, Conta> banco = new HashMap<>();

    public AdaptadorContaFakeImp() {
        banco.put(10, new Conta(10, new BigDecimal(100), "Fernando Fake"));
        banco.put(20, new Conta(20, new BigDecimal(100), "Rebeca Fake"));
    }

    @Override
    public Conta get(Integer numero) {
        System.out.println("Fake banco de dados -> Conta get(numero)");
        return banco.get(numero);
    }

    @Override
    public void alterar(Conta conta) {
        System.out.println("Fake banco de dados -> alterar(numero)");
        var ct = banco.get(conta.getNumero());
        if (!isNull(ct)) {
            banco.put(conta.getNumero(), conta);
        } else {
            throw new NegocioException("Conta inexistente: " + conta.getNumero());
        }
    }

}
