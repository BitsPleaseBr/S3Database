package model.dao.statement;

import java.sql.PreparedStatement;
import java.util.HashMap;
import model.bean.info.Info;
import model.bean.info.Tabela;

public class StatementFactory {


  protected PreparedStatement createInsertStatement(Tabela tabela,
      HashMap<? extends Info, Object> dados) {

    StatementBuilder sb = new StatementBuilder();

    return sb.withTabela(tabela).withTipo(sb.INSERT).withMap(dados).build();
  }


  protected PreparedStatement createSelectStatement(Tabela tabela, Info[] conditions,
      Object[] conditionsValues, String conditional, Info... infos) {

    StatementBuilder sb = new StatementBuilder();
    sb.withTabela(tabela).withTipo(sb.SELECT).withInfos(infos);

    for (int i = 0; i < conditions.length; i++)
      sb.addCondition(conditions[i], conditional).addConditionValue(conditionsValues[i]);

    return sb.build();
  }
  
  protected PreparedStatement createSelectStatement(Tabela tabela, Info[] conditions, Object[] conditionsValues, Info... infos) {
    
    return this.createSelectStatement(tabela, conditions, conditionsValues, "and", infos);
  }

  protected PreparedStatement createUpdateStatement(Tabela tabela,
      HashMap<? extends Info, Object> dados, Info[] conditions, Object[] conditionsValues, String conditional) {

    StatementBuilder sb = new StatementBuilder();

    sb.withTabela(tabela).withTipo(sb.UPDATE).withMap(dados);

    for (Info condition : conditions)
      sb.addCondition(condition, conditional);

    for (Object conditionValue : conditionsValues)
      sb.addConditionValue(conditionValue);

    return sb.build();
  }
  
  protected PreparedStatement createUpdateStatement(Tabela tabela, HashMap<? extends Info, Object> dados, Info[] conditions, Object[]conditionsValues) {
    
    return this.createUpdateStatement(tabela, dados, conditions, conditionsValues, "and");
  }


  protected PreparedStatement createDeleteStatement(Tabela tabela, Info[] conditions,
      Object[] conditionsValues, String conditional) {

    StatementBuilder sb = new StatementBuilder();
    sb.withTabela(tabela).withTipo(sb.DELETE);

    for (int i = 0; i < conditions.length; i++) {

      sb.addCondition(conditions[i], conditional).addConditionValue(conditionsValues[i]);
    }

    return sb.build();
  }
  
  protected PreparedStatement createDeleteStatement(Tabela tabela, Info[] conditions, Object[] conditionsValues) {
    
    return this.createDeleteStatement(tabela, conditions, conditionsValues, "and");
  }
}
