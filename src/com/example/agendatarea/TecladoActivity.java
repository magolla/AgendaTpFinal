package com.example.agendatarea;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TecladoActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teclado);
		
		Button btn1 = (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(btnOnClick);
		Button btn2 = (Button) findViewById(R.id.btn2);
		btn2.setOnClickListener(btnOnClick);
		Button btn3 = (Button) findViewById(R.id.btn3);
		btn3.setOnClickListener(btnOnClick);
		Button btn4 = (Button) findViewById(R.id.btn4);
		btn4.setOnClickListener(btnOnClick);
		Button btn5 = (Button) findViewById(R.id.btn5);
		btn5.setOnClickListener(btnOnClick);
		Button btn6 = (Button) findViewById(R.id.btn6);
		btn6.setOnClickListener(btnOnClick);
		Button btn7 = (Button) findViewById(R.id.btn7);
		btn7.setOnClickListener(btnOnClick);
		Button btn8 = (Button) findViewById(R.id.btn8);
		btn8.setOnClickListener(btnOnClick);
		Button btn9 = (Button) findViewById(R.id.btn9);
		btn9.setOnClickListener(btnOnClick);
		Button btn0 = (Button) findViewById(R.id.btn0);
		btn0.setOnClickListener(btnOnClick);
		Button btnAsterisco = (Button) findViewById(R.id.btnAsterisco);
		btnAsterisco.setOnClickListener(btnOnClick);
		Button btnNumeral = (Button) findViewById(R.id.btnNumeral);
		btnNumeral.setOnClickListener(btnOnClick);
		
		
		

		
		

	}

	private OnClickListener btnOnClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			TextView editTelefono = (TextView) findViewById(R.id.editTelefono);
			
			String numeroActual = editTelefono.getText().toString();
			
			Button b = (Button) v;
			String text = b.getText().toString();
			
			numeroActual = numeroActual + text;
			
			editTelefono.setText(numeroActual);
			
		}
	};
	

}
