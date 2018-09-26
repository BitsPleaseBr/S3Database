package model.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Diogo Classe para criar uma conexão com o banco de dados. Não pode ser instanciada.
 */
public class ConnectionFactory {

  // Constantes para acessar o banco de dados
  private static String connectionURL = System.getenv("connectionURL");
  private static String user = System.getenv("user");
  private static String password = System.getenv("password");

  /**
   * Método para retornar a conexão com o banco de dados
   * 
   * @return Uma instância da conexão com o banco de dados se ela foi bem sucedida, caso contrário
   *         retorna null.
   */
  public static Connection getConnection() {

    // Variável para retornar a conexão
    Connection conexao = null;

    // Realizar a conexão
    try {
      conexao = DriverManager.getConnection(connectionURL, user, password);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return conexao;
  }

  private ConnectionFactory() {

  }
}
