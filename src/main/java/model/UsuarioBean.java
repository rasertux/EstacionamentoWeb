package model;

public class UsuarioBean {

	String nome, login, senha, hashrecuperasenha, email;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getHashrecuperasenha() {
		return hashrecuperasenha;
	}
	
	public void setHashrecuperasenha(String hashrecuperasenha) {
		this.hashrecuperasenha = hashrecuperasenha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
