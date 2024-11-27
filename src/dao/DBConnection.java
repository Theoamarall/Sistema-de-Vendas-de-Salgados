package dao;

import java.sql.*;

public class DBConnection {
    private Connection connection;

    public DBConnection() {
        this.connection = null;
    }

    public void conectar() throws SQLException {
        String url = "jdbc:sqlite:salgados.db";
        this.connection = DriverManager.getConnection(url);
    }

    public void criarTabelaSeNaoExistir() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS vendas (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "tipo TEXT NOT NULL, " +
                     "peso DOUBLE NOT NULL, " +
                     "preco DOUBLE NOT NULL)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void desconectar() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}