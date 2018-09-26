package model.dao.statement;

import java.sql.PreparedStatement;
import model.bean.EnderecoBean;
import model.bean.info.EnderecoInfo;
import model.bean.info.Info;
import model.bean.info.Tabela;

public class EnderecoStatementFactory extends StatementFactory {


  public PreparedStatement insertStatement(EnderecoBean bean) {

    // Cria um statement para inserir os dados do endereço na tabela de endereços
    return createInsertStatement(Tabela.Endereco, bean.getInfosEnd());
  }

  public PreparedStatement updateStatement(EnderecoBean bean) {

    // Obtendo dados do endereço
    Object idUser = bean.getInfo(EnderecoInfo.IDUser);
    Object tipoEndereco = bean.getInfo(EnderecoInfo.Tipo);

    // Cria um statement para atualizar os dados do endereço utilizando o id do usuário e o tipo de
    // endereço como condições
    return createUpdateStatement(Tabela.Endereco, bean.getInfosEnd(),
        new Info[] {EnderecoInfo.IDUser, EnderecoInfo.Tipo}, new Object[] {idUser, tipoEndereco}, "and");
  }

  public PreparedStatement deleteStatement(int idUser) {

    // Cria um statement para deletar os dados de um endereço utilizando o id do usuário como
    // condição
    return createDeleteStatement(Tabela.Endereco, new Info[] {EnderecoInfo.IDUser},
        new Object[] {idUser}, "and");
  }

  public PreparedStatement deleteStatement(int idUser, int tipo) {

    // Cria um statement para deletar os dados de um endereço utilizando o id do usuário e o tipo
    // como condições
    return createDeleteStatement(Tabela.Endereco,
        new Info[] {EnderecoInfo.IDUser, EnderecoInfo.Tipo}, new Object[] {idUser, tipo}, "and");
  }

  public PreparedStatement selectStatement(int idUser) {

    // Cria um statement para obter os dados de um endereço utilizando o id do usuário como condição
    return createSelectStatement(Tabela.Endereco, new Info[] {EnderecoInfo.IDUser},
        new Object[] {idUser}, "and", EnderecoInfo.values());
  }

  public PreparedStatement selectStatement(int idUser, int tipo) {

    // Cria um statement para obter os dados de um endereço utilizando o id do usuário e o tipo como
    // condições
    return createSelectStatement(Tabela.Endereco,
        new Info[] {EnderecoInfo.IDUser, EnderecoInfo.Tipo}, new Object[] {idUser, tipo}, "and",
        EnderecoInfo.values());
  }
}
