package com.example.agendatarea;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class DrawerListAdapter extends ArrayAdapter {

	public DrawerListAdapter(Context context, List objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)parent.getContext().
					getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.drawer_list_item, null);
		}


		ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
		TextView name = (TextView) convertView.findViewById(R.id.name);

		ItemsDrawer item = (ItemsDrawer) getItem(position);
		icon.setImageResource(item.getIconId());
		name.setText(item.getName());

		return convertView;
	}
}
