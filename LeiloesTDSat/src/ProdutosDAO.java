
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    public void cadastrarProduto(ProdutosDTO produto) {
        String query = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        Connection conexao = ConexaoBancoDados.obterConexao(); // Obter conexão

        try (PreparedStatement prepStatement = conexao.prepareStatement(query)) {
            prepStatement.setString(1, produto.getNome());
            prepStatement.setInt(2, produto.getValor());
            prepStatement.setString(3, produto.getStatus());

            prepStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listaProdutos = new ArrayList<>();
        String query = "SELECT * FROM produtos";
        Connection conexao = ConexaoBancoDados.obterConexao();

        try (PreparedStatement prepStatement = conexao.prepareStatement(query); var resultSet = prepStatement.executeQuery()) {

            while (resultSet.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultSet.getInt("id"));
                produto.setNome(resultSet.getString("nome"));
                produto.setValor(resultSet.getInt("valor"));
                produto.setStatus(resultSet.getString("status"));
                listaProdutos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        } finally {
            ConexaoBancoDados.fecharConexao(conexao);
        }

        return listaProdutos;
    }

    public boolean venderProduto(int idProduto) {
        String query = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
        Connection conexao = ConexaoBancoDados.obterConexao();

        try (PreparedStatement prepStatement = conexao.prepareStatement(query)) {
            prepStatement.setInt(1, idProduto);
            int linhasAfetadas = prepStatement.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado ou não pôde ser vendido.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
            return false;
        } finally {
            ConexaoBancoDados.fecharConexao(conexao);
        }
    }

}
