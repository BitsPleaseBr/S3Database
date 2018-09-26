package model.bean;

import java.util.ArrayList;
import java.util.HashMap;
import model.bean.info.MedicoInfo;
import model.bean.info.UserInfo;

public class MedicoBean extends UserBean {


  // HashMap que guarda todas as informações relativas ao médico
  private HashMap<MedicoInfo, Object> infosMed = new HashMap<>();
  private ArrayList<EspecialidadeBean> especialidades = new ArrayList<>();
  

  // O construtor define o tipo do UserBean com o valor 2, que representa os usuários que são
  // médicos no banco de dados
  public MedicoBean() {

    getInfosUser().put(UserInfo.Tipo, 2);
  }

  // Função para guardar alguma informação no map de informações
  public MedicoBean setInfo(MedicoInfo key, Object value) {

    // Caso o ID esteja sendo definido, define na classe superior também
    if (key.equals(MedicoInfo.IDUser))
      getInfosUser().put(UserInfo.ID, key.parse(value));

    infosMed.put(key, key.parse(value));

    return this;
  }

  // Função para obter alguma informações do map de informações
  public Object getInfo(MedicoInfo key) {

    return infosMed.get(key);
  }
  
  public MedicoBean addEspecialidade(EspecialidadeBean bean) {
    
    this.especialidades.add(bean);
    return this;
  }
  
  public ArrayList<EspecialidadeBean> getEspecialidades() {
    
    return this.especialidades;
  }

  // Função para obter todas as informações do map de informações
  public HashMap<MedicoInfo, Object> getInfosMed() {

    return infosMed;
  }
}
