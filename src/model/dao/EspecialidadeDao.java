package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.bean.EspecialidadeBean;
import model.bean.info.EspecialidadeInfo;
import model.bean.info.MedicoHasEspecialidadeInfo;
import model.bean.info.Tabela;
import model.dao.statement.EspecialidadeStatementFactory;
import model.dao.statement.StatementBuilder;

public class EspecialidadeDao {

  
  private EspecialidadeStatementFactory factory = new EspecialidadeStatementFactory();
  
  
  public int cadastrar(EspecialidadeBean bean, int idUser) throws SQLException {
    
    int id = -1;
    
    try {
      
      ResultSet rs = factory.selectStatement((String) bean.getInfo(EspecialidadeInfo.Descricao)).executeQuery();
      
      boolean existe = rs.next();
      
      if (existe) 
        id = rs.getInt("id");
      else {
        
        PreparedStatement ps = factory.insertStatement(bean);
        ps.executeUpdate();
        
        rs = ps.getGeneratedKeys();
        
        while (rs.next())
          id = rs.getInt(1);
      }
      
    } catch (SQLException e) {
      
      e.printStackTrace();
      System.out.println("Não foi possível verificar se a especialidade já existe");
      
      return -1;
    }
    
    
    if (id > 0) {
      
      StatementBuilder sb = new StatementBuilder();
      
      HashMap<MedicoHasEspecialidadeInfo, Object> dados = new HashMap<>();
      
      dados.put(MedicoHasEspecialidadeInfo.MedicoId, idUser);
      dados.put(MedicoHasEspecialidadeInfo.EspecialidadeId, id);
      
      PreparedStatement ps = sb
                            .withTabela(Tabela.Medico_Has_Especialidade)
                            .withTipo(sb.INSERT)
                            .withMap(dados)
                            .build();
      
      ps.execute();
    }
    
    
    return id;
  }
  
  public void cadastrar(ArrayList<EspecialidadeBean> beans, int idUser) {
    
    for (EspecialidadeBean bean : beans) {
      try {
        cadastrar(bean, idUser);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
  
  public ArrayList<EspecialidadeBean> selecionar(int idUser) {
    
    ArrayList<EspecialidadeBean> beans = new ArrayList<>();
    
    StatementBuilder sb = new StatementBuilder();
    
    PreparedStatement ps = sb
                            .withTabela(Tabela.Medico_Has_Especialidade)
                            .withTipo(sb.SELECT)
                            .withInfos(MedicoHasEspecialidadeInfo.EspecialidadeId)
                            .addCondition(MedicoHasEspecialidadeInfo.MedicoId, "and")
                            .addConditionValue(idUser)
                            .build();
                  
    ArrayList<Integer> ids = new ArrayList<>();
    
    try {
      
      ResultSet rs = ps.executeQuery();
      
      while(rs.next())
        ids.add(rs.getInt(1));
      
    } catch (SQLException e) {
      
      e.printStackTrace();
      System.out.println("Não foi possível obter as especializações do usuário com id: " + idUser);
    }
    
    for (int id : ids) {
      
      try {
        
        ResultSet rs = factory.selectStatement(id).executeQuery();
        
        while (rs.next())
          beans.add(new EspecialidadeBean().setInfo(EspecialidadeInfo.Descricao, rs.getString("descricao")).setInfo(EspecialidadeInfo.Id, id));
        
      } catch (SQLException e) {
        
        System.out.println("Erro ao obter informações da especialidade com id: " + id);
        e.printStackTrace();
      }
    }
    
    return beans;
  }
}