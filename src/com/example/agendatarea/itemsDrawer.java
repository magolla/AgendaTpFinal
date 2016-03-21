package com.example.agendatarea;

public class itemsDrawer {
	private String titulo;
	private int icono;
	
	public itemsDrawer(String titulo,int icono){
		this.setTitulo(titulo);
		this.setIcono(icono);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getIcono() {
		return icono;
	}

	public void setIcono(int icono) {
		this.icono = icono;
	}
	
	
	

}
