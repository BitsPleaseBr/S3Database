package model.bean;

import java.util.HashMap;
import model.bean.info.EspecialidadeInfo;

public class EspecialidadeBean {

  
  private HashMap<EspecialidadeInfo, Object> infosEsp = new HashMap<>();
  
  
  public EspecialidadeBean setInfo(EspecialidadeInfo key, Object value) {
    
    this.infosEsp.put(key, key.parse(value));
    
    return this;
  }
  
  public Object getInfo(EspecialidadeInfo key) {
    
    return this.infosEsp.get(key);
  }
  
  public HashMap<EspecialidadeInfo, Object> getInfosEsp() {
    
    return this.infosEsp;
  }
}