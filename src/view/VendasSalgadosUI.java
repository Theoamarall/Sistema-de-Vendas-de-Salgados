package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import controller.VendasSalgados;

public class VendasSalgadosUI {
    private VendasSalgados vendasSalgados;
    private JTextArea textAreaRelatorio;

    public VendasSalgadosUI(VendasSalgados vendasSalgados) {
        this.vendasSalgados = vendasSalgados;

        JFrame frame = new JFrame("Controle de Vendas de Salgados");
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panelInputs = new JPanel();
        panelInputs.setLayout(new GridLayout(4, 2, 10, 10));
        panelInputs.setBackground(new Color(243, 243, 243));

        JLabel labelTipo = new JLabel("Tipo de Salgado:");
        String[] salgados = {
            "Coxinha", "Kibe", "Esfiha", "Pastel", "Empada",
            "Enroladinho de salsicha", "Pão de queijo", "Joelho", "Folhado"
        };
        JComboBox<String> comboBoxTipo = new JComboBox<>(salgados);

        JLabel labelPeso = new JLabel("Peso (g):");
        JTextField textPeso = new JTextField(10);

        JLabel labelPreco = new JLabel("Preço (R$):");
        JTextField textPreco = new JTextField(10);

        JButton btnRegistrar = new JButton("Registrar Venda");
        btnRegistrar.setBackground(new Color(58, 123, 255));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 12));

        JButton btnRelatorio = new JButton("Gerar Relatório");
        btnRelatorio.setBackground(new Color(58, 255, 123));
        btnRelatorio.setForeground(Color.WHITE);
        btnRelatorio.setFont(new Font("Arial", Font.BOLD, 12));

        textAreaRelatorio = new JTextArea(10, 40);
        textAreaRelatorio.setEditable(false);
        textAreaRelatorio.setFont(new Font("Arial", Font.PLAIN, 12));
        textAreaRelatorio.setLineWrap(true);
        textAreaRelatorio.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textAreaRelatorio);

        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tipo = (String) comboBoxTipo.getSelectedItem();
                double peso = Double.parseDouble(textPeso.getText());
                double preco = Double.parseDouble(textPreco.getText());
                try {
                    vendasSalgados.registrarVenda(tipo, peso, preco);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                textPeso.setText("");
                textPreco.setText("");
            }
        });

        btnRelatorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String relatorio = vendasSalgados.gerarRelatorio();
                    textAreaRelatorio.setText(relatorio);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        panelInputs.add(labelTipo);
        panelInputs.add(comboBoxTipo);
        panelInputs.add(labelPeso);
        panelInputs.add(textPeso);
        panelInputs.add(labelPreco);
        panelInputs.add(textPreco);
        panelInputs.add(btnRegistrar);

        frame.add(panelInputs, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(btnRelatorio, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}