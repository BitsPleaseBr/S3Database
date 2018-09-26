package model.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.bean.EnderecoBean;
import model.bean.info.EnderecoInfo;
import model.dao.statement.EnderecoStatementFactory;

public class EnderecoDao {

  // Factory que cria os PreparedStatements para conexão com o banco de dados
  private EnderecoStatementFactory factory = new EnderecoStatementFactory();

  // Função para cadastrar um endereço no banco de dados
  public int cadastrar(EnderecoBean bean, int idUser) throws SQLException {

    // Define o id do usuário no bean
    bean.setInfo(EnderecoInfo.IDUser, idUser);

    // Utiliza a factory para criar um PreparedStatement e inserir o bean no banco de dados
    factory.insertStatement(bean).execute();

    // Retorna o id de usuário de referência
    return idUser;
  }

  // Função para cadastrar vários endereços no banco de dados
  public int cadastrar(List<EnderecoBean> beans, int idUser) throws SQLException {

    // Para cada endereço na lista, chama a função para cadastro individual de endereços
    for (EnderecoBean bean : beans)
      cadastrar(bean, idUser);

    // Retorna o id de usuário referência
    return idUser;
  }

  // Função para alterar um endereço já existente no banco de dados
  public EnderecoDao alterar(EnderecoBean bean) throws SQLException {

    // Utiliza o factory para criar um PreparedStatement e alterar um endereço no banco de dados
    factory.updateStatement(bean).execute();
    return this;
  }

  // Função para deletar todos os endereços de um usuário do banco de dados
  public EnderecoDao deletar(int idUser) throws SQLException {

    // Utiliza o factory para criar um PreparedStatement e deletar os endereços do banco de dados
    factory.deleteStatement(idUser).execute();
    return this;
  }

  // Função para deletar um endereço em específico de um usuário do banco de dados
  public EnderecoDao deletar(int idUser, int tipo) throws SQLException {

    // Utiliza o factory para criar um PreparedStatemente e deletar um endereço do banco de dados
    factory.deleteStatement(idUser, tipo).execute();
    return this;
  }

  // Função para selecionar todos os endereços de um usuário do banco de daodos
  public List<EnderecoBean> selecionar(int idUser) throws SQLException {

    // Lista que guarda os endereços do usuário
    List<EnderecoBean> beans = new ArrayList<>();

    // Obtém o resultado do query utlizando o factory para criar um PreparedStatement e obter os
    // endereços do banco de dados
    ResultSet rs = factory.selectStatement(idUser).executeQuery();

    // Enquanto houverem endereços obtidos, os adiciona à lista de endereços
    while (rs.next())
      beans.add(createBean(rs));

    // Retorna a lista de endereços do usuário
    return beans;
  }

  // Função para obter um endereço em específico de um usuário do banco de dados
  public EnderecoBean selecionar(int idUser, int tipo) throws SQLException {

    // Obtém o resultado do query
    ResultSet rs = factory.selectStatement(idUser, tipo).executeQuery();

    // Cria uma variável de um novo endereço
    EnderecoBean eb = null;

    // Caso haja um endereço encontrado, define as informações no endereço acima
    while (rs.next())
      eb = createBean(rs);

    // Retorna o endereço
    return eb;
  }

  // Função utilizada para criar beans a partir de um ResultSet
  private EnderecoBean createBean(ResultSet rs) throws SQLException {

    // Cria o bean
    EnderecoBean eb = new EnderecoBean();

    // Obtém o metadata do ResultSet
    ResultSetMetaData rsmd = rs.getMetaData();

    // Passa por cada coluna no metadata
    for (int i = 1; i <= rsmd.getColumnCount(); i++) {

      // Obtém o nome da coluna
      String colName = rsmd.getColumnName(i);

      // Passa por cada informação de endereço especificada no enum de informações
      for (EnderecoInfo info : EnderecoInfo.values()) {

        // Verifica se o nome da coluna é igual ao campo que a informação representa na tabela
        if (info.getCampo().equals(colName)) {

          // Salva a informação no bean
          eb.setInfo(info, rs.getObject(i));
          break;
        }
      }
    }

    // Retorna o bean
    return eb;
  }
}
