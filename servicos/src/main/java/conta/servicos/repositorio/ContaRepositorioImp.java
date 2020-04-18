package conta.servicos.repositorio;

import conta.sistema.dominio.modelo.Conta;
import conta.sistema.dominio.modelo.NegocioException;
import conta.sistema.porta.ContaRepositorio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

import static java.util.Objects.isNull;

@Named
public class ContaRepositorioImp implements ContaRepositorio {

    private static final String ERRO = "Erro inesperado de acesso ao banco. Entre em contato com o administrador.";

    private JdbcTemplate jdbc;

    @Inject
    public ContaRepositorioImp(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public Conta get(Integer numero) {
        Conta conta = null;

        if (isNull(numero))
            return null;

        var sql = "SELECT * FROM conta WHERE numero = ?";
        var parameter = new Object[]{numero};

        // Object-Relacional Mapping
        RowMapper<Conta> orm = (resultSet, id) ->
                new Conta(resultSet.getInt(1),
                        resultSet.getBigDecimal(2),
                        resultSet.getString(3));

        try {
            var lista = jdbc.query(sql, parameter, orm);
            if (!lista.isEmpty())
                conta = lista.get(0);
        } catch (Exception e) {
            throw new NegocioException(ERRO);
        }

        return conta;
    }

    @Transactional
    @Override
    public void alterar(Conta conta) {
        if (isNull(conta))
            throw new NegocioException("Conta é obrigatório.");

        var sql = "UPDATE conta SET saldo=?, correntista=? WHERE numero=?";
        var parameter = new Object[]{conta.getSaldo(), conta.getCorrentista(), conta.getNumero()};

        try {
            jdbc.update(sql, parameter);
        } catch (Exception e) {
            throw new NegocioException(ERRO);
        }
    }

}
