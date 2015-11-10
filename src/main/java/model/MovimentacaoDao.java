package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import factory.Conexao;

public class MovimentacaoDao implements GenericDao {

	private static Connection conexao;

	private static final String SQL_INSERT = "insert into movimentacao(placa, entrada, fatura) values (?,?,?)";
	private static final String SQL_DELETE = "delete from movimentacao where idmov=?";
	private static final String SQL_UPDATE = "update movimentacao set placa=?, entrada=?, saida=? where idmov=?";
	private static final String SQL_INSERT_SAIDA = "update movimentacao set saida=?, fatura=? where idmov=?";
	private static final String SQL_SELECT_BY_ID = "select * from movimentacao where idmov=?";
	private static final String SQL_SELECT_ALL = "select * from movimentacao order by idmov desc";

	@Override
	public boolean inserir(Object objeto) {
		conexao = Conexao.conectar();
		try {
			PreparedStatement stmt = conexao.prepareStatement(SQL_INSERT);
			MovimentacaoBean mov = (MovimentacaoBean) objeto;
			stmt.setString(1, mov.getPlaca().getPlaca());
			stmt.setTimestamp(2, new Timestamp(mov.getEntrada().getTimeInMillis()));
			stmt.setFloat(3, mov.getPlaca().getIdtarifa().getValor());
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
			String idmov = (String) objeto;
			stmt.setString(1, idmov);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object consultar(Object objeto) {
		conexao = Conexao.conectar();
		try {
			PreparedStatement stmt = conexao.prepareStatement(SQL_SELECT_BY_ID);
			String idmov = (String) objeto;
			stmt.setString(1, idmov);
			ResultSet rs = stmt.executeQuery();
			MovimentacaoBean mov = new MovimentacaoBean();
			while (rs.next()) {
				VeiculoDao dao = new VeiculoDao();
				mov.setIdmov(rs.getInt("idmov"));
				mov.setPlaca((VeiculoBean) dao.consultar(rs.getString("placa")));
				Calendar entrada = Calendar.getInstance();
				entrada.setTime(rs.getTimestamp("entrada"));
				mov.setEntrada(entrada);
				Calendar saida = Calendar.getInstance();
				if (rs.getTimestamp("saida") != null) {
					saida.setTime(rs.getTimestamp("saida"));
					mov.setSaida(saida);
				}
				mov.setFatura(rs.getFloat("fatura"));
			}
			return mov;
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
			MovimentacaoBean mov = null;
			ResultSet rs = null;
			rs = stmt.executeQuery();
			while (rs.next()) {
				VeiculoDao dao = new VeiculoDao();
				mov = new MovimentacaoBean();
				mov.setIdmov(rs.getInt("idmov"));
				mov.setPlaca((VeiculoBean) dao.consultar(rs.getString("placa")));
				Calendar entrada = Calendar.getInstance();
				entrada.setTime(rs.getTimestamp("entrada"));
				mov.setEntrada(entrada);
				Calendar saida = Calendar.getInstance();
				if (rs.getTimestamp("saida") != null) {
					saida.setTime(rs.getTimestamp("saida"));
					mov.setSaida(saida);
				}
				mov.setFatura(rs.getFloat("fatura"));
				todos.add(mov);
			}
			return todos;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			Conexao.fecharConexao(conexao);
		}
	}

	public boolean insereSaida(Object objeto) {
		conexao = Conexao.conectar();
		try {
			PreparedStatement stmt = conexao.prepareStatement(SQL_INSERT_SAIDA);
			MovimentacaoBean mov = (MovimentacaoBean) objeto;
			stmt.setTimestamp(1, new Timestamp(mov.getSaida().getTimeInMillis()));
			stmt.setFloat(2, mov.getFatura());
			stmt.setInt(3, mov.getIdmov());
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

}
