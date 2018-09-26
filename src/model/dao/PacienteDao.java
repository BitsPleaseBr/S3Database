package model.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import model.bean.PacienteBean;
import model.bean.UserBean;
import model.bean.info.PacienteInfo;
import model.dao.statement.PacienteStatementFactory;

public class PacienteDao extends UserDao {

  // Factory que cria os PreparedStatements para conexão com banco de dados
  private PacienteStatementFactory factory = new PacienteStatementFactory();

  // Função para cadastrar um paciente
  public int cadastrar(PacienteBean bean) throws SQLException {

    // Chama o cadastra da classe superior e obtém o id do usuário cadastrado
    int id = super.cadastrar(bean);

    // Define o id do usuáario no bean
    bean.setInfo(PacienteInfo.IDUser, id);

    // Cadastra a parte paciente do bean
    factory.insertStatement(bean).execute();

    // Retorna o id do usuário cadastrado
    return id;
  }

  // Função para alterar um paciente no banco de dados
  public PacienteDao alterar(PacienteBean bean) throws SQLException {

    // Chama o alterar da classe superior para alterar as informações do usuário comum
    super.alterar(bean);

    // Altera as informações de paciente
    factory.updateStatement(bean).execute();

    return this;
  }

  // Função para deletar um paciente do banco de dados
  public PacienteDao deletar(int id) throws SQLException {

    // Chama a classe superior para deletar o usuário da tabela principal, como o banco tem cascade
    // já deleta tudo o que contenha o id do usuário das outras tabelas
    super.deletar(id);
    return this;
  }

  // Função para obter um paciente do banco de dados
  public PacienteBean selecionar(int id) throws SQLException {

    // Obtém os dados de um usuário comum
    UserBean ub = super.selecionar(id);

    // Instancia o bean do paciente
    PacienteBean pb = new PacienteBean();

    // Define informações de usuário comum no bean do paciente
    pb.getInfosUser().putAll(ub.getInfosUser());

    // Faz o query das informações do paciente
    ResultSet rs = factory.selectStatement(id).executeQuery();

    // Caso haja alguma informação no query
    while (rs.next()) {

      // Obtém o metadata do ResultSet
      ResultSetMetaData rsmd = rs.getMetaData();

      // Passa por cada coluna do ResultSet
      for (int i = 1; i <= rsmd.getColumnCount(); i++) {

        // Obtém o nome da coluna
        String colName = rsmd.getColumnName(i);

        // Passa por cada informação do enum de informações do paciente
        for (PacienteInfo info : PacienteInfo.values()) {

          // Verifica se o nome da coluna é igual ao nome da coluna que a informação representa no
          // banco
          if (info.getCampo().equals(colName)) {

            // Salva a informação no bean
            pb.setInfo(info, rs.getObject(i));
            break;
          }
        }
      }
    }

    // Retorna o bean
    return pb;
  }
}
