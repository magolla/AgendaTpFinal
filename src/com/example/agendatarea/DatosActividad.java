package com.example.agendatarea;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class DatosActividad extends Activity {


	private Uri uri;
	



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datos_actividad);



		Spinner spinner = (Spinner) findViewById(R.id.tipoTel);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tipoTelArray, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

		final EditText nombre = (EditText) findViewById(R.id.nombre);
		final EditText apellido = (EditText) findViewById(R.id.apellido);
		final EditText telefono = (EditText) findViewById(R.id.tel);
		final Spinner tipoTel = (Spinner) findViewById(R.id.tipoTel);
		final Button btnOk = (Button) findViewById(R.id.btnOk);
		final ImageView foto = (ImageView) findViewById(R.id.imagen);


		foto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");

				startActivityForResult(intent, 95);

			}
		});

		btnOk.setOnClickListener(new View.OnClickListener() {



			@Override
			public void onClick(View v) {
				
				String nombreString = nombre.getText().toString();
				String apellidoString = apellido.getText().toString();
				String telefonoString = telefono.getText().toString();
				String tipoTelString = tipoTel.getSelectedItem().toString();
				
				if(nombre.getText().toString().matches("")|| apellido.getText().toString().matches("") || telefono.getText().toString().matches("")){
					
					Toast.makeText(DatosActividad.this, "Hay campos sin rellenar.", Toast.LENGTH_LONG ).show();
				}
				else
				{
					Toast.makeText(DatosActividad.this, "Todo validado", Toast.LENGTH_LONG ).show();
					
					SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getWritableDatabase();
					Cursor cantNombre = db.rawQuery("SELECT "+ "COUNT(*) " + "FROM " + DbContactosHelper.TABLA_CONTACTOS + " " + "WHERE LOWER(nombre) = LOWER(?)", new String[]{nombreString });
					Cursor cantApellido = db.rawQuery("SELECT "+ "COUNT(*) " + "FROM " + DbContactosHelper.TABLA_CONTACTOS + " " + "WHERE LOWER(nombre) = LOWER(?)", new String[]{nombreString });
					
					cantNombre.moveToFirst();
					cantApellido.moveToFirst();
					int cantRegistros = cantNombre.getInt( 0 );
					int cantRegistrosApellido = cantApellido.getInt(0);
					cantNombre.close();
					cantApellido.close();
					if(cantRegistros > 0 && cantRegistrosApellido > 0){
						Toast.makeText(getApplicationContext(), "Ese nombre ya existe", Toast.LENGTH_LONG).show();
						return;
					}else{
						ContentValues values = new ContentValues();
						values.put("nombre", nombreString);
						values.put("apellido", apellidoString);
						values.put("telefono", telefonoString);
						values.put("tipoTelefono", tipoTelString);
						db.insert(DbContactosHelper.TABLA_CONTACTOS, null, values);
						
						setResult(Activity.RESULT_OK);
						
						finish();
					}
					db.close();

				}

			}
		});



	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(requestCode == 95){
			if(resultCode == Activity.RESULT_OK){
				uri = data.getData();
				ImageView image = (ImageView)findViewById(R.id.imagen);
				image.setImageURI(uri);

				/*

			    OutputStream out;
	            String root = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
	            File createDir = new File(root+"Folder Name"+File.separator);
	            if(!createDir.exists()) {
	                createDir.mkdir();
	            }
	            File file = new File(root + "Folder Name" + File.separator +"Name of File");
	            file.createNewFile();
	            out = new FileOutputStream(file);                       

		        out.write(data);
		        out.close();

				 * */

			}
		}

	}


}