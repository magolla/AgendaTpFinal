package com.example.agendatarea;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PrincipalActividad extends Activity {
	
	private ListView lista;
	BaseAdapter adapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		
		Button btnAgregar = (Button)findViewById(R.id.agregar);
		
		lista = (ListView)findViewById(R.id.lista);
		
		registerForContextMenu(lista);
		
		btnAgregar.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(PrincipalActividad.this, DatosActividad.class);
				startActivityForResult(intent, 99);
				
			}
		});
		
	
		
		adapter = new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				if(convertView == null){
					convertView = getLayoutInflater().inflate(R.layout.lista_filas, parent, false);
				}
				TextView nombreApellido = (TextView)convertView.findViewById(R.id.nombreApellido);
				TextView tipoTel = (TextView)convertView.findViewById(R.id.tipoTel);
				nombreApellido.setText(getNombre(position) + " " + getApellido(position));
				tipoTel.setText(getTipoTelefono(position) + ":" + getTelefono(position) + ":" + getItemId(position));
				return convertView;
			}
			
			@Override
			public long getItemId(int position) {
				SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getReadableDatabase();
				Cursor c = db.rawQuery("SELECT id FROM " + DbContactosHelper.TABLA_CONTACTOS + " ORDER BY nombre ASC LIMIT 1 OFFSET " + position, null);
				c.moveToFirst();
				String id = c.getString(0);
				c.close();
				db.close();
				return Long.parseLong(id);
			}
			
			public String getNombre(int position) {
				SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getReadableDatabase();
				Cursor c = db.rawQuery("SELECT nombre FROM " + DbContactosHelper.TABLA_CONTACTOS + " ORDER BY nombre ASC LIMIT 1 OFFSET " + position, null);
				c.moveToFirst();
				String nombre = c.getString(0);
				c.close();
				db.close();
				return nombre;
			}
			
			public String getApellido(int position) {
				SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getReadableDatabase();
				Cursor c = db.rawQuery("SELECT apellido FROM " + DbContactosHelper.TABLA_CONTACTOS + " ORDER BY nombre ASC LIMIT 1 OFFSET " + position, null);
				c.moveToFirst();
				String nombre = c.getString(0);
				c.close();
				db.close();
				return nombre;
			}
			
			public String getTelefono(int position) {
				SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getReadableDatabase();
				Cursor c = db.rawQuery("SELECT telefono FROM " + DbContactosHelper.TABLA_CONTACTOS + " ORDER BY nombre ASC LIMIT 1 OFFSET " + position, null);
				c.moveToFirst();
				String nombre = c.getString(0);
				c.close();
				db.close();
				return nombre;
			}
			
			public String getTipoTelefono(int position) {
				SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getReadableDatabase();
				Cursor c = db.rawQuery("SELECT tipoTelefono FROM " + DbContactosHelper.TABLA_CONTACTOS + " ORDER BY nombre ASC LIMIT 1 OFFSET " + position, null);
				c.moveToFirst();
				String nombre = c.getString(0);
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
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Opciones");
		menu.add(0, v.getId(), 0, "Editar");
		menu.add(0, v.getId(), 0, "Eliminar");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 99){
			if( resultCode == Activity.RESULT_OK ){
				adapter.notifyDataSetChanged();
			}
		}
		
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    int index = info.position;
	    
		if (item.getTitle() == "Editar") {
			Toast.makeText(this, "Editar" + adapter.getItemId(index), Toast.LENGTH_SHORT).show();
			
		} else if (item.getTitle() == "Eliminar") {
			Toast.makeText(this, "Eliminar", Toast.LENGTH_SHORT).show();
		SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getReadableDatabase();	
		db.delete(DbContactosHelper.TABLA_CONTACTOS.toString(), "id=?", new String[] {String.valueOf(adapter.getItemId(index))});
		adapter.notifyDataSetChanged();
		} else {
			return false;
		}
		return true;
	}

}
