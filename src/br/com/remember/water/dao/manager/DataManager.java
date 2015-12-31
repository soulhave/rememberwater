package br.com.remember.water.dao.manager;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import br.com.remember.water.bean.ParametroBean;
import br.com.remember.water.bean.definition.CopoTomadoDefinition;
import br.com.remember.water.bean.definition.ParametroDefinition;
import br.com.remember.water.dao.CopoTomadoDAO;
import br.com.remember.water.dao.ParametroDAO;

public class DataManager {

	private static final String PATH_BANCO = "/data/br.com.rememberwaterdb/databases/rememberwaterdb.db";
	private static final String REMEMBER_WATER = "REMEMBERWATER";
	private static final String TAG = "DataManager";
	private Context context;
	private SQLiteDatabase database;
	private ParametroDAO parametroDAO;
	private CopoTomadoDAO copoTomadoDAO;
	
	public DataManager(Context context){
		setContext(context);
		SQLiteOpenHelper openHelper = new OpenHelper(context, REMEMBER_WATER, null, 2);
		setDatabase(openHelper.getWritableDatabase());
		
		this.copoTomadoDAO = new CopoTomadoDAO(new CopoTomadoDefinition(), database);
		this.parametroDAO = new ParametroDAO(new ParametroDefinition(), database);		
	}
	
	private void openDb(){
		if(!getDatabase().isOpen()){
			setDatabase(SQLiteDatabase.openDatabase(Environment.getDataDirectory() + PATH_BANCO, null, SQLiteDatabase.OPEN_READWRITE));
		}
	}
	
	private void closeDb() {
		if (getDatabase().isOpen()) {
			getDatabase().close();
	    }
	}

	/**
	 * Retorna lista com os parametros.
	 * @return
	 */
	public List<ParametroBean> getListParametro(){
		return parametroDAO.getAll();
	}
	
	/**
	 * Retorna parametro
	 * @return
	 */
	public ParametroBean getParametro(){
		List<ParametroBean> all = parametroDAO.getAll();
		return (all!=null && !all.isEmpty()?all.get(0):null);
	}
	
	/**
	 * Insere o parametros
	 * @param bean
	 * @return
	 */
	public boolean insereParametro(ParametroBean bean){
		boolean result = false;
		try{
			//getDatabase().beginTransaction();
			Log.i(TAG,"TRANSA플O INCIADA, INSERIR PARAMETRO.");
			parametroDAO.save(bean);
			//getDatabase().endTransaction();
			result = true;
			Log.i(TAG,"TRANSA플O CONCLUIDA COM SUCESSO, INSERIR PARAMETRO.");
		}catch (Exception e) {
			e.getMessage();
		}
		
		return result;
	}
	public boolean deleteParametro(ParametroBean bean){
		boolean result = false;
		try{
			//getDatabase().beginTransaction();
			Log.i(TAG,"TRANSA플O INCIADA, DELETAR PARAMETRO.");
			parametroDAO.delete(bean.getId());
			//getDatabase().endTransaction();
			result = true;
			Log.i(TAG,"TRANSA플O CONCLUIDA COM SUCESSO, DELETAR PARAMETRO.");
		}catch (Exception e) {
			e.getMessage();
		}
		
		return result;
	}
	public boolean updateParametro(ParametroBean bean){
		boolean result = false;
		try{
			//getDatabase().beginTransaction();
			Log.i(TAG,"TRANSA플O INCIADA, UPDATE PARAMETRO.");
			parametroDAO.update(bean, bean.getId());
			//getDatabase().endTransaction();
			result = true;
			Log.i(TAG,"TRANSA플O CONCLUIDA COM SUCESSO, UPDATE PARAMETRO.");
		}catch (Exception e) {
			e.getMessage();
		}
		
		return result;
	}
	
	private void resetDb() {      
		closeDb();
	    SystemClock.sleep(500);
	    openDb();
	}
	
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public SQLiteDatabase getDatabase() {
		return database;
	}
	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}

}
