package model.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.bean.info.UserInfo;

public class UserBean {


  // HashMap que guarda as informações do usuário
  private HashMap<UserInfo, Object> infosUser = new HashMap<>();

  // ArrayLists que guardam os Telefones e Endereços do usuário
  private ArrayList<EnderecoBean> enderecos = new ArrayList<>();
  private ArrayList<TelefoneBean> telefones = new ArrayList<>();


  // Construtor que define a situação do usuário como 1, que no banco representa um usuário na fase
  // de pré-cadastro
  public UserBean() {

    this.infosUser.put(UserInfo.Situacao, 1);
  }

  // Função para guardar alguma informação no map de informações
  public UserBean setInfo(UserInfo key, Object value) {

    this.infosUser.put(key, key.parse(value));
    return this;
  }

  // Função para obter alguma informação do map de informações
  public Object getInfo(UserInfo key) {

    return this.infosUser.get(key);
  }

  // Função para obter todas as informações do map de informações
  public HashMap<UserInfo, Object> getInfosUser() {

    return this.infosUser;
  }

  // Função para adicionar um telefone à lista de telefones
  public UserBean addTelefone(TelefoneBean bean) {

    this.telefones.add(bean);
    return this;
  }

  // Função para adicionar vários telefones à lista de telefones
  public UserBean addTelefones(List<TelefoneBean> beans) {

    for (TelefoneBean bean : beans)
      this.telefones.add(bean);
    return this;
  }

  // Função para adicionar um endereço à lista de endereços
  public UserBean addEndereco(EnderecoBean bean) {

    this.enderecos.add(bean);
    return this;
  }

  // Função para adicionar vários endereços à lista de endereços
  public UserBean addEnderecos(List<EnderecoBean> beans) {

    for (EnderecoBean bean : beans)
      this.enderecos.add(bean);
    return this;
  }

  // Função para obter a lista de endereços
  public ArrayList<EnderecoBean> getEnderecos() {

    return this.enderecos;
  }

  // Função para obter a lista de telefones
  public ArrayList<TelefoneBean> getTelefones() {

    return this.telefones;
  }
}
