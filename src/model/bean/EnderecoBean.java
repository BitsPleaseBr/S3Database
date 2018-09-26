package model.bean;

import java.util.HashMap;
import model.bean.info.EnderecoInfo;

public class EnderecoBean {


  // Variáveis que guardam os valores que indicam que tipo de endereço o bean é
  public transient final int RESIDENCIAL = 1, COMERCIAL = 2; // o transient serve para o Gson não
                                                             // pegar a variável

  // HashMap que guarda as informações do endereço
  private HashMap<EnderecoInfo, Object> infosEnd = new HashMap<>();


  // Função para guardar alguma informação no map de informações
  public EnderecoBean setInfo(EnderecoInfo key, Object value) {

    infosEnd.put(key, key.parse(value));
    return this;
  }

  // Função para obter alguma informação do map de informações
  public Object getInfo(EnderecoInfo key) {

    return infosEnd.get(key);
  }

  // Função para obter todas as informações do map de informações
  public HashMap<EnderecoInfo, Object> getInfosEnd() {

    return this.infosEnd;
  }
}
