package model.dao.statement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import model.bean.info.Info;
import model.bean.info.Tabela;
import model.conexao.ConnectionFactory;

public class StatementBuilder {


  // Tipos de Statement que podem ser criados
  public final int INSERT = 1, SELECT = 2, UPDATE = 3, DELETE = 4;

  // Todos os atributos que são utilizados para gerar um statement
  private String tabela;
  private int tipo;
  private HashMap<? extends Info, Object> mapa = new HashMap<>();
  private ArrayList<Info> infos = new ArrayList<>();
  private ArrayList<Info> conditions = new ArrayList<>();
  private ArrayList<Object> conditionsValues = new ArrayList<>();
  private ArrayList<String> conditionsConditional = new ArrayList<>();

  // O Statement de fato
  private PreparedStatement ps;

  // Variável utilizada para saber em que ? a configuração está
  private int fimValues;


  // Função utilizada para retornar o PreparedStatement já configura e pronto para uso
  public PreparedStatement build() {

    setInfos(infos.toArray(new Info[] {})).instantiateStatement().prepareStatement();

    System.out.println("Statement pronto!");

    return ps;
  }


  // Função utilizada para instanciar o Statement
  private StatementBuilder instantiateStatement() {

    System.out.println("Instanciando Statement...");

    try {

      // Cria um statement capaz de retornar as primary keys geradas
      ps = ConnectionFactory.getConnection().prepareStatement(estruturarComando(),
          Statement.RETURN_GENERATED_KEYS);

      System.out.println("Statement instanciado!");
    } catch (SQLException e) {

      System.out.println("Erro ao instanciar Statement \n" + estruturarComando());
      e.printStackTrace();
    }

    return this;
  }

  // Função utilizada preparar o statement para ser usado
  private StatementBuilder prepareStatement() {

    System.out.println("Preparando Statement...");

    switch (tipo) {

      // Caso o statement seja do tipo de insert
      case INSERT: {

        // Define as informações que serão inseridas
        setInfosValues();
        break;
      }

      // Caso o statement seja do tipo de select
      case SELECT: {

        // Define as condições para obter as informações
        setConditionsValues();
        break;
      }

      // Caso o statement seja do tipo de update
      case UPDATE: {

        // Define as informações que serão atualizadas
        setInfosValues();

        // Define as condições para atualizar as informações
        setConditionsValues();
        break;
      }

      // Caso o statement seja do tipo de delete
      case DELETE: {

        // Define as condições para deletar
        setConditionsValues();
        break;
      }
    }

    System.out.println("Statement preparado!");

    return this;
  }

  // Função utilizada para criar o comando SQL
  private String estruturarComando() {

    String comando = "";

    // Lol bro acho que aqui não precisa comentar
    switch (tipo) {

      case INSERT: {

        comando = "insert into " + tabela + "(" + getCampos() + ") values (" + getValues() + ")";
        break;
      }

      case SELECT: {

        comando = "select " + getCampos() + " from " + tabela + getConditions();
        break;
      }

      case UPDATE: {

        comando = "update " + tabela + " set " + getCamposAndValues() + getConditions();
        break;
      }

      case DELETE: {

        comando = "delete from " + tabela + getConditions();
        break;
      }
    }

    System.out.println("Comando gerado: " + comando);

    return comando;
  }


  // Função para obter os campos que serão inseridos no comando de Insert
  private String getCampos() {

    String campos = "";

    int i = 0;

    switch (tipo) {

      case INSERT: {

        // Para cada entrada no mapa de inserções
        for (Entry<? extends Info, Object> entrada : mapa.entrySet()) {

          // Adiciona o nome da coluna que será inserida
          campos += ((Info) entrada.getKey()).getCampo();

          // Verifica se deve ser colocada uma vírgula
          campos += i < mapa.entrySet().size() - 1 ? ", " : "";

          i++;
        }
        break;
      }

      case SELECT: {

        // Para cada informação na lista de informações sendo solicitadas
        for (Info info : infos) {

          // Adiciona o nome da coluna solicitada
          campos += info.getCampo();

          // Verifica se deve ser colocada uma vírgula
          campos += i < infos.size() - 1 ? ", " : "";

          i++;
        }
        break;
      }
    }

    return campos;
  }

  // Função para obter os campos junto com as interrogações
  private String getCamposAndValues() {

    String camposAndValues = "";

    int i = 0;

    // Para cada entrada no mapa de atualizações
    for (Entry<? extends Info, Object> entrada : mapa.entrySet()) {

      // Adiciona o nome da coluna junto com o " = ?" à String
      camposAndValues += ((Info) entrada.getKey()).getCampo() + " = ";
      camposAndValues += "?";

      // Verifica se deve ser colocada uma vírgula
      camposAndValues += i < mapa.entrySet().size() - 1 ? ", " : "";

      i++;
    }

    return camposAndValues;
  }

  // Função para obter as interrogações do comando de insert
  private String getValues() {

    String values = "";

    switch (tipo) {

      case INSERT: {

        int length = mapa.entrySet().size();

        // Vai de 0 até o tamanho do mapa
        for (int i = 0; i < length; i++) {

          // Adiciona a interrogação e verifica se deve ser colocada uma vírgula
          values += "?";
          values += i < length - 1 ? ", " : "";
        }

        break;
      }
    }

    return values;
  }

  // Função para obter as condições dos updates, deletes e selects
  private String getConditions() {

    String conditions = this.conditions.size() > 0 ? " where " : "";

    // Vai de 0 até o tamanho da lista de condições
    for (int i = 0; i < this.conditions.size(); i++) {

      // Adiciona o nome do coluna junto com o " = ?" e também o " and " caso necessário
      conditions += this.conditions.get(i).getCampo() + " = ?"
          + (i + 1 < this.conditions.size() ? " " + this.conditionsConditional.get(i) + " " : "");
    }

    return conditions;
  }

  // Função para definir os valores das condições do comando
  private void setConditionsValues() {

    // Vai de 0 até o tamanho de lista de valores de conditions
    for (int i = 0; i < this.conditionsValues.size(); i++) {

      // Debug
      System.out.println("Definindo condição " + this.conditions.get(i).getCampo() + " como "
          + this.conditionsValues.get(i) + "...");

      try {

        // Define o valor da condição no PreparedStatement
        ps.setObject(i + 1 + fimValues, this.conditionsValues.get(i));
      } catch (SQLException e) {

        // Debug
        System.out.println("Não foi possível definir a condição em: "
            + this.conditions.get(i).getCampo() + " = " + this.conditionsValues.get(i));
        e.printStackTrace();
      }
    }

    System.out.println("Condições definidas com sucesso!");
  }

  // Função para definir os valores dos campos que serão inseridos ou atualizados
  private void setInfosValues() {

    int i = 1;

    // Para cada entrada no mapa de inserções/atualizações
    for (Entry<? extends Info, Object> entrada : mapa.entrySet()) {

      // Debug
      System.out.println(
          "Definindo valor " + entrada.getKey().getCampo() + " como " + entrada.getValue() + "...");

      try {

        // Define o valor do campo no PreparedStatement
        ps.setObject(i, entrada.getValue());
      } catch (SQLException e) {

        // Debug
        System.out.println("Não foi possível definir o valor em: " + entrada.getKey().getCampo()
            + " = " + entrada.getValue());
        e.printStackTrace();
      }

      // Define a variável global para saber de que interrogação partir na próxima vez que algum
      // valor for ser definido
      fimValues = i;
      i++;
    }

    System.out.println("Valores definidos com sucesso!");
  }

  // Função para definir que tabela vai ser usada no PreparedStatement
  // Utilizada em todos os tipos de statement
  public StatementBuilder withTabela(Tabela tabela) {

    this.tabela = tabela.getNome();
    return this;
  }

  // Função para definir de que tipo o statement é
  // Utilizada em todos os tipos de statement
  public StatementBuilder withTipo(int tipo) {

    this.tipo = tipo;
    return this;
  }

  // Função para adicionar uma condição para o comando
  // Usado em updates, selects e deletes
  public StatementBuilder addCondition(Info condition, String conditional) {

    this.conditions.add(condition);
    this.conditionsConditional.add(conditional);
    return this;
  }

  // Função para adicionar um valor para uma condição de um comando
  // Utilizado em updates, selects e deletes
  public StatementBuilder addConditionValue(Object value) {

    this.conditionsValues.add(value);
    return this;
  }

  // Função para adicionar um mapa com valores usados no statement
  // Utilizado em inserts e updates
  public StatementBuilder withMap(HashMap<? extends Info, Object> mapa) {

    this.mapa = mapa;

    return this;
  }

  // Função para adicionar os campos usados no statement
  // Utilizado em selects
  public StatementBuilder withInfos(Info... infos) {

    for (Info info : infos)
      this.infos.add(info);

    return this;
  }

  // Função para definir os campos usados no statement ignorando os que também são usados
  // em condições, filtra os campos enviados pelo withInfos
  private StatementBuilder setInfos(Info... infos) {

    this.infos.clear();

    for (Info info : infos) {

      if (!this.conditions.contains(info))
        this.infos.add(info);
    }

    return this;
  }
}
