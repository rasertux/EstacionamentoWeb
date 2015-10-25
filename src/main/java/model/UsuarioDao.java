package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import factory.Conexao;

public class UsuarioDao {

	private static Connection conexao;

	private static final String SQL_INSERT = "insert into usuario(nome, login, senha, email) values (?,?,?,?)";
	private static final String SQL_DELETE = "delete from usuario where login=?";
	private static final String SQL_SELECT_ALL = "select * from usuario";
	private static final String SQL_UPDATE = "update usuario set nome=?, senha=?, email=? where login=?";
	private static final String SQL_USER = "select * from usuario where login=? and senha=?";

	public static boolean inserirUsuario(UsuarioBean usuario) {
		conexao = Conexao.conectar();
		try {
			PreparedStatement stmt = conexao.prepareStatement(SQL_INSERT);
			String senhacript = criptografaSenha(usuario.getSenha());
			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getLogin());
			stmt.setString(3, senhacript);
			stmt.setString(4, usuario.getEmail());
			stmt.execute();
			stmt.close();
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		} finally {
			Conexao.fecharConexao(conexao);
		}
	}

	public static boolean remover(String login) {
		conexao = Conexao.conectar();
		try {
			PreparedStatement stmt = conexao.prepareStatement(SQL_DELETE);
			stmt.setString(1, login);
			stmt.execute();
			stmt.close();
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		} finally {
			Conexao.fecharConexao(conexao);
		}
	}

	public static ArrayList<UsuarioBean> getItens() {
		conexao = Conexao.conectar();
		ArrayList<UsuarioBean> todos = new ArrayList<UsuarioBean>();
		try {
			PreparedStatement stmt = conexao.prepareStatement(SQL_SELECT_ALL);
			UsuarioBean usuario = null;
			ResultSet rs = null;
			rs = stmt.executeQuery();
			while (rs.next()) {
				usuario = new UsuarioBean();
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setEmail(rs.getString("email"));
				todos.add(usuario);
			}
			return todos;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			Conexao.fecharConexao(conexao);
		}
	}

	public static boolean alterarUsuario(UsuarioBean usuario) {
		conexao = Conexao.conectar();
		try {
			PreparedStatement stmt = conexao.prepareStatement(SQL_UPDATE);
			String senhacript = criptografaSenha(usuario.getSenha());
			stmt.setString(1, usuario.getNome());
			stmt.setString(2, senhacript);
			stmt.setString(3, usuario.getEmail());
			stmt.setString(4, usuario.getLogin());
			stmt.execute();
			stmt.close();
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		} finally {
			Conexao.fecharConexao(conexao);
		}
	}

	public static UsuarioBean getUsuarioLogin(String login, String senha) {
		conexao = Conexao.conectar();
		try {
			PreparedStatement stmt = conexao.prepareStatement(SQL_USER);
			String senhacript = criptografaSenha(senha);
			stmt.setString(1, login);
			stmt.setString(2, senhacript);
			UsuarioBean usuario = null;
			ResultSet rs = null;
			rs = stmt.executeQuery();
			while (rs.next()) {
				usuario = new UsuarioBean();
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setEmail(rs.getString("email"));
			}
			return usuario;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			Conexao.fecharConexao(conexao);
		}
	}

	public static String criptografaSenha(String senha) {
		String senhacript = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(senha.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			senhacript = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return senhacript;
	}

}
