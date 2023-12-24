import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDados {

    private static final String URL = "jdbc:mysql://localhost:3306/leilao?useSSL=false";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    private static Connection conexao;

    
    public static Connection obterConexao() {
        try {
            
            if (conexao == null || conexao.isClosed()) {
                // Carregar o driver JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");

                
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
                System.out.println("Conexão bem-sucedida!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conexao;
    }

    // Método estático para fechar a conexão
    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexão fechada!");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
