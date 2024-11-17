import java.sql.*;

public class DBConnection {
    private String url;
    private Connection conn;

    // Construtor da classe que recebe a URL do banco de dados
    public DBConnection(String url) {
        this.url = url;
    }

<<<<<<< HEAD
    // Construtor sem parâmetros, com URL padrão ou a partir de variável de ambiente
    public DBConnection() {
        // Tenta obter o caminho do banco de dados a partir de uma variável de ambiente
        String caminhoDB = System.getenv("CAMINHO_DB");
        if (caminhoDB == null) {
            // Caso a variável de ambiente não esteja definida, usa o caminho padrão
            caminhoDB = "vendas_salgados.db";  // Caminho relativo no mesmo diretório do projeto
        }
        this.url = caminhoDB;
=======
    // Construtor sem parâmetros, com URL padrão
    public DBConnection() {
        // URL padrão do banco de dados, que pode ser alterada conforme necessário
        this.url = "/sdcard/PROJETOS/JAVA/vendas_salgados.db"; // Defina a URL aqui diretamente
>>>>>>> 8fc4241 (Adicionando a pasta lib com as dependências)
    }

    // Método para conectar ao banco de dados
    public void conectar() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + url);
            System.out.println("Conectado ao banco de dados.");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    // Método para obter a conexão
    public Connection getConnection() {
        return conn;
    }

    // Método para criar a tabela, caso não exista
    public void criarTabelaSeNaoExistir() {
        String sql = "CREATE TABLE IF NOT EXISTS vendas (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "tipo_salgado TEXT NOT NULL," +
                     "peso_em_gramas REAL NOT NULL," +
                     "preco REAL NOT NULL)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'vendas' criada ou já existe.");
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }
    }
}