package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import model.bean.UserBean;
import model.bean.info.UserInfo;
import model.dao.statement.UserStatementFactory;

public abstract class UserDao {

  // Factory que cria os PreparedStatements para conexão com banco de dados
  private UserStatementFactory factory = new UserStatementFactory();

  // Função para cadastrar um usuário
  protected int cadastrar(UserBean bean) throws SQLException {

    // Obtém o PreparedStatement para fazer o cadastro
    PreparedStatement ps = factory.insertStatement(bean);

    // Cadastra
    ps.executeUpdate();

    // Obtém o id gerado pelo cadastro
    ResultSet rs = ps.getGeneratedKeys();

    // Caso haja um id gerado, retorna-o
    if (rs.next())
      return rs.getInt(1);

    // Retorna -1 caso tenha ocorrido um erro no cadastro
    return -1;
  }

  // Função para alterar um usuário
  protected UserDao alterar(UserBean bean) throws SQLException {

    // Altera as informações do usuário no banco
    factory.updateStatement(bean).execute();
    return this;
  }

  // Função para deletar um usuário
  protected UserDao deletar(int id) throws SQLException {

    // Deleta as informações do usuário do banco
    factory.deleteStatement(id).execute();
    return this;
  }

  // Função para obter um usuário
  protected UserBean selecionar(int id) throws SQLException {

    // Faz o query dos dados do usuário
    ResultSet rs = factory.selectStatement(id).executeQuery();

    // Cria um novo bean de usuário
    UserBean bean = new UserBean();

    // Enquanto houver informações de usuário
    while (rs.next()) {

      // Obtém o metadata do ResultSet
      ResultSetMetaData rsmd = rs.getMetaData();

      // Passa por cada coluna do metadata
      for (int i = 1; i <= rsmd.getColumnCount(); i++) {

        // Obtém o nome da coluna
        String colName = rsmd.getColumnName(i);

        // Passa por cada informação do enum de informações do usuário
        for (UserInfo info : UserInfo.values()) {

          // Verifica se o nome da coluna é igual ao nome da coluna que a informação representa no
          // banco
          if (info.getCampo().equals(colName)) {
            // Salva a informação no bean
            bean.setInfo(info, rs.getObject(i));
            break;
          }
        }
      }
    }

    // Retorna o bean
    return bean;
  }

  /*
   * public int login(String email, String senha) throws SQLException {
   * 
   * StatementBuilder sf = new StatementBuilder();
   * 
   * ResultSet rs = sf.setTabela(Tabela.User).setTipo(sf.SELECT).setInfos(UserInfo.ID,
   * UserInfo.Senha) .addCondition(UserInfo.Email).addConditionValue(email).build().executeQuery();
   * 
   * while (rs.next()) {
   * 
   * Blob senhaServer = rs.getBlob(2); int length = (int) senhaServer.length();
   * 
   * if (PswdStorage.compararHashClient(senha, senhaServer.getBytes(1, length))) return
   * rs.getInt(1); }
   * 
   * return -1; }
   */
}
