package cadastrodb.model;

import cadastro.model.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {
    private ConectorDB conectorBD;
    private SequenceManager sequenceManager;

    public PessoaFisicaDAO() {
        conectorBD = new ConectorDB();
        sequenceManager = new SequenceManager();
    }

    // Método para obter uma pessoa física pelo ID
    public PessoaFisica getPessoa(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PessoaFisica pessoa = null;

        try {
            connection = conectorBD.getConnection();

            String sql = "SELECT * FROM Pessoa INNER JOIN PessoaFisica ON Pessoa.idPessoa = PessoaFisica.ID WHERE Pessoa.idPessoa = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                pessoa = new PessoaFisica(
                        resultSet.getInt("idPessoa"),
                        resultSet.getString("nome"),
                        resultSet.getString("logradouro"),
                        resultSet.getString("cidade"),
                        resultSet.getString("estado"),
                        resultSet.getString("telefone"),
                        resultSet.getString("email"),
                        resultSet.getString("CPF")
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

    // Método para retornar todas as pessoas físicas do banco de dados
    public List<PessoaFisica> getPessoas() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PessoaFisica> pessoas = new ArrayList<>();

        try {
            connection = conectorBD.getConnection();
            
            String sql = "SELECT * FROM Pessoa INNER JOIN PessoaFisica ON Pessoa.idPessoa = PessoaFisica.id";
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PessoaFisica pessoa = new PessoaFisica(
                        resultSet.getInt("idPessoa"),
                        resultSet.getString("nome"),
                        resultSet.getString("logradouro"),
                        resultSet.getString("cidade"),
                        resultSet.getString("estado"),
                        resultSet.getString("telefone"),
                        resultSet.getString("email"),
                        resultSet.getString("CPF")
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

    // Método para incluir uma pessoa física no banco de dados
    public boolean incluir(PessoaFisica pessoa) {
        Connection connection = null;
        PreparedStatement preparedStatementPessoa = null;
        PreparedStatement preparedStatementPessoaFisica = null;
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

            // Insere os dados na tabela PessoaFisica
            String sqlPessoaFisica = "INSERT INTO PessoaFisica (ID, CPF) VALUES (?, ?)";
            preparedStatementPessoaFisica = connection.prepareStatement(sqlPessoaFisica);
            preparedStatementPessoaFisica.setInt(1, pessoa.getId());
            preparedStatementPessoaFisica.setString(2, pessoa.getCpf());
            preparedStatementPessoaFisica.executeUpdate();

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
            conectorBD.close(preparedStatementPessoaFisica);
            conectorBD.close(preparedStatementPessoa);
            conectorBD.close(connection);
        }

        return sucesso;
    }

    // Método para alterar os dados de uma pessoa física no banco de dados
    public boolean alterar(PessoaFisica pessoa) {
        Connection connection = null;
        PreparedStatement preparedStatementPessoa = null;
        PreparedStatement preparedStatementPessoaFisica = null;
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

            // Atualiza os dados na tabela PessoaFisica
            String sqlPessoaFisica = "UPDATE PessoaFisica SET cpf = ? WHERE id = ?";
            preparedStatementPessoaFisica = connection.prepareStatement(sqlPessoaFisica);
            preparedStatementPessoaFisica.setString(1, pessoa.getCpf());
            preparedStatementPessoaFisica.setInt(2, pessoa.getId());
            preparedStatementPessoaFisica.executeUpdate();

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
            conectorBD.close(preparedStatementPessoaFisica);
            conectorBD.close(preparedStatementPessoa);
            conectorBD.close(connection);
        }

        return sucesso;
    }

    // Método para excluir uma pessoa física do banco de dados
    public boolean excluir(int id) {
        Connection connection = null;
        PreparedStatement preparedStatementPessoa = null;
        PreparedStatement preparedStatementPessoaFisica = null;
        boolean sucesso = false;

        try {
            // Obtém uma conexão com o banco de dados
            connection = conectorBD.getConnection();
            connection.setAutoCommit(false); // Desativa o modo de autocommit

            // Exclui registro da tabela PessoaFisica
            String sqlPessoaFisica = "DELETE FROM PessoaFisica WHERE ID = ?";
            preparedStatementPessoaFisica = connection.prepareStatement(sqlPessoaFisica);
            preparedStatementPessoaFisica.setInt(1, id);
            preparedStatementPessoaFisica.executeUpdate();

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
            conectorBD.close(preparedStatementPessoaFisica);
            conectorBD.close(preparedStatementPessoa);
            conectorBD.close(connection);
        }

        return sucesso;
    }
}
