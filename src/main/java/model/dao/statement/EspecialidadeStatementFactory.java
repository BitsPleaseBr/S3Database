package model.dao.statement;

import java.sql.PreparedStatement;
import model.bean.EspecialidadeBean;
import model.bean.info.EspecialidadeInfo;
import model.bean.info.Info;
import model.bean.info.Tabela;

public class EspecialidadeStatementFactory extends StatementFactory {

  
  public PreparedStatement insertStatement(EspecialidadeBean bean) {
    
    return createInsertStatement(Tabela.Especialidade, bean.getInfosEsp());
  }
  
  public PreparedStatement selectStatement(String descricao) {
    
    return createSelectStatement(Tabela.Especialidade, new Info[] {EspecialidadeInfo.Descricao}, new Object[] {descricao}, EspecialidadeInfo.values());
  }
  
  public PreparedStatement selectStatement(int id) {
    
    return createSelectStatement(Tabela.Especialidade, new Info[] {EspecialidadeInfo.Id}, new Object[] {id}, EspecialidadeInfo.values());
  }
  
  public PreparedStatement selectStatement(int[] ids) {
    
    Info[] infos = new Info[ids.length];
    Object[] values = new Object[ids.length];
    
    for (int i = 0; i < ids.length; i++) {
      
      infos[i] = EspecialidadeInfo.Id;
      values[i] = ids[i];
    }
    
    return createSelectStatement(Tabela.Especialidade, infos, values, "or", EspecialidadeInfo.values());
  }
}