package teste;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import model.bean.EnderecoBean;
import model.bean.MedicoBean;
import model.bean.TelefoneBean;
import model.bean.info.EnderecoInfo;
import model.bean.info.MedicoInfo;
import model.bean.info.TelefoneInfo;
import model.bean.info.UserInfo;
import model.dao.MedicoDao;

public class Teste_Medico {


  public static void main(String[] args) {

  }

  private static void selecionar(int id) {

    try {

      new MedicoDao().selecionar(id);

    } catch (SQLException e) {

      System.out.println("o mano deu erro");
      e.printStackTrace();
    }
  }

  private static void deletar(int id) {

    try {

      new MedicoDao().deletar(id);
    } catch (SQLException e) {

      System.out.println("o mano deu erro");
      e.printStackTrace();
    }
  }

  private static void alterar(int id) {

    MedicoBean mb = new MedicoBean();
    mb.setInfo(UserInfo.ID, id);
    mb.setInfo(MedicoInfo.IDUser, id);
    mb.setInfo(UserInfo.Nome, "Gelson");

    try {

      new MedicoDao().alterar(mb);
    } catch (SQLException e) {

      System.out.println("o mano deu erro");
      e.printStackTrace();
    }
  }

  private static void cadastrar() {

    MedicoBean mb = new MedicoBean();

    mb.setInfo(UserInfo.Nome, "Gilson");
    mb.setInfo(UserInfo.Sobrenome, "Gerson");
    mb.setInfo(UserInfo.CPF, "12345678912");
    mb.setInfo(UserInfo.DataNascimento, "12/12/1222");
    mb.setInfo(UserInfo.Email, "gilsongerson@gmail.com");
    mb.setInfo(UserInfo.Senha, "meunomeégilsongerson");

    mb.setInfo(MedicoInfo.UF, "SC");
    mb.setInfo(MedicoInfo.Pais, "BR");
    mb.setInfo(MedicoInfo.CRM, "1231231");

    TelefoneBean celular = new TelefoneBean();

    celular.setInfo(TelefoneInfo.Tipo, celular.CELULAR);
    celular.setInfo(TelefoneInfo.Numero, "6666-6666");

    TelefoneBean telefone = new TelefoneBean();

    telefone.setInfo(TelefoneInfo.Tipo, telefone.TELEFONE);
    telefone.setInfo(TelefoneInfo.Numero, "40028922");

    mb.addTelefone(celular);
    mb.addTelefone(telefone);

    EnderecoBean residencial = new EnderecoBean();

    residencial.setInfo(EnderecoInfo.Tipo, residencial.RESIDENCIAL);
    residencial.setInfo(EnderecoInfo.Cep, "89066-040");
    residencial.setInfo(EnderecoInfo.Estado, "SC");
    residencial.setInfo(EnderecoInfo.Cidade, "Blumenau");
    residencial.setInfo(EnderecoInfo.Bairro, "Itoupava Central");
    residencial.setInfo(EnderecoInfo.Rua, "Erwin Henschel");
    residencial.setInfo(EnderecoInfo.Complemento, "casa");
    residencial.setInfo(EnderecoInfo.Numero, 1);

    EnderecoBean comercial = new EnderecoBean();

    HashMap<EnderecoInfo, Object> mapaEndereco = residencial.getInfosEnd();

    for (Entry<EnderecoInfo, Object> entrada : mapaEndereco.entrySet()) {

      comercial.setInfo(entrada.getKey(), entrada.getValue());
    }

    comercial.setInfo(EnderecoInfo.Tipo, comercial.COMERCIAL);
    comercial.setInfo(EnderecoInfo.Numero, 2);

    mb.addEndereco(residencial);
    mb.addEndereco(comercial);

    try {

      new MedicoDao().cadastrar(mb);
    } catch (SQLException e) {

      System.out.println("o mano deu erro");
      e.printStackTrace();
    }
  }
}
