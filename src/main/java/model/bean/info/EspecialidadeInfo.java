package model.bean.info;

import java.util.function.Function;

public enum EspecialidadeInfo implements Info {

  
  Descricao("descricao"), Id("id");
 
  
  // Campo que esse enum representa no banco
  private final String campo;
  // Função de parsing usada para validar o valor informado
  private Function<Object, Object> parser;

  // Construtor pra receber o campo que esse enum representa no banco
  // Caso não seja fornecido uma função de parsing, então a função padrão é usada
  EspecialidadeInfo(String campo) {

    this(campo, valor -> valor);
  }

  EspecialidadeInfo(String campo, Function<Object, Object> parser) {
    this.campo = campo;
    this.parser = parser;
  }

  public String getCampo() {

    return this.campo;
  }

  // Função usada para fazer o parsing correto do valor
  public Object parse(Object value) {
    return parser.apply(value);
  }
}