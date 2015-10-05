package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.Conexao;
import model.TarifaBean;

public class TarifaDao implements GenericDao {

	private static final String SQL_INSERT = "insert into tarifa  values (0,?,?)";
	private static final String SQL_DELETE = "delete from tarifa where idtarifa = ?";
	private static final String SQL_UPDATE = "update tarifa set descricao = ?, valor = ? where idtarifa = ?";
	private static final String SQL_SELECT_BY_ID = "select * from tarifa where idtarifa = ?";
	private static final String SQL_SELECT_ALL = "select * from tarifa";

	@Override
	public boolean inserir(Object objeto) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SQL_INSERT);

			TarifaBean tarifa = (TarifaBean) objeto;// convertendo o objeto em
													// TarifaBean
			stmt.setString(1, tarifa.getDescricao());
			stmt.setFloat(2, tarifa.getValor());
			stmt.executeUpdate();

			return true;
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return false;
		} finally {
			if (stmt != null)
				try {
					if (conn != null)
						conn.close();
					if (stmt != null)
						stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	@Override
	public boolean remover(Object objeto) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SQL_DELETE);
			Integer id = (Integer) objeto;

			stmt.setInt(1, id);
			stmt.executeUpdate();

			return true;
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return false;
		} finally {
			if (stmt != null)
				try {
					if (conn != null)
						conn.close();
					if (stmt != null)
						stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	@Override
	public boolean alterar(Object objeto) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SQL_UPDATE);

			TarifaBean tarifa = (TarifaBean) objeto;// convertendo o objeto em
													// TarifaBean
			stmt.setString(1, tarifa.getDescricao());
			stmt.setFloat(2, tarifa.getValor());
			stmt.setInt(3, tarifa.getIdtarifa());
			stmt.executeUpdate();

			return true;
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return false;
		} finally {
			if (stmt != null)
				try {
					if (conn != null)
						conn.close();
					if (stmt != null)
						stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

	}

	@Override
	public Object consultar(Object objeto) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
			Integer id = (Integer) objeto;
			stmt.setInt(1, id);

			TarifaBean tarifa = null;

			rs = stmt.executeQuery();
			while (rs.next()) {
				tarifa = new TarifaBean();
				tarifa.setIdtarifa(rs.getInt("idtarifa"));
				tarifa.setDescricao(rs.getString("descricao"));
				tarifa.setValor(rs.getFloat("valor"));
			}
			return tarifa;
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return null;
		} finally {
			if (stmt != null)
				try {
					if (conn != null)
						conn.close();
					if (stmt != null)
						stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	@Override
	public List<Object> getItens() {
		List<Object> todos = new ArrayList<Object>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SQL_SELECT_ALL);

			TarifaBean tarifa = null;

			rs = stmt.executeQuery();
			while (rs.next()) {
				tarifa = new TarifaBean();
				tarifa.setIdtarifa(rs.getInt("idtarifa"));
				tarifa.setDescricao(rs.getString("descricao"));
				tarifa.setValor(rs.getFloat("valor"));

				todos.add(tarifa);
			}
			return todos;
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return null;
		} finally {
			if (stmt != null)
				try {
					if (conn != null)
						conn.close();
					if (stmt != null)
						stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	public List<Object> getItensPor(String condicao) {
		List<Object> todos = new ArrayList<Object>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SQL_SELECT_ALL + condicao);

			TarifaBean tarifa = null;

			rs = stmt.executeQuery();
			while (rs.next()) {
				tarifa = new TarifaBean();
				tarifa.setIdtarifa(rs.getInt("idtarifa"));
				tarifa.setDescricao(rs.getString("descricao"));
				tarifa.setValor(rs.getFloat("valor"));

				todos.add(tarifa);
			}
			return todos;
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return null;
		} finally {
			if (stmt != null)
				try {
					if (conn != null)
						conn.close();
					if (stmt != null)
						stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

}
