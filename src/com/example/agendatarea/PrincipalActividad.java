package com.example.agendatarea;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
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
	
	private String[] titulosDrawer;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

	
	
	
	private IContactoServicio contactoServicio;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getReadableDatabase();
//		
//		 db.execSQL("DROP DATABASE " + DbContactosHelper.FILE_NAME);
//		db.execSQL("DROP TABLE " + DbContactosHelper.TABLA_CONTACTOS);
//		db.execSQL(DbContactosHelper.getCreatetablecontactos());

		Button btnAgregar = (Button)findViewById(R.id.agregar);
		
		lista = (ListView)findViewById(R.id.lista);
		
		registerForContextMenu(lista);
		
		
        
		titulosDrawer = getResources().getStringArray(R.array.listaDrawer);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        ArrayList<ItemsDrawer> items = new ArrayList<ItemsDrawer>();
        items.add(new ItemsDrawer(titulosDrawer[0],R.drawable.ic_action_dial_pad));
        items.add(new ItemsDrawer(titulosDrawer[1],R.drawable.ic_action_person));
        items.add(new ItemsDrawer(titulosDrawer[2],R.drawable.ic_action_map));
        

        // Set the adapter for the list view
        mDrawerList.setAdapter(new DrawerListAdapter(this, items));

		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				
//				 Fragment fragment = new PlanetFragment();
//				    Bundle args = new Bundle();
//				    args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//				    fragment.setArguments(args);
//
//				    // Insert the fragment by replacing any existing fragment
//				    FragmentManager fragmentManager = getFragmentManager();
//				    fragmentManager.beginTransaction()
//				                   .replace(R.id.content_frame, fragment)
//				                   .commit();
//
//				    // Highlight the selected item, update the title, and close the drawer
//				    mDrawerList.setItemChecked(position, true);
//				    setTitle(mPlanetTitles[position]);
//				    mDrawerLayout.closeDrawer(mDrawerList);
//
//				
//				
			}
		});
		
		
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
				
				
				if(MetodosUtiles.getImageBitmap(getApplicationContext(),getNombre(position) + getApellido(position))!=null){
					myImage.setImageBitmap(MetodosUtiles.getImageBitmap(getApplicationContext(),getNombre(position) + getApellido(position)));
				}else
				{
					myImage.setImageResource(R.drawable.agendaimg);
					
				}
				
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
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    int index = info.position;
	    
		if (item.getTitle() == "Editar") {
			
			Contacto contactoObj = new Contacto();
			
			String id = String.valueOf(adapter.getItemId(index));
			
			contactoObj = MetodosUtiles.buscarContactoXId(id, getApplicationContext());
			
			
			
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
	
	

}
