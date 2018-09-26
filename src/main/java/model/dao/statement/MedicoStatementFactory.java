package model.dao.statement;

import java.sql.PreparedStatement;
import model.bean.MedicoBean;
import model.bean.info.Info;
import model.bean.info.MedicoInfo;
import model.bean.info.Tabela;

public class MedicoStatementFactory extends StatementFactory {


  public PreparedStatement insertStatement(MedicoBean bean) {

    // Cria um statement para inserir os dados do médico na tabela do médico
    return createInsertStatement(Tabela.Medico, bean.getInfosMed());
  }

  public PreparedStatement updateStatement(MedicoBean bean) {

    // Obtendo os dados do médico
    Object idUser = bean.getInfo(MedicoInfo.IDUser);

    // Cria um statement para atualizar as informações do médico utilizando o id de usuário como
    // condição
    return createUpdateStatement(Tabela.Medico, bean.getInfosMed(), new Info[] {MedicoInfo.IDUser},
        new Object[] {idUser}, "and");
  }

  public PreparedStatement deleteStatement(int id) {

    // Cria um statement para deletar as informações do médico utilizando o id de usuário como
    // condição
    return createDeleteStatement(Tabela.Medico, new Info[] {MedicoInfo.IDUser}, new Object[] {id}, "and");
  }

  public PreparedStatement selectStatement(int id) {

    // Cria um statement para obter as informações do médico utilizando o id de usuário como
    // condição
    return createSelectStatement(Tabela.Medico, new Info[] {MedicoInfo.IDUser}, new Object[] {id}, "and",
        MedicoInfo.values());
  }
}
