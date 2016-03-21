package com.example.agendatarea;

import java.io.FileInputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PrincipalActividad extends Activity {
	
	private ListView lista;
	BaseAdapter adapter;
	String nombreApellido;
	
	private DrawerLayout drawer;
	private ListView listaDrawer;
	private TypedArray iconosDrawer;
	private String[] textoDrawer;
	private ArrayList<itemsDrawer> itemsdrawer;
	NavigationAdapter NavAdapter;
	
	
	private IContactoServicio contactoServicio;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getReadableDatabase();
		
//		 db.execSQL("DROP DATABASE " + DbContactosHelper.FILE_NAME);
//		db.execSQL(DbContactosHelper.getCreatetablecontactos());

		Button btnAgregar = (Button)findViewById(R.id.agregar);
		
		lista = (ListView)findViewById(R.id.lista);
		
		registerForContextMenu(lista);
		
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		listaDrawer = (ListView) findViewById(R.id.left_drawer);
		
		iconosDrawer = getResources().obtainTypedArray(R.array.listaDrawerIconos);
		textoDrawer = getResources().getStringArray(R.array.listaDrawer);
		
		itemsdrawer =  new ArrayList<itemsDrawer>();
		
		itemsdrawer.add(new itemsDrawer(textoDrawer[0], iconosDrawer.getResourceId(0, -1)));
		itemsdrawer.add(new itemsDrawer(textoDrawer[1], iconosDrawer.getResourceId(1, -1)));
		itemsdrawer.add(new itemsDrawer(textoDrawer[2], iconosDrawer.getResourceId(2, -1)));

		NavAdapter = new NavigationAdapter(this, itemsdrawer);
		
		listaDrawer.setAdapter(NavAdapter);
		
		
	        
		
		//Para agregar un contacto nuevo
		btnAgregar.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(PrincipalActividad.this, DatosActividad.class);
				startActivityForResult(intent, 99);
				
			}
		});
		
		
		

		//Click para llamar al numero de telefono
	lista.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
			
			SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getReadableDatabase();	
			String telefono = null;
			
			
			Cursor c = db.rawQuery("select telefono from "+ DbContactosHelper.TABLA_CONTACTOS + " where id=" + adapter.getItemId(position), null);
			c.moveToFirst();
			if (c.moveToFirst()) {
			    telefono = c.getString(c.getColumnIndex("telefono"));
			}
			
			
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telefono));
			startActivity(intent);
			
		}
	});


		
	
		//Adapter
		adapter = new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				if(convertView == null){
					convertView = getLayoutInflater().inflate(R.layout.lista_filas, parent, false);
				}
				TextView nombreApellido = (TextView)convertView.findViewById(R.id.nombreApellido);
				TextView tipoTel = (TextView)convertView.findViewById(R.id.tipoTel);
				ImageView myImage = (ImageView) convertView.findViewById(R.id.fotoContacto);
				nombreApellido.setText(getNombre(position) + " " + getApellido(position));
				tipoTel.setText(getTipoTelefono(position) + ":" + getTelefono(position) + ":" + getItemId(position));
				
				
				
		            myImage.setImageBitmap(getImageBitmap(getApplicationContext(),getNombre(position) + getApellido(position)));
				
				
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
		
		if(requestCode == 99 || requestCode == 98){
			if( resultCode == Activity.RESULT_OK ){
				adapter.notifyDataSetChanged();
			}
		}
		
	}
	
	public Bitmap getImageBitmap(Context context,String name){
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
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    int index = info.position;
	    
		if (item.getTitle() == "Editar") {
			Toast.makeText(this, "Editar" + adapter.getItemId(index), Toast.LENGTH_SHORT).show();
			
			Contacto contactoObj = new Contacto();
			
			String id = String.valueOf(adapter.getItemId(index));
			
			contactoObj = buscarContactoXId(id, getApplicationContext());
			
			
			
			Intent intent = new Intent(PrincipalActividad.this, DatosActividadEdicion.class);
			
			
			intent.putExtra("contactoObj", contactoObj);
			startActivityForResult(intent, 98);
			
			
			
		} else if (item.getTitle() == "Eliminar") {
		SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getReadableDatabase();	
		String nombre = null;
		String apellido = null;
		
		
		Cursor c = db.rawQuery("select nombre from "+ DbContactosHelper.TABLA_CONTACTOS + " where id=" + adapter.getItemId(index), null);
		c.moveToFirst();
		if (c.moveToFirst()) {
		    nombre = c.getString(c.getColumnIndex("nombre"));
		}
		
		c = db.rawQuery("select apellido from "+ DbContactosHelper.TABLA_CONTACTOS + " where id=" + adapter.getItemId(index), null);
		c.moveToFirst();
		if (c.moveToFirst()) {
		    apellido = c.getString(c.getColumnIndex("apellido"));
		}
		
		db.delete(DbContactosHelper.TABLA_CONTACTOS.toString(), "id=?", new String[] {String.valueOf(adapter.getItemId(index))});
		
		deleteFile(nombre + apellido);
		
		db.close();
		
		adapter.notifyDataSetChanged();
		} else {
			return false;
		}
		return true;
	}
	
	public Contacto buscarContactoXId(String id,Context contexto){

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
