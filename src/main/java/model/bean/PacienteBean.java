package model.bean;

import java.util.HashMap;
import model.bean.info.PacienteInfo;
import model.bean.info.UserInfo;

public class PacienteBean extends UserBean {


  // HashMap que guarda todas as informações relativas ao paciente
  private HashMap<PacienteInfo, Object> infosPac = new HashMap<>();


  // O construtor define o tipo do UserBean com o valor 1, que representa os usuários que são
  // pacientes no banco de dados
  public PacienteBean() {

    getInfosUser().put(UserInfo.Tipo, 1);
  }

  // Função para guardar alguma informação no map de informações
  public PacienteBean setInfo(PacienteInfo key, Object value) {

    // Caso o ID esteja sendo definido, define na classe superior também
    if (key.equals(PacienteInfo.IDUser))
      super.setInfo(UserInfo.ID, key.parse(value));

    infosPac.put(key, key.parse(value));

    return this;
  }

  // Função para obter alguma informação do map de informações
  public Object getInfo(PacienteInfo key) {

    return infosPac.get(key);
  }

  // Função para obter todas as informações do map de informações
  public HashMap<PacienteInfo, Object> getInfosPac() {

    return infosPac;
  }
}
