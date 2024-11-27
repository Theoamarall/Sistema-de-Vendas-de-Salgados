package model;

public class Salgado {
    private String tipo;
    private double peso;
    private double preco;
    private int quantidadeVendida;
    private double pesoTotal;

    public Salgado(String tipo, double peso, double preco) {
        this.tipo = tipo;
        this.peso = peso;
        this.preco = preco;
        this.quantidadeVendida = 0;
        this.pesoTotal = 0.0;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public double getPesoTotal() {
        return pesoTotal;
    }

    public void registrarVenda(double pesoVendidos) {
        this.quantidadeVendida++;
        this.pesoTotal += pesoVendidos;
    }

    public double calcularTotalValor() {
        return preco * quantidadeVendida;
    }

    public double calcularPesoTotalKg() {
        return pesoTotal / 1000;
    }

    @Override
    public String toString() {
        return "Salgado{" +
               "tipo='" + tipo + '\'' +
               ", peso=" + peso +
               ", preco=" + preco +
               ", quantidadeVendida=" + quantidadeVendida +
               ", pesoTotal=" + pesoTotal + "g" +
               '}';
    }
}