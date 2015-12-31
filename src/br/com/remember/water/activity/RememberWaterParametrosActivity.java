package br.com.remember.water.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import br.com.remember.water.R;
import br.com.remember.water.apps.AppDataService;
import br.com.remember.water.bean.ParametroBean;
import br.com.remember.water.dao.manager.DataManager;
import br.com.remember.water.utilitarios.Utilitarios;

public class RememberWaterParametrosActivity extends Activity {

	private static final int SALVAR = 0; 
	private static final int VOLTAR = 1; 
	private static final int EXIT = 2; 
	
	private static String TAG = "RememberWaterParametrosActivity";
	
	private AppDataService appService;
	private DataManager dataManager;
	private ParametroBean parametro;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	// instancia o service
    	appService = (AppDataService) getApplication(); //Dados do banco de dados
    	
    	/*Coloca a aplicação como full screen*/
    	requestWindowFeature(Window.FEATURE_NO_TITLE); 
    	
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
    			WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
    	setContentView(R.layout.parametros);
    	
    	/*Busca parametros do banco*/
    	dataManager = appService.getDataManager();
    	parametro = dataManager.getParametro();
    	
    	/**/
    	if(parametro!=null){
    		/* Preenchendo dados do combo Litros*/
    		comboLitrosAgua(parametro.getLitrosDia());

    		/*Data Inicial*/
    		TimePicker horaInicial = (TimePicker) findViewById(R.id.timerInicio);
    		setDataHora(parametro.getDataIncioSono(), horaInicial);
    		
    		/*Data Final*/
    		TimePicker horaFinal = (TimePicker) findViewById(R.id.timerFinal);
    		setDataHora(parametro.getDataFinalSono(), horaFinal);
    		
    		/* Preenchendo dados do combo Copo*/
    		comboMlAguaCopo(parametro.getCoposMlDia());
    	}else{
			showAlertMessage("Paramêtros","Paramêtros não encontrados. A aplicação será finalizada! Caso o problema persista entre em contato com o fornecedor do produto.", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//Após exibir o alerta toda aplicação deverá ser encerrada.
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			});
    	}
    }
	
    @Override
    public void onBackPressed() {
    	showYesNoMessage("Confirma", "Deseja sair sem salvar?", new OnClickListener() {
			//Positivo
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish(); //Caso sim somente finaliza a activity
			}
		}, null); //Caso negativo continua na tela.
    }
    
    /**
     * 
     * @param hora
     * @param horaInicial
     */
    private void setDataHora(String hora, TimePicker horaPicker) {
    	horaPicker.setIs24HourView(true);
    	horaPicker.setCurrentHour(Integer.parseInt(hora.substring(0,2)));
    	horaPicker.setCurrentMinute(Integer.parseInt(hora.substring(3,5)));
	}
    /**
     * Mensagem de alerta a ser exibida
     * @param titulo
     * @param Messagem
     */
	public void showAlertMessage(String titulo, String Messagem, OnClickListener onClickListener) {
		new AlertDialog.Builder(this).setTitle(titulo).setMessage(Messagem).setPositiveButton(Utilitarios.OK, onClickListener).show();
	}


	/**
	 * Exibe mensagem de confirmação Sim Não, 
	 * @param titulo - Titulo da mensagem
	 * @param Messagem - Mensagem
	 * @param onClickListenerPositive - Evento que deverá ocorrer se positivo
	 * @param onClickListenerNegative - Evento que deverá ocorrer se negativo
	 */
	public void showYesNoMessage(String titulo, String Messagem, OnClickListener onClickListenerPositive,OnClickListener onClickListenerNegative) {
		new AlertDialog.Builder(this).setTitle(titulo).setMessage(Messagem).setPositiveButton(Utilitarios.SIM, onClickListenerPositive).setNegativeButton(Utilitarios.NAO, onClickListenerNegative).show();
	}

	/**
	 * Combo Litros d'agua
	 * @param litros 
	 */
    private void comboLitrosAgua(Double litros) {
		String[] items = new String[] {"1.0L", "1.5L", "2.0L","2.5L", "3.0L", "3.5L", "4.0L", "4.5L", "5.0L"};
    	Spinner spinner = (Spinner) findViewById(R.id.qtdLitros);
    	String litro = litros.toString()+"L";
    	configuraAdapterSpiner(items, spinner, litro);
	}


	/**
	 * Combo de copo d'agua
	 * @param l 
	 */
    private void comboMlAguaCopo(Double coposAguaDia) {
		String[] items = new String[] {"100ML", "200ML", "300ML", "400ML", "500ML"};
		Spinner spinner = (Spinner) findViewById(R.id.qtdCopos);
		String copoDagua = coposAguaDia.toString().substring(0,3)+"ML";
		configuraAdapterSpiner(items, spinner,copoDagua);
	}

	/**
	 * Monta os itens do combo
	 * @param items
	 * @param spinner
	 */
    private void configuraAdapterSpiner(String[] items, Spinner spinner, String locate) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
    	spinner.setSelection(adapter.getPosition(locate));
	}

    
    /**
     * Botões de menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	Utilitarios.montaItenMenu(menu,SALVAR,0,"Salvar",R.drawable.save);
    	Utilitarios.montaItenMenu(menu,VOLTAR,1,"Voltar",R.drawable.back);
    	Utilitarios.montaItenMenu(menu,EXIT,2,"Sair",R.drawable.exit);
    	
    	return super.onCreateOptionsMenu(menu);
    }

    /**
     * Ação dos botões
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	/*Determina qual ação foi selecionada*/
		switch (item.getItemId()) {
			case SALVAR:
				onSalvar(); //Salva os dados
				break;
			case VOLTAR:
				onBackPressed(); //Aciona a chamda da tela anterior
				break;
			case EXIT:
				break; //TODO:Irá fechar aplicação. Pesquisar como.
			default:
				Log.v(TAG, "Default?");
		}
    	return super.onOptionsItemSelected(item);
    }
    
    /**
     * Ação chamada pela activiy. Qualquer validação deverá ser realizada aqui.
     */
    public void onSalvar(){
    	//Salva os dados no banco de dados
    	salvar();
    	//Fecha a tela e volta par atela inicial
    	finish();
    	//Avisa que os dados foram salvos
    	Toast.makeText(this, "Paramêtros Salvos com Sucesso!", Toast.LENGTH_SHORT).show(); 
    }
    
    /**
     * Salva os dados no banco;
     */
	private void salvar() {
		Spinner qtdCopos = (Spinner) findViewById(R.id.qtdCopos);
    	TimePicker horaInicial = (TimePicker) findViewById(R.id.timerInicio);
    	TimePicker horaFinal = (TimePicker) findViewById(R.id.timerFinal);
    	Spinner qtdLitros = (Spinner) findViewById(R.id.qtdLitros);
    	
    	parametro.setCoposMlDia(trataValoresCombo(qtdCopos));
    	parametro.setLitrosDia(trataValoresCombo(qtdLitros));
    	parametro.setDataIncioSono(Utilitarios.complementaZeroEsquerda(horaInicial.getCurrentHour(),2)+":"+Utilitarios.complementaZeroEsquerda(horaInicial.getCurrentMinute(),2));
    	parametro.setDataFinalSono(Utilitarios.complementaZeroEsquerda(horaFinal.getCurrentHour(),2)+":"+Utilitarios.complementaZeroEsquerda(horaFinal.getCurrentMinute(),2));
    	
    	dataManager.updateParametro(parametro);
	}

	/**
	 * Trata valores do combo para salvar no banco.
	 * @param qtdCopos
	 * @return
	 */
    private Double trataValoresCombo(Spinner qtdCopos) {
		String valorSelectd = (String)qtdCopos.getSelectedItem();
    	valorSelectd = Utilitarios.onlyNumbers(valorSelectd);
    	return Double.parseDouble(valorSelectd);
	}
}