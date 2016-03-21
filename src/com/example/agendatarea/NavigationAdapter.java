package com.example.agendatarea;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationAdapter extends BaseAdapter {
	
	private Activity activity;
	ArrayList<itemsDrawer> arrayItems;
	
	public NavigationAdapter(Activity activity,ArrayList<itemsDrawer> listaArray) {
		super();
		this.activity = activity;
		this.arrayItems = listaArray;
		
	}
	
	@Override
	public Object getItem(int position) {
		return arrayItems.get(position);
		
	}

	@Override
	public int getCount() {
		
		return arrayItems.size();
	}

	

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	public static class Fila
	{
		TextView titulo_itm;
		ImageView icono;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Fila view;
		LayoutInflater inflator = activity.getLayoutInflater();
		
		if(convertView==null){
			
			view = new Fila();
			
			itemsDrawer itm = arrayItems.get(position);
			convertView =inflator.inflate(R.layout.lista_drawer, null);
			
			
			view.titulo_itm =(TextView) convertView.findViewById(R.id.nombreItem);
			view.titulo_itm.setText(itm.getTitulo());
			
			view.icono = (ImageView) convertView.findViewById(R.id.icon);
			view.icono.setImageResource(itm.getIcono());
			convertView.setTag(view);
		}
		else
		{
			view = (Fila) convertView.getTag();
			
		}
			return convertView;
		
		
	}

	
	
	
}
