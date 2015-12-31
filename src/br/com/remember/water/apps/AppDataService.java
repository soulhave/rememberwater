package br.com.remember.water.apps;

import android.app.Application;
import br.com.remember.water.dao.manager.DataManager;

public class AppDataService extends Application{
	private DataManager dataManager;
	private BebeAguaAppBO bebeAguaAppBO;

	@Override
	public void onCreate() {
		super.onCreate();
		dataManager = new DataManager(this);
		bebeAguaAppBO = new BebeAguaAppBO();
	}
	
	public DataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	public BebeAguaAppBO getBebeAguaAppBO() {
		return bebeAguaAppBO;
	}

	public void setBebeAguaAppBO(BebeAguaAppBO bebeAguaAppBO) {
		this.bebeAguaAppBO = bebeAguaAppBO;
	}
}