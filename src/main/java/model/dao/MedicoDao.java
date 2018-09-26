package model.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import model.bean.EnderecoBean;
import model.bean.MedicoBean;
import model.bean.TelefoneBean;
import model.bean.UserBean;
import model.bean.info.MedicoInfo;
import model.dao.statement.MedicoStatementFactory;

public class MedicoDao extends UserDao {

  // Factory que cria os PreparedStatements para conexão com banco de dados
  private MedicoStatementFactory factory = new MedicoStatementFactory();

  // Função para cadastrar um médico
  public int cadastrar(MedicoBean bean) throws SQLException {

    // Chama o cadastro da classe superior e obtém o id do usuário cadastrado
    int id = super.cadastrar(bean);

    // Define o id do usuário no bean
    bean.setInfo(MedicoInfo.IDUser, id);

    // Cadastra a parte médica do bean
    factory.insertStatement(bean).execute();

    // Cadastra os endereços e telefones no banco de dados
    new EnderecoDao().cadastrar(bean.getEnderecos(), id);
    new TelefoneDao().cadastrar(bean.getTelefones(), id);
    new EspecialidadeDao().cadastrar(bean.getEspecialidades(), id);
    
    // Retorna o id do usuário cadastrado
    return id;
  }

  // Função para alterar um médico no banco de dados
  public MedicoDao alterar(MedicoBean bean) throws SQLException {

    // Chama o alterar da classe superior para alterar as informações do usuário comum
    super.alterar(bean);

    // Altera as informações de médico no banco de dados
    factory.updateStatement(bean).execute();

    // Altera as informações de cada endereço no banco
    for (EnderecoBean enderecoBean : bean.getEnderecos())
      new EnderecoDao().alterar(enderecoBean);

    // Altera as informações de telefone no banco
    for (TelefoneBean telefoneBean : bean.getTelefones())
      new TelefoneDao().alterar(telefoneBean);

    return this;
  }

  // Função para deletar um médico do banco de dados
  public MedicoDao deletar(int id) throws SQLException {

    // Chama a classe superior para deletar o usuário da tabela principal, como o banco tem cascade
    // já deleta tudo o que contenha o id do usuário das outras tabelas
    super.deletar(id);
    return this;
  }

  // Função para obter um médico do banco de dados
  public MedicoBean selecionar(int id) throws SQLException {

    // Obtém os dados de um usuário do banco
    UserBean ub = super.selecionar(id);

    // Instancia o bean do médico
    MedicoBean mb = new MedicoBean();

    // Passa todas as informações de usuário para o bean do médico
    mb.getInfosUser().putAll(ub.getInfosUser());

    // Faz o query das informações do médico
    ResultSet rs = factory.selectStatement(id).executeQuery();

    // Caso haja alguma informação no query
    while (rs.next()) {

      // Obtém o metadata do ResultSet
      ResultSetMetaData rsmd = rs.getMetaData();

      // Passa por cada coluna no metadata
      for (int i = 1; i <= rsmd.getColumnCount(); i++) {

        // Obtém o nome da coluna
        String colName = rsmd.getColumnName(i);

        // Passa por cada informação do enum de informações do médico
        for (MedicoInfo info : MedicoInfo.values()) {

          // Verifica se o nome da coluna é igual ao nome da coluna que a informação representa no
          // banco
          if (info.getCampo().equals(colName)) {

            // Salva a informação no bean
            mb.setInfo(info, rs.getObject(i));
            break;
          }
        }
      }
    }

    // Obtém os endereços e telefones do usuário
    mb.addEnderecos(new EnderecoDao().selecionar(id));
    mb.addTelefones(new TelefoneDao().selecionar(id));

    // Retorna o bean
    return mb;
  }

}
