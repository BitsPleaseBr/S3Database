package model.dao.statement;

import java.sql.PreparedStatement;
import model.bean.TelefoneBean;
import model.bean.info.Info;
import model.bean.info.Tabela;
import model.bean.info.TelefoneInfo;

public class TelefoneStatementFactory extends StatementFactory {


  public PreparedStatement insertStatement(TelefoneBean bean) {

    // Cria um statement para inserir os dados do telefone na tabela do telefone
    return createInsertStatement(Tabela.Telefone, bean.getInfosTel());
  }

  public PreparedStatement updateStatement(TelefoneBean bean) {

    // Obtendo informações do telefone
    Object idUser = bean.getInfo(TelefoneInfo.IDUser);
    Object tipo = bean.getInfo(TelefoneInfo.Tipo);

    // Cria um statement para atualizar os dados do telefone utilizando o id do usuário e o tipo
    // como condições
    return createUpdateStatement(Tabela.Telefone, bean.getInfosTel(),
        new Info[] {TelefoneInfo.IDUser, TelefoneInfo.Tipo}, new Object[] {idUser, tipo});
  }

  public PreparedStatement deleteStatement(int idUser) {

    // Cria um statement para deletar os dados do telefone utilizando o id do usuário como condição
    return createDeleteStatement(Tabela.Telefone, new Info[] {TelefoneInfo.IDUser},
        new Object[] {idUser});
  }

  public PreparedStatement deleteStatement(int idUser, int tipo) {

    // Cria um statement para deletar os dados do telefone utilizando o id do usuário e o tipo como
    // condições
    return createDeleteStatement(Tabela.Telefone,
        new Info[] {TelefoneInfo.IDUser, TelefoneInfo.Tipo}, new Object[] {idUser, tipo});
  }

  public PreparedStatement selectStatement(int idUser) {

    // Cria um statement para obter os dados do telefone utilizando o id do usuário como condição
    return createSelectStatement(Tabela.Telefone, new Info[] {TelefoneInfo.IDUser},
        new Object[] {idUser}, TelefoneInfo.values());
  }

  public PreparedStatement selectStatement(int idUser, int tipo) {

    // Cria um statement para obter os dados do telefone utilizando o id do usuário e o tipo como
    // condições
    return createSelectStatement(Tabela.Telefone,
        new Info[] {TelefoneInfo.IDUser, TelefoneInfo.Tipo}, new Object[] {idUser, tipo},
        TelefoneInfo.values());
  }
}
