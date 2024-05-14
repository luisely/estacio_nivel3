
package cadastrodb.model;

import cadastro.model.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaDAO {
    private ConectorDB conectorBD;
    private SequenceManager sequenceManager;

    public PessoaJuridicaDAO() {
        conectorBD = new ConectorDB();
        sequenceManager = new SequenceManager();
    }

    public PessoaJuridica getPessoa(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PessoaJuridica pessoa = null;

        try {

            connection = conectorBD.getConnection();

            String sql = "SELECT * FROM Pessoa INNER JOIN PessoaJuridica ON Pessoa.id = PessoaJuridica.id WHERE Pessoa.id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                pessoa = new PessoaJuridica(
                    resultSet.getInt("idPessoa"),
                    resultSet.getString("nome"),
                    resultSet.getString("logradouro"),
                    resultSet.getString("cidade"),
                    resultSet.getString("estado"),
                    resultSet.getString("telefone"),
                    resultSet.getString("email"),
                    resultSet.getString("cnpj")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fecha os recursos
            conectorBD.close(resultSet);
            conectorBD.close(preparedStatement);
            conectorBD.close(connection);
        }

        return pessoa;
    }

    // Método para retornar todas as pessoas jurídicas do banco de dados
    public List<PessoaJuridica> getPessoas() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PessoaJuridica> pessoas = new ArrayList<>();

        try {
            // Obtém uma conexão com o banco de dados
            connection = conectorBD.getConnection();

            // Cria uma instrução SQL para buscar todas as pessoas jurídicas
            String sql = "SELECT * FROM Pessoa INNER JOIN PessoaJuridica ON Pessoa.idPessoa = PessoaJuridica.id";
            preparedStatement = connection.prepareStatement(sql);

            // Executa a consulta SQL
            resultSet = preparedStatement.executeQuery();

            // Cria e adiciona cada pessoa jurídica à lista
            while (resultSet.next()) {
                PessoaJuridica pessoa = new PessoaJuridica(
                        resultSet.getInt("idPessoa"),
                        resultSet.getString("nome"),
                        resultSet.getString("logradouro"),
                        resultSet.getString("cidade"),
                        resultSet.getString("estado"),
                        resultSet.getString("telefone"),
                        resultSet.getString("email"),
                        resultSet.getString("cnpj")
                );
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fecha os recursos
            conectorBD.close(resultSet);
            conectorBD.close(preparedStatement);
            conectorBD.close(connection);
        }

        return pessoas;
    }

    // Método para incluir uma pessoa jurídica no banco de dados
    public boolean incluir(PessoaJuridica pessoa) {
        Connection connection = null;
        PreparedStatement preparedStatementPessoa = null;
        PreparedStatement preparedStatementPessoaJuridica = null;
        boolean sucesso = false;

        try {
            // Obtém uma conexão com o banco de dados
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); // Desativa o modo de autocommit

            // Insere os dados na tabela Pessoa
            String sqlPessoa = "INSERT INTO Pessoa (idPessoa, nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatementPessoa = connection.prepareStatement(sqlPessoa);
            preparedStatementPessoa.setInt(1, pessoa.getId());
            preparedStatementPessoa.setString(2, pessoa.getNome());
            preparedStatementPessoa.setString(3, pessoa.getLogradouro());
            preparedStatementPessoa.setString(4, pessoa.getCidade());
            preparedStatementPessoa.setString(5, pessoa.getEstado());
            preparedStatementPessoa.setString(6, pessoa.getTelefone());
            preparedStatementPessoa.setString(7, pessoa.getEmail());
            preparedStatementPessoa.executeUpdate();

            // Insere os dados na tabela PessoaJuridica
            String sqlPessoaJuridica = "INSERT INTO PessoaJuridica (ID, CNPJ) VALUES (?, ?)";
            preparedStatementPessoaJuridica = connection.prepareStatement(sqlPessoaJuridica);
            preparedStatementPessoaJuridica.setInt(1, pessoa.getId());
            preparedStatementPessoaJuridica.setString(2, pessoa.getCnpj());
            preparedStatementPessoaJuridica.executeUpdate();

            // Confirma as alterações no banco de dados
            connection.commit();
            sucesso = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // Em caso de erro, faz rollback das alterações
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Fecha os recursos
            conectorBD.close(preparedStatementPessoaJuridica);
            conectorBD.close(preparedStatementPessoa);
            conectorBD.close(connection);
        }

        return sucesso;
    }

    // Método para alterar os dados de uma pessoa jurídica no banco de dados
    public boolean alterar(PessoaJuridica pessoa) {
        Connection connection = null;
        PreparedStatement preparedStatementPessoa = null;
        PreparedStatement preparedStatementPessoaJuridica = null;
        boolean sucesso = false;

        try {
            // Obtém uma conexão com o banco de dados
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); // Desativa o modo de autocommit

            // Atualiza os dados na tabela Pessoa
            String sqlPessoa = "UPDATE Pessoa SET nome = ?, logradouro = ?, cidade = ?, estado = ?, telefone = ?, email = ? WHERE idPessoa = ?";
            preparedStatementPessoa = connection.prepareStatement(sqlPessoa);
            preparedStatementPessoa.setString(1, pessoa.getNome());
            preparedStatementPessoa.setString(2, pessoa.getLogradouro());
            preparedStatementPessoa.setString(3, pessoa.getCidade());
            preparedStatementPessoa.setString(4, pessoa.getEstado());
            preparedStatementPessoa.setString(5, pessoa.getTelefone());
            preparedStatementPessoa.setString(6, pessoa.getEmail());
            preparedStatementPessoa.setInt(7, pessoa.getId());
            preparedStatementPessoa.executeUpdate();

            // Atualiza os dados na tabela PessoaJuridica
            String sqlPessoaJuridica = "UPDATE PessoaJuridica SET cnpj = ? WHERE id = ?";
            preparedStatementPessoaJuridica = connection.prepareStatement(sqlPessoaJuridica);
            preparedStatementPessoaJuridica.setString(1, pessoa.getCnpj());
            preparedStatementPessoaJuridica.setInt(2, pessoa.getId());
            preparedStatementPessoaJuridica.executeUpdate();

            // Confirma as alterações no banco de dados
            connection.commit();
            sucesso = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // Em caso de erro, faz rollback das alterações
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Fecha os recursos
            conectorBD.close(preparedStatementPessoaJuridica);
            conectorBD.close(preparedStatementPessoa);
            conectorBD.close(connection);
        }

        return sucesso;
    }

    // Método para excluir uma pessoa jurídica do banco de dados
    public boolean excluir(int id) {
        Connection connection = null;
        PreparedStatement preparedStatementPessoa = null;
        PreparedStatement preparedStatementPessoaJuridica = null;
        boolean sucesso = false;

        try {
            // Obtém uma conexão com o banco de dados
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); // Desativa o modo de autocommit

            // Exclui a pessoa da tabela PessoaJuridica
            String sqlPessoaJuridica = "DELETE FROM PessoaJuridica WHERE id = ?";
            preparedStatementPessoaJuridica = connection.prepareStatement(sqlPessoaJuridica);
            preparedStatementPessoaJuridica.setInt(1, id);
            preparedStatementPessoaJuridica.executeUpdate();

            // Exclui a pessoa da tabela Pessoa
            String sqlPessoa = "DELETE FROM Pessoa WHERE idPessoa = ?";
            preparedStatementPessoa = connection.prepareStatement(sqlPessoa);
            preparedStatementPessoa.setInt(1, id);
            preparedStatementPessoa.executeUpdate();

            // Confirma as alterações no banco de dados
            connection.commit();
            sucesso = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // Em caso de erro, faz rollback das alterações
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Fecha os recursos
            conectorBD.close(preparedStatementPessoaJuridica);
            conectorBD.close(preparedStatementPessoa);
            conectorBD.close(connection);
        }

        return sucesso;
    }
}
