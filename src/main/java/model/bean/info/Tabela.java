package model.bean.info;

public enum Tabela {


  User("user"), Medico("medico"), Paciente("paciente"), Endereco("endereco"), Telefone(
      "telefone"), Especialidade("especialidade"), Medico_Has_Especialidade("medico_has_especialidade");

  // Tabela que o Enum representa no banco de dados
  private String nome;

  Tabela(String nome) {

    this.nome = nome;
  }

  public String getNome() {

    return this.nome;
  }
}
