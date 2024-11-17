import java.sql.*;
import java.text.DecimalFormat;

public class VendasSalgados {
    private Connection conn;
    private DecimalFormat df = new DecimalFormat("0.00");

    // Construtor que recebe a conexão do banco de dados
    public VendasSalgados(Connection conn) {
        this.conn = conn;
    }

    // Método para registrar a venda no banco de dados
    public void registrarVenda(String tipo, double peso, double preco) {
        String sql = "INSERT INTO vendas (tipo_salgado, peso_em_gramas, preco) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tipo);
            pstmt.setDouble(2, peso);
            pstmt.setDouble(3, preco);
            pstmt.executeUpdate();
            System.out.println("Venda registrada com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao registrar a venda: " + e.getMessage());
        }
    }

    // Método para gerar o relatório das vendas
    public String gerarRelatorio() {
        // Array com todos os tipos de salgados
        String[] salgados = {
            "Coxinha", "Kibe", "Esfiha", "Pastel", "Empada",
            "Enroladinho de salsicha", "Pão de queijo", "Joelho", "Folhado"
        };

        String sql = "SELECT tipo_salgado, peso_em_gramas, preco FROM vendas";
        StringBuilder relatorio = new StringBuilder();
        double totalGeral = 0;
        // Mapeamento de totais por tipo
        double[] totais = new double[salgados.length];
        double[] pesos = new double[salgados.length];
        int[] vendidos = new int[salgados.length];

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            relatorio.append("Relatório de Vendas:\n");
            relatorio.append("Tipo de Salgado | Peso (g) | Preço (R$)\n");
            relatorio.append("----------------------------------------\n");

            while (rs.next()) {
                String tipo = rs.getString("tipo_salgado");
                double peso = rs.getDouble("peso_em_gramas");
                double preco = rs.getDouble("preco");

                // Adiciona no relatório a venda do salgado
                relatorio.append(tipo + " | " + peso + " | " + df.format(preco) + "\n");

                totalGeral += preco;

                // Encontrar o índice do tipo de salgado no array
                for (int i = 0; i < salgados.length; i++) {
                    if (salgados[i].equals(tipo)) {
                        totais[i] += preco;
                        pesos[i] += peso;
                        vendidos[i]++;
                    }
                }
            }

            // Relatório de totais por tipo (Incluindo todos os tipos, mesmo que não vendidos)
            relatorio.append("\nTotal de Vendas:\n");
            for (int i = 0; i < salgados.length; i++) {
                relatorio.append(salgados[i] + ": " + vendidos[i] + " vendidos | R$ " + df.format(totais[i]) + " | Peso: " + df.format(pesos[i]) + "g\n");
                
                // Caso algum tipo não tenha sido vendido, garantir que ele seja mostrado no relatório
                if (vendidos[i] == 0) {
                    relatorio.append(salgados[i] + ": Nenhuma venda registrada.\n");
                }
            }

            // Total geral e peso total
            relatorio.append("\nTotal Geral: R$ " + df.format(totalGeral) + "\n");
            double pesoTotal = 0;
            for (int i = 0; i < salgados.length; i++) {
                pesoTotal += pesos[i];
            }
            relatorio.append("\nPeso Total de Todos os Salgados: " + df.format(pesoTotal / 1000) + " kg");

        } catch (SQLException e) {
            System.out.println("Erro ao gerar o relatório: " + e.getMessage());
        }
        return relatorio.toString();
    }
}