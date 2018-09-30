package model.bean;

import java.util.HashMap;
import model.bean.info.TelefoneInfo;

public class TelefoneBean {


  // Variáveis que guardam os valores que indicam que tipo de telefone o bean é
  public transient final int TELEFONE = 1, CELULAR = 2; // o transient serve para o Gson não
                                                        // pegar a variável

  // HashMap que guarda as informações do telefone
  private HashMap<TelefoneInfo, Object> infosTel = new HashMap<>();


  // Função para guardar alguma informação no map de informações
  public TelefoneBean setInfo(TelefoneInfo key, Object value) {

    infosTel.put(key, key.parse(value));
    return this;
  }

  // Função para obter alguma informação do map de informações
  public Object getInfo(TelefoneInfo key) {

    return infosTel.get(key);
  }

  // Função para obter todas as informações do map de informações
  public HashMap<TelefoneInfo, Object> getInfosTel() {

    return this.infosTel;
  }
}
