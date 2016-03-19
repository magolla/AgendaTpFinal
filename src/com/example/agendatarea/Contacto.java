package com.example.agendatarea;

import android.os.Parcel;
import android.os.Parcelable;

public class Contacto implements Parcelable{
	
	private String id;
	private String nombre;
	private String apellido;
	private String telefono;
	private String tipoTelefono;

	
	public Contacto()
	{
		id = "";
		nombre = "";
		apellido = "";
		telefono = "";
		tipoTelefono = "";
		
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getTipoTelefono() {
		return tipoTelefono;
	}
	public void setTipoTelefono(String tipoTelefono) {
		this.tipoTelefono = tipoTelefono;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(nombre);
		dest.writeString(apellido);
		dest.writeString(telefono);
		dest.writeString(tipoTelefono);
	}
	
	  public static final Parcelable.Creator<Contacto> CREATOR = new Parcelable.Creator<Contacto>() {
	        public Contacto createFromParcel(Parcel in) {
	            return new Contacto(in);
	        }

	        public Contacto[] newArray(int size) {
	            return new Contacto[size];
	        }	
	    };
	
	    private Contacto(Parcel in) {
	    	id = in.readString();
	        nombre = in.readString();
	        apellido = in.readString();
	        telefono = in.readString();
	        tipoTelefono = in.readString();
	    }

}