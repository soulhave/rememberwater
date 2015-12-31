package br.com.remember.water.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import br.com.remember.water.R;
import br.com.remember.water.apps.AppDataService;
import br.com.remember.water.bean.ParametroBean;
import br.com.remember.water.dao.manager.DataManager;
import br.com.remember.water.service.RememberWaterService;
import br.com.remember.water.utilitarios.Utilitarios;

public class RememberWaterMainActivity extends Activity {

	private static final int START = 0; 
	private static final int PARAMETROS = 1; 
	private static final int EXIT = 2; 
	private static String TAG = "RememberWaterMainActivity";
	private AppDataService appService;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	Log.i(TAG,"OnCreate - PID:"+android.os.Process.myPid()+" TID:"+android.os.Process.myTid()+" "+android.os.Process.myUid());
    	appService = (AppDataService) getApplication(); //Dados do banco de dados
    	
    	/*Coloca a aplicação como full screen*/
    	requestWindowFeature(Window.FEATURE_NO_TITLE); 
    	
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
    			WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
    	setContentView(R.layout.main);
    	
    	/*Verifica se já existe registro de parametros. Se não coloca dados iniciais*/
		DataManager dataManager = appService.getDataManager();
		if (dataManager.getListParametro() == null || dataManager.getListParametro().isEmpty()) {
			ParametroBean bean = new ParametroBean(2D, 300D, "PT", "22:00:00", "08:00:00"); //Valores padrões dos parametros.
			dataManager.insereParametro(bean);
			dataManager.getListParametro();
    	}
		
    }

    @Override
    public void onBackPressed() {
    	Log.i(TAG,"onBackPressed Acionado!");
    }
    
    /**
     * Botões de menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	Utilitarios.montaItenMenu(menu,START,0,Utilitarios.INICIAR,R.drawable.on);
    	Utilitarios.montaItenMenu(menu,PARAMETROS,1,"Configurações",R.drawable.param);
    	Utilitarios.montaItenMenu(menu,EXIT,2,"Sair",R.drawable.exit);
    	
    	MenuItem itemStart = menu.getItem(START);
		//Verifica se o serviço está processando e configura os dados do menu caso necessário. 
		if(RememberWaterService.EXECUTA_THREAD){
			modificaIcone(itemStart,R.drawable.off,Utilitarios.PARAR);
		}else{
			modificaIcone(itemStart,R.drawable.on,Utilitarios.INICIAR);
		}
		
    	return super.onCreateOptionsMenu(menu);
    }

    /**
     * Ação dos botões
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	/*Determina qual ação foi selecionada*/
		switch (item.getItemId()) {
			case START:
				onInicio(item);
				Log.v(TAG, "Clicou em START");
				break;
			case PARAMETROS:
				startActivity(new Intent(this, RememberWaterParametrosActivity.class));
				Log.v(TAG, "Clicou em PARAMETROS");
				break;
			case EXIT:
//				startActivity(new Intent(this, RememberWaterBebaAguaActivity.class));
				onExit();
				Log.v(TAG, "Clicou em EXIT");
				break;
			default:
				Log.v(TAG, "Default?");
		}
    	return super.onOptionsItemSelected(item);
    }
    /**
     * Exit on screen and service
     * @throws  
     */
    private void onExit() {
    	stopService(new Intent(this, RememberWaterService.class));
		Toast.makeText(this, "Finalizando todos os processos.", Toast.LENGTH_SHORT).show();
		finish();		
	}

    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	android.os.Process.killProcess(android.os.Process.myPid());
    }
    
    /**
     * Inicio do processo.
     * @param item 
     */
	private void onInicio(MenuItem item) {
		if(RememberWaterService.EXECUTA_THREAD){ //Está executando, será necessário parar o processo e voltar o botão para inciar.
			changeItemStart(item,R.drawable.on,Utilitarios.INICIAR);
			appService.getBebeAguaAppBO().stopScheduler(this);
			stopService(new Intent(this, RememberWaterService.class));
		}else{ //Incia o processo e coloca o botão configurado para parar quando solicitado.
			changeItemStart(item,R.drawable.off,Utilitarios.PARAR);
			startService(new Intent("INICIAR_SERVICO"));
			moveTaskToBack(true);
			//Cria scheduller
			appService.getBebeAguaAppBO().criarScheduler(this);
			Toast.makeText(this, "Em execução. A aplicação poder ser acessada a qualquer momento na barra superior", Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Altera os dados do menu
	 * @param item menu
	 * @param icone icone
	 * @param label label
	 */
	private void changeItemStart(MenuItem item, int icone,String label) {
		RememberWaterService.EXECUTA_THREAD=!RememberWaterService.EXECUTA_THREAD;
		modificaIcone(item, icone, label);
	}
	
    /**
     * Conected the server
     * @param className
     * @param service
     */
    
	private void modificaIcone(MenuItem item, int icone, String label) {
		item.setIcon(icone);
		item.setTitle(label);
	}

	public AppDataService getAppService() {
		return appService;
	}

	public void setAppService(AppDataService appService) {
		this.appService = appService;
	}
}