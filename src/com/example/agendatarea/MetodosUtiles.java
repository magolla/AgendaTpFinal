package com.example.agendatarea;

import java.io.FileInputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MetodosUtiles {
	
	public static Bitmap getImageBitmap(Context context,String name){
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

}
