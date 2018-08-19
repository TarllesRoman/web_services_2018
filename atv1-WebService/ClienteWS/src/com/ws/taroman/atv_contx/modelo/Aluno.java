package com.ws.taroman.atv_contx.modelo;

import org.json.simple.JSONObject;

public class Aluno{
	private int id;
	private String nome;
	private boolean matriculado;
	
	public Aluno() {	}

	public Aluno(int id, String nome, boolean matriculado) {
		this.id = id;
		this.nome = nome;
		this.matriculado = matriculado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isMatriculado() {
		return matriculado;
	}

	public void setMatriculado(boolean matriculado) {
		this.matriculado = matriculado;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		JSONObject jso = new JSONObject();
		
		jso.put("id", id);
		jso.put("nome", nome);
		jso.put("matriculado", matriculado);
		
		return jso.toJSONString();
	}


}

