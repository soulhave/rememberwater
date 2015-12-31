package br.com.remember.water.dao;

import java.io.Serializable;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import android.database.sqlite.SQLiteDatabase;
import br.com.remember.water.bean.ParametroBean;


public class ParametroDAO extends DroidDao<ParametroBean, Serializable>{

	public ParametroDAO(Class<ParametroBean> model, TableDefinition<ParametroBean> tableDefinition, SQLiteDatabase database) {
		super(model, tableDefinition, database);
	}
	public ParametroDAO(TableDefinition<ParametroBean> tableDefinition, SQLiteDatabase database) {
		super(ParametroBean.class, tableDefinition, database);
	}


}