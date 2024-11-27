package controller;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import model.Salgado;

public class VendasSalgados {
    private Connection connection;
    private Map<String, Salgado> vendas;

    // Construtor que inicializa a conexão e o mapa de vendas
    public VendasSalgados(Connection connection) {
        this.connection = connection;
        this.vendas = new HashMap<>();
    }

    // Método para registrar uma venda
    public void registrarVenda(String tipo, double peso, double preco) throws SQLException {
        // Se o tipo de salgado ainda não foi registrado, cria um novo salgado
        if (!vendas.containsKey(tipo)) {
            vendas.put(tipo, new Salgado(tipo, 0, 0));
        }

        // Recupera o salgado do mapa e atualiza o peso e o preço
        Salgado salgado = vendas.get(tipo);
        salgado.setPeso(salgado.getPeso() + peso);
        salgado.setPreco(salgado.getPreco() + preco);

        // Insere os dados da venda no banco de dados
        String sql = "INSERT INTO vendas (tipo, peso, preco) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            stmt.setDouble(2, peso);
            stmt.setDouble(3, preco);
            stmt.executeUpdate();
        }
    }

    // Método para gerar o relatório de vendas
    public String gerarRelatorio() throws SQLException {
        StringBuilder relatorio = new StringBuilder();
        double totalGeral = 0;
        double totalPeso = 0;

        // Para cada tipo de salgado registrado, calcula o total de peso e preço
        for (Map.Entry<String, Salgado> entry : vendas.entrySet()) {
            Salgado salgado = entry.getValue();
            double totalPesoSalgado = salgado.getPeso() / 1000; // Convertendo peso para kg
            double totalPrecoSalgado = salgado.getPreco();

            // Adiciona ao relatório os totais de cada salgado
            relatorio.append(String.format("%s: %.2f kg, R$ %.2f\n", salgado.getTipo(), totalPesoSalgado, totalPrecoSalgado));

            // Atualiza o total geral
            totalPeso += totalPesoSalgado;
            totalGeral += totalPrecoSalgado;
        }

        // Adiciona o total geral de vendas no final do relatório
        relatorio.append(String.format("\nTotal Geral: %.2f kg, R$ %.2f", totalPeso, totalGeral));

        return relatorio.toString();
    }

    // Método para fechar a conexão com o banco de dados
    public void desconectar() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}