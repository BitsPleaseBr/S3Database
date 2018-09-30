package model.dao.statement;

import java.sql.PreparedStatement;
import model.bean.PacienteBean;
import model.bean.info.Info;
import model.bean.info.PacienteInfo;
import model.bean.info.Tabela;

public class PacienteStatementFactory extends StatementFactory {


  public PreparedStatement insertStatement(PacienteBean bean) {

    // Cria um statement para inserir os dados do paciente na tabela do paciente
    return createInsertStatement(Tabela.Paciente, bean.getInfosPac());
  }

  public PreparedStatement updateStatement(PacienteBean bean) {

    // Obtendo os dados do paciente
    Object idUser = bean.getInfo(PacienteInfo.IDUser);

    // Cria um statement para atualizar os dados do paciente utilizando o id de usuário como
    // condição
    return createUpdateStatement(Tabela.Paciente, bean.getInfosPac(),
        new Info[] {PacienteInfo.IDUser}, new Object[] {idUser});
  }

  public PreparedStatement deleteStatement(int id) {

    // Cria um statement para deletar os dados do paciente utilizando o id de usuário como condição
    return createDeleteStatement(Tabela.Paciente, new Info[] {PacienteInfo.IDUser},
        new Object[] {id});
  }

  public PreparedStatement selectStatement(int id) {

    // Cria um statement para obter os dados do paciente utilizando o id de usuário como condição
    return createSelectStatement(Tabela.Paciente, new Info[] {PacienteInfo.IDUser},
        new Object[] {id}, PacienteInfo.values());
  }
}
