package br.com.remember.water.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import br.com.remember.water.apps.AppDataService;
import br.com.remember.water.utilitarios.Utilitarios;

public class RememberWaterService extends Service {
	
	public static boolean EXECUTA_THREAD = false;
	public static int ID_THREAD = 1;
	
	private final IBinder binder = new LocalBinder();
	private AppDataService appService;
	
	@Override
	public void onCreate() {
		super.onCreate();
		appService = (AppDataService)getApplication();
	}
	
	@Override
	public ComponentName startService(Intent service) {
		return super.startService(service);
	}
	
	/**
	 * Inicia o processo de serviço.
	 */
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Utilitarios.notificacaoEmAndamento(this);
	}
	
	
	/**
	 * Fecha o onboing.. 
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		Utilitarios.encerraNotificacao(this);
		EXECUTA_THREAD = false;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	public class LocalBinder extends Binder {
		public RememberWaterService getService() {
            return RememberWaterService.this;
        }
    }

	public AppDataService getAppService() {
		return appService;
	}

	public void setAppService(AppDataService appService) {
		this.appService = appService;
	}

	public IBinder getBinder() {
		return binder;
	}

}
