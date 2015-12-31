package br.com.remember.water.dao.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.remember.water.bean.definition.CopoTomadoDefinition;
import br.com.remember.water.bean.definition.ParametroDefinition;

public class OpenHelper extends SQLiteOpenHelper {

	

	public OpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	      if (!db.isReadOnly()) {
	    	  db.execSQL("PRAGMA foreign_keys=ON;");
	      }
	}

	@SuppressWarnings("static-access")
	@Override
	public void onCreate(SQLiteDatabase db) {
		try{
			
			ParametroDefinition parametroDefinition = new ParametroDefinition();
			parametroDefinition.onCreate(db);
			
			CopoTomadoDefinition copoTomadoDefinition = new CopoTomadoDefinition();
			copoTomadoDefinition.onCreate(db);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try{
			ParametroDefinition parametroDefinition = new ParametroDefinition();
			parametroDefinition.onUpgrade(db, oldVersion, newVersion);
			
			CopoTomadoDefinition copoTomadoDefinition = new CopoTomadoDefinition();
			copoTomadoDefinition.onUpgrade(db, oldVersion, newVersion);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
