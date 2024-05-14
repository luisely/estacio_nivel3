package cadastro.model.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceManager {
     private ConectorDB conectorDB;

    public SequenceManager() {
        conectorDB = new ConectorDB();
    }

    public int getValue(String sequenceName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int nextValue = 0;

         try {

            connection = conectorDB.getConnection();

            String sql = "SELECT NEXT VALUE FOR " + sequenceName + " AS next_value";

            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nextValue = resultSet.getInt("next_value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fecha os recursos
            conectorDB.close(resultSet);
            conectorDB.close(preparedStatement);
            conectorDB.close(connection);
        }

        return nextValue;
    }
}
