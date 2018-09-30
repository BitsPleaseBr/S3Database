package model.dao.statement;

import java.sql.PreparedStatement;
import model.bean.UserBean;
import model.bean.info.Info;
import model.bean.info.Tabela;
import model.bean.info.UserInfo;

public class UserStatementFactory extends StatementFactory {


  public PreparedStatement insertStatement(UserBean bean) {

    // Cria um statement para inserir os dados do usuário na tabela de usuário
    return createInsertStatement(Tabela.User, bean.getInfosUser());
  }

  public PreparedStatement updateStatement(UserBean bean) {

    // Obtendo dados do usuário
    Object id = bean.getInfo(UserInfo.ID);

    // Cria um statement para atualizar os dados do usuário utilizando o id como condição
    return createUpdateStatement(Tabela.User, bean.getInfosUser(), new Info[] {UserInfo.ID},
        new Object[] {id});
  }

  public PreparedStatement deleteStatement(int id) {

    // Cria um statement para deletar os dados do usuário utilizando o id como condição
    return createDeleteStatement(Tabela.User, new Info[] {UserInfo.ID}, new Object[] {id});
  }

  public PreparedStatement selectStatement(int id) {

    // Cria um statement para obter os dados do usuário utilizando o id como condição
    return createSelectStatement(Tabela.User, new Info[] {UserInfo.ID}, new Object[] {id},
        UserInfo.values());
  }
}
