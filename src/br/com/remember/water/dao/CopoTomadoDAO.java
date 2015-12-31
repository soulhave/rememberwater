package br.com.remember.water.dao;

import java.io.Serializable;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import br.com.remember.water.bean.CopoTomadoBean;

import android.database.sqlite.SQLiteDatabase;


public class CopoTomadoDAO extends DroidDao<CopoTomadoBean, Serializable>{

	public CopoTomadoDAO(Class<CopoTomadoBean> model, TableDefinition<CopoTomadoBean> tableDefinition, SQLiteDatabase database) {
		super(model, tableDefinition, database);
	}
	public CopoTomadoDAO(TableDefinition<CopoTomadoBean> tableDefinition, SQLiteDatabase database) {
		super(CopoTomadoBean.class, tableDefinition, database);
	}
}