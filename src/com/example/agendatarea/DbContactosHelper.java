package com.example.agendatarea;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbContactosHelper extends SQLiteOpenHelper{
	
	private static final String FILE_NAME = "DbAgenda.db3";
	private static final int CURRENT_VERSION = 2;
	
	public static final String TABLA_CONTACTOS = "Contactos";
	
	private static final String createTableContactos = "CREATE TABLE " + TABLA_CONTACTOS + " ("
			+ "  id integer primary key autoincrement,"
			+ "  nombre varchar(64) NOT NULL,"
			+ "  apellido varchar(64) DEFAULT NULL,"
			+ "  telefono varchar(15) DEFAULT NULL,"
			+ "  tipoTelefono varchar(10) DEFAULT NULL)"; 
	
	
	public DbContactosHelper(Context context) {
		super(context, FILE_NAME, null, CURRENT_VERSION);
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(createTableContactos);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE " + TABLA_CONTACTOS);
		db.execSQL(createTableContactos);
		
	}

}
