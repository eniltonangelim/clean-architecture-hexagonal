package conta.sistema.porta;

import conta.sistema.dominio.modelo.Conta;

public interface ContaRepositorio {
    Conta get(Integer numero);
    void alterar(Conta conta);
}
