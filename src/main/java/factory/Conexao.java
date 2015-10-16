package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	public static Connection conectar() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost/estacionamento", "root", "");
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public static void fecharConexao(Connection conexao) {
		try {
			if (conexao != null && !conexao.isClosed()) {
				conexao.close();
				conexao = null;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
