package com.example.agendatarea;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ContactoServicio implements IContactoServicio{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Contacto buscarContactoXId(String id,Context contexto){
		
		Contacto contactoObj = new Contacto();
		
		SQLiteDatabase db = new DbContactosHelper(contexto).getReadableDatabase();
		
		Cursor c = db.rawQuery("select nombre,apellido,telefono,tipoTelefono from "+ DbContactosHelper.TABLA_CONTACTOS 
				+ " where id=" + id, null);

		//Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
		     //Recorremos el cursor hasta que no haya más registros
		     do {
		    	 
		    	 contactoObj.setNombre(c.getString(0));
		    	 contactoObj.setApellido(c.getString(1));
		    	 contactoObj.setTelefono(c.getString(2));
		    	 contactoObj.setTipoTelefono(c.getString(3));

		     } while(c.moveToNext());
		}
		
		contactoObj.setId(id);
		db.close();
		
		return contactoObj;
	}

}
