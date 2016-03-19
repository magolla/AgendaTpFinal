package com.example.agendatarea;

import java.io.Serializable;

import android.content.Context;

public interface IContactoServicio extends Serializable{
	
	public Contacto buscarContactoXId(String id,Context contexto);

}
