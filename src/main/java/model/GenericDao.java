package model;

import java.util.List;

public interface GenericDao {

	public boolean inserir(Object objeto);

	public boolean remover(Object objeto);

	public boolean alterar(Object objeto);

	public Object consultar(Object objeto);

	public List<Object> getItens();
}
