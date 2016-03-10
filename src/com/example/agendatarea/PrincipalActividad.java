package com.example.agendatarea;




import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PrincipalActividad extends Activity {
	
	private ListView lista;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Button btnAgregar = (Button)findViewById(R.id.agregar);
		
		lista = (ListView)findViewById(R.id.lista);
		
		btnAgregar.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(PrincipalActividad.this, DatosActividad.class);
				startActivityForResult(intent, 99);
				
			}
		});
		
		final BaseAdapter adapter = new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				if(convertView == null){
					convertView = getLayoutInflater().inflate(R.layout.lista_filas, parent, false);
				}
				TextView nombreApellido = (TextView)convertView.findViewById(R.id.nombreApellido);
				TextView tipoTel = (TextView)convertView.findViewById(R.id.tipoTel);
				nombreApellido.setText(getNombre(position) + " " + getApellido(position));
				tipoTel.setText(getTipoTelefono(position) + ":" + getTelefono(position));
				return convertView;
			}
			
			@Override
			public long getItemId(int position) {
				return 1;
			}
			
			public String getNombre(int position) {
				SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getReadableDatabase();
				Cursor c = db.rawQuery("SELECT nombre FROM " + DbContactosHelper.TABLA_CONTACTOS + " ORDER BY nombre ASC LIMIT 1 OFFSET " + position, null);
				c.moveToFirst();
				String nombre = c.getString(position);
				c.close();
				db.close();
				return nombre;
			}
			
			public String getApellido(int position) {
				SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getReadableDatabase();
				Cursor c = db.rawQuery("SELECT apellido FROM " + DbContactosHelper.TABLA_CONTACTOS + " ORDER BY nombre ASC LIMIT 1 OFFSET " + position, null);
				c.moveToFirst();
				String nombre = c.getString(position);
				c.close();
				db.close();
				return nombre;
			}
			
			public String getTelefono(int position) {
				SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getReadableDatabase();
				Cursor c = db.rawQuery("SELECT telefono FROM " + DbContactosHelper.TABLA_CONTACTOS + " ORDER BY nombre ASC LIMIT 1 OFFSET " + position, null);
				c.moveToFirst();
				String nombre = c.getString(position);
				c.close();
				db.close();
				return nombre;
			}
			
			public String getTipoTelefono(int position) {
				SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getReadableDatabase();
				Cursor c = db.rawQuery("SELECT tipoTelefono FROM " + DbContactosHelper.TABLA_CONTACTOS + " ORDER BY nombre ASC LIMIT 1 OFFSET " + position, null);
				c.moveToFirst();
				String nombre = c.getString(position);
				c.close();
				db.close();
				return nombre;
			}
			
			
			@Override
			public int getCount() {

					SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getReadableDatabase();
					Cursor c = db.rawQuery("SELECT "
								+ "COUNT(*) "
							  + "FROM " + DbContactosHelper.TABLA_CONTACTOS + " ", 
						  	  null);
					c.moveToFirst();
					int cantRegistros = c.getInt( 0 );
					c.close();
					db.close();
					return cantRegistros;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		lista.setAdapter(adapter);
	}

}
