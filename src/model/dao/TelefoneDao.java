package model.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bean.TelefoneBean;
import model.bean.info.TelefoneInfo;
import model.dao.statement.TelefoneStatementFactory;

public class TelefoneDao {

  // Factory que cria os PreparedStatements para conexão com banco de dados
  private TelefoneStatementFactory factory = new TelefoneStatementFactory();

  // Função para cadastrar um telefone
  public int cadastrar(TelefoneBean bean, int idUser) throws SQLException {

    // Salva o id do usuário no telefone
    bean.setInfo(TelefoneInfo.IDUser, idUser);

    // Cadastra o telefone
    factory.insertStatement(bean).execute();

    // Retorna o id do usuário referência
    return idUser;
  }

  // Função para cadastrar uma lista de telefones
  public TelefoneDao cadastrar(List<TelefoneBean> beans, int idUser) throws SQLException {

    // Para cada telefone na lista, chama a função para cadastro individual
    for (TelefoneBean bean : beans)
      cadastrar(bean, idUser);
    return this;
  }

  // Função para alterar um telefone no banco de dados
  public TelefoneDao alterar(TelefoneBean bean) throws SQLException {

    // Altera os dados do telefone no banco
    factory.updateStatement(bean).execute();
    return this;
  }

  // Função para deletar todos os telefones de um usuário no banco
  public TelefoneDao deletar(int idUser) throws SQLException {

    // Deleta os telefones do banco de dados
    factory.deleteStatement(idUser).execute();
    return this;
  }

  // Função para deletar um telefone em específico de um usuário
  public TelefoneDao deletar(int idUser, int tipo) throws SQLException {

    // Deleta o telefone do banco de dados
    factory.deleteStatement(idUser, tipo).execute();
    return this;
  }

  // Função para obter todos os telefones de um usuário
  public List<TelefoneBean> selecionar(int idUser) throws SQLException {

    // Cria uma lista de telefones
    List<TelefoneBean> beans = new ArrayList<>();

    // Faz o query dos dados dos telefones do usuário
    ResultSet rs = factory.selectStatement(idUser).executeQuery();

    // Enquanto houverem dados, cria os telefones e adiciona à lista
    while (rs.next())
      beans.add(createBean(rs));

    // Retorna a lista de telefones
    return beans;
  }

  // Função para obter um telefone específico de um usuário
  public TelefoneBean selecionar(int idUser, int tipo) throws SQLException {

    // Faz o query do telefone
    ResultSet rs = factory.selectStatement(idUser, tipo).executeQuery();

    // Declara a variável do bean
    TelefoneBean tb = null;

    // Caso haja um telefone, salva-o na variável
    while (rs.next())
      tb = createBean(rs);

    // Retorna o telefone
    return tb;
  }

  // Função para criar um bean a partir de um ResultSet
  private TelefoneBean createBean(ResultSet rs) throws SQLException {

    // Instacia o bean
    TelefoneBean tb = new TelefoneBean();

    // Obtém o metadata do ResultSet
    ResultSetMetaData rsmd = rs.getMetaData();

    // Passa por cada coluna do metadata
    for (int i = 1; i <= rsmd.getColumnCount(); i++) {

      // Obtém o nome da coluna
      String colName = rsmd.getColumnName(i);

      // Passa por cada informação do enum de informações de telefone
      for (TelefoneInfo info : TelefoneInfo.values()) {

        // Verifica se o nome da coluna é igual ao nome da coluna que a informação representa no
        // banco
        if (info.getCampo().equals(colName))
          // Salva a informação no bean
          tb.setInfo(info, rs.getObject(i));
        break;
      }
    }

    // Retorna o bean
    return tb;
  }
}
