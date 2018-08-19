package com.ws.taroman.atv_contx.modelo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Turma {
	private int id, ano;
	private String curso;
	private Aluno alunos[];
	
	public Turma() {	}

	public Turma(int id, int ano, String curso, Aluno[] alunos) {
		this.id = id;
		this.ano = ano;
		this.curso = curso;
		this.alunos = alunos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public Aluno[] getAlunos() {
		return alunos;
	}

	public void setAlunos(Aluno[] alunos) {
		this.alunos = alunos;
	}
	
	@SuppressWarnings("unchecked")
	public String toJson() {
		JSONObject jso = new JSONObject();
		
		jso.put("id", id);
		jso.put("ano", ano);
		jso.put("curso", curso);
		
		JSONArray jsaAlunos = new JSONArray();
		for (Aluno a:alunos) {
			jsaAlunos.add(a);
		}
		
		jso.put("alunos", jsaAlunos);
		
		return jso.toJSONString();
	}
	
}
