package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import factory.Conexao;

public class VeiculoDao implements GenericDao {

	private static Connection conexao;

	private static final String SQL_INSERT = "insert into veiculo values (?,?,?,?)";
	private static final String SQL_DELETE = "delete from veiculo where placa=?";
	private static final String SQL_UPDATE = "update veiculo set marca=?, modelo=?, idtarifa=? where placa=?";
	private static final String SQL_SELECT_BY_ID = "select * from veiculo where placa=?";
	private static final String SQL_SELECT_ALL = "select * from veiculo";

	@Override
	public boolean inserir(Object objeto) {
		conexao = Conexao.conectar();
		try {
			PreparedStatement stmt = conexao.prepareStatement(SQL_INSERT);
			VeiculoBean veiculo = (VeiculoBean) objeto;
			stmt.setString(1, veiculo.getPlaca());
			stmt.setString(2, veiculo.getMarca());
			stmt.setString(3, veiculo.getModelo());
			stmt.setInt(4, veiculo.getIdtarifa().getIdtarifa());
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

	@Override
	public boolean remover(Object objeto) {
		conexao = Conexao.conectar();
		try {
			PreparedStatement stmt = conexao.prepareStatement(SQL_DELETE);
			String placa = (String) objeto;
			stmt.setString(1, placa);
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

	@Override
	public boolean alterar(Object objeto) {
		conexao = Conexao.conectar();
		try {
			PreparedStatement stmt = conexao.prepareStatement(SQL_UPDATE);
			VeiculoBean veiculo = (VeiculoBean) objeto;
			stmt.setString(1, veiculo.getMarca());
			stmt.setString(2, veiculo.getModelo());
			stmt.setInt(3, veiculo.getIdtarifa().getIdtarifa());
			stmt.setString(4, veiculo.getPlaca());
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

	@Override
	public Object consultar(Object objeto) {
		conexao = Conexao.conectar();
		try {
			PreparedStatement stmt = conexao.prepareStatement(SQL_SELECT_BY_ID);
			VeiculoBean veiculo = (VeiculoBean) objeto;
			stmt.setString(1, veiculo.getPlaca());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				TarifaDao dao = new TarifaDao();
				veiculo = new VeiculoBean();
				veiculo.setPlaca(rs.getString("placa"));
				veiculo.setMarca(rs.getString("marca"));
				veiculo.setModelo(rs.getString("modelo"));
				veiculo.setIdtarifa((TarifaBean) dao.consultar(rs.getInt("idtarifa")));
			}
			return veiculo;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			Conexao.fecharConexao(conexao);
		}
	}

	@Override
	public List<Object> getItens() {
		conexao = Conexao.conectar();
		List<Object> todos = new ArrayList<Object>();
		try {
			PreparedStatement stmt = conexao.prepareStatement(SQL_SELECT_ALL);
			VeiculoBean veiculo = null;
			ResultSet rs = null;
			rs = stmt.executeQuery();
			while (rs.next()) {
				TarifaDao dao = new TarifaDao();
				veiculo = new VeiculoBean();
				veiculo.setPlaca(rs.getString("placa"));
				veiculo.setMarca(rs.getString("marca"));
				veiculo.setModelo(rs.getString("modelo"));
				veiculo.setIdtarifa((TarifaBean) dao.consultar(rs.getInt("idtarifa")));
				todos.add(veiculo);
			}
			return todos;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			Conexao.fecharConexao(conexao);
		}
	}

	public List<Object> getItensPor(String condicao) {
		conexao = Conexao.conectar();
		List<Object> todos = new ArrayList<Object>();
		try {
			PreparedStatement stmt = conexao.prepareStatement(SQL_SELECT_ALL + condicao);
			VeiculoBean veiculo = null;
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				TarifaDao dao = new TarifaDao();
				veiculo = new VeiculoBean();
				veiculo.setPlaca(rs.getString("placa"));
				veiculo.setMarca(rs.getString("marca"));
				veiculo.setModelo(rs.getString("modelo"));
				veiculo.setIdtarifa((TarifaBean) dao.consultar(rs.getInt("idtarifa")));
				todos.add(veiculo);
			}
			return todos;
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return null;
		} finally {
			Conexao.fecharConexao(conexao);
		}
	}

}
