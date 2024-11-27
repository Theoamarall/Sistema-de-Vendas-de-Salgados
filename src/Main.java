import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import controller.VendasSalgados;
import view.VendasSalgadosUI;
import dao.DBConnection;  // Certifique-se de importar as classes corretamente


public class Main {
    private VendasSalgadosUI vendasSalgadosUI;
    private VendasSalgados vendasSalgados;

    public Main() {
        try {
            DBConnection dbConnection = new DBConnection();
            dbConnection.conectar();
            dbConnection.criarTabelaSeNaoExistir();
            vendasSalgados = new VendasSalgados(dbConnection.getConnection());

            vendasSalgadosUI = new VendasSalgadosUI(vendasSalgados);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}