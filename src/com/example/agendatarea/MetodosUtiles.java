package com.example.agendatarea;

import java.io.FileInputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MetodosUtiles {
	
	public static Bitmap getImageBitmap(Context context,String name){
		  try{
		    FileInputStream fis = context.openFileInput(name);
		        Bitmap b = BitmapFactory.decodeStream(fis);
		        fis.close();
		        return b;
		    }
		    catch(Exception e){
		    }
		    return null;
		}
	
	
	public static Contacto buscarContactoXId(String id,Context contexto){

		Contacto contactoObj = new Contacto();

		SQLiteDatabase db = new DbContactosHelper(contexto).getReadableDatabase();

		Cursor c = db.rawQuery("select nombre,apellido,telefono,tipoTelefono from "+ DbContactosHelper.TABLA_CONTACTOS 
				+ " where id=" + id, null);

		if (c.moveToFirst()) {
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
