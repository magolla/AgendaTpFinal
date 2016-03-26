package com.example.agendatarea;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class DatosActividadEdicion extends Activity {

private Uri uri;
	boolean guardarFoto=false;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datos_actividad_edicion);
		
		Intent i = getIntent();
		
		final Contacto contactoObj = (Contacto) i.getParcelableExtra("contactoObj");

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
		final Button btnEditar = (Button) findViewById(R.id.btnEditar);
		final ImageView foto = (ImageView) findViewById(R.id.imagen);
		
		

		nombre.setText(contactoObj.getNombre());
		apellido.setText(contactoObj.getApellido());
		telefono.setText(contactoObj.getTelefono());
		if(MetodosUtiles.getImageBitmap(getApplicationContext(),contactoObj.getNombre() + contactoObj.getApellido())!=null){
			foto.setImageBitmap(MetodosUtiles.getImageBitmap(getApplicationContext(),contactoObj.getNombre() + contactoObj.getApellido()));
		}
		 int index = 0;

		  for (int f=0;f<spinner.getCount();f++){
		   if (spinner.getItemAtPosition(f).toString().equalsIgnoreCase(contactoObj.getTipoTelefono())){
		    index = f;
		    break;
		   }
		   
		   }
		
		tipoTel.setSelection(index);

		 //private method of your class
		 
	
		  
		  
		
		foto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");

				startActivityForResult(intent, 95);

			}
		});

		btnEditar.setOnClickListener(new View.OnClickListener() {



			@Override
			public void onClick(View v) {

				String nombreString = nombre.getText().toString();
				String apellidoString = apellido.getText().toString();
				String telefonoString = telefono.getText().toString();
				String tipoTelString = tipoTel.getSelectedItem().toString();

				if(nombre.getText().toString().matches("")|| apellido.getText().toString().matches("") || telefono.getText().toString().matches("")){

					Toast.makeText(DatosActividadEdicion.this, "Hay campos sin rellenar.", Toast.LENGTH_LONG ).show();
				}
				else
				{

					SQLiteDatabase db = new DbContactosHelper(getApplicationContext()).getWritableDatabase();

						ContentValues values = new ContentValues();
						values.put("nombre", nombreString);
						values.put("apellido", apellidoString);
						values.put("telefono", telefonoString);
						values.put("tipoTelefono", tipoTelString);

						deleteFile(contactoObj.getNombre() + contactoObj.getApellido());
						
						
						if(foto!=null && guardarFoto){
							foto.buildDrawingCache();
							Bitmap bitmap =  foto.getDrawingCache();
							saveImage(getApplicationContext(), bitmap, nombreString + apellidoString);
						}
						

//						db.insert(DbContactosHelper.TABLA_CONTACTOS, null, values);
						
						db.execSQL("UPDATE "+ DbContactosHelper.TABLA_CONTACTOS +" SET nombre=? WHERE id="+ contactoObj.getId(),new String[] {nombreString});
						db.execSQL("UPDATE "+ DbContactosHelper.TABLA_CONTACTOS +" SET apellido=? WHERE id="+ contactoObj.getId(),new String[] {apellidoString});
						db.execSQL("UPDATE "+ DbContactosHelper.TABLA_CONTACTOS +" SET telefono=? WHERE id="+ contactoObj.getId(),new String[] {telefonoString});
						db.execSQL("UPDATE "+ DbContactosHelper.TABLA_CONTACTOS +" SET tipoTelefono=? WHERE id="+ contactoObj.getId(),new String[] {tipoTelString});

						setResult(Activity.RESULT_OK);

						finish();
					db.close();

				}

			}


		});

		foto.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				
				foto.setImageResource(R.drawable.agendaimg);
				guardarFoto = false;
				
				return false;
			}
		});

	}
	
	
	public void saveImage(Context context, Bitmap b,String name){

	    FileOutputStream out;
	    try {
	        out = context.openFileOutput(name, Context.MODE_PRIVATE);
	        b.compress(Bitmap.CompressFormat.JPEG, 90, out);
	        out.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private Bitmap uriToBitmap(Uri selectedFileUri) {
        
		Bitmap image = null;
		try {
            ParcelFileDescriptor parcelFileDescriptor =
                    getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            image = BitmapFactory.decodeFileDescriptor(fileDescriptor);


            parcelFileDescriptor.close();
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(requestCode == 95){
			if(resultCode == Activity.RESULT_OK){
				uri = data.getData();
				ImageView image = (ImageView)findViewById(R.id.imagen);
				image.setImageURI(uri);
				guardarFoto = true;

			}
		}

	}
}
