package br.com.remember.water.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.remember.water.R;
import br.com.remember.water.apps.AppDataService;
import br.com.remember.water.utilitarios.Utilitarios;

public class RememberWaterBebaAguaActivity extends Activity implements OnKeyListener{

	public static final String TAG = "RememberWaterBebaAguaActivity";
	private Vibrator vibrate;
	private AppDataService appService;
	private AnimationDrawable animation;
	private ImageButton imageAnim;
	private MediaPlayer playBeep;
	private PowerManager.WakeLock wl;
	private KeyguardLock lock;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	// instancia o service
    	appService = (AppDataService) getApplication(); //Dados do banco de dados
    	
    	KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE); 
    	lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE); 
    	
    	/*Coloca a aplicação como full screen*/
    	requestWindowFeature(Window.FEATURE_NO_TITLE); 
    	
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | 
    			WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | 
    			WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | 
    			WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
    			WindowManager.LayoutParams.FLAG_FULLSCREEN | 
    			WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | 
    			WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | 
    			WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    	
    	lock.disableKeyguard();
    	
    	//Configura o layout
    	setContentView(R.layout.bebagua);
    	
    	ImageView imageView = (ImageView)findViewById(R.id.dwBebaAgua);
    	imageView.setOnKeyListener(this);
    	
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	Log.i(TAG,"Down");
    	switch (keyCode) {
    	case KeyEvent.KEYCODE_POWER:
    		finalizarLembrete();
    	}
    	return super.onKeyDown(keyCode, event);
    }
    
    @Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
    	Log.i(TAG,"Up");
    	switch (keyCode) {
    	case KeyEvent.KEYCODE_POWER:
    		finalizarLembrete();
    	}
    	return super.onKeyUp(keyCode, event);
	}
   
    
    /**
     * Quando inciar realiza barulho e vibração
     */
    @Override
    protected void onStart() {
    	super.onStart();
    	
    	//Recuperando imagem.
    	imageAnim = (ImageButton) findViewById(R.id.dwBebaAgua);
    	imageAnim.setOnClickListener(new EncerraAlarme());
    	animateBebaAgua(imageAnim);
    	
    	//Toca beep em modo de loop
    	playBeep = Utilitarios.playBeep(this, R.raw.beep, true);
    	vibrate = Utilitarios.vibrate(this);
    	
    }

	@Override
	protected void onStop() {
		super.onStop();
		finalizarLembrete();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
	
    
	/**
	 * Animação
	 * @param imageAnim 
	 */
    private void animateBebaAgua(ImageButton imageAnim) {
		//Criando animação.
    	animation = new AnimationDrawable();
		animation.addFrame(getResources().getDrawable(R.drawable.agua_amarelo_vermelho), 100);
		animation.addFrame(getResources().getDrawable(R.drawable.agua_vermelho_amarelo), 100);
		animation.setOneShot(false);
		
		//Preparando os dados
		imageAnim.setBackgroundDrawable(animation);
		imageAnim.post(new Runnable() {
			@Override
			public void run() {
				animation.start();
			}
		});
	}
    
    @Override
    public void onBackPressed() {
    	finalizarLembrete();
    }

	private void finalizarLembrete() {
		appService.getBebeAguaAppBO().criarScheduler(this);
		Toast.makeText(RememberWaterBebaAguaActivity.this, "Bebeu Água!", Toast.LENGTH_SHORT).show();
		if(vibrate!=null){
			vibrate.cancel();
		}
    	imageAnim.setBackgroundResource(R.drawable.agua_azul);
    	if(playBeep!=null){
    		playBeep.stop();
    	}

    	moveTaskToBack(true);
		lock.reenableKeyguard();

    	finish();
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
	 * Processa quando bebeu água
	 */
	private void processaBeberAgua() {	
		finalizarLembrete();
	}

	/**
	 * Class de evento de encerrar alarme
	 * @author ramon
	 *
	 */
	private final class EncerraAlarme implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			processaBeberAgua();
		}
	}


	public AnimationDrawable getAnimation() {
		return animation;
	}

	public void setAnimation(AnimationDrawable animation) {
		this.animation = animation;
	}

	public ImageButton getImageAnim() {
		return imageAnim;
	}

	public void setImageAnim(ImageButton imageAnim) {
		this.imageAnim = imageAnim;
	}

	public MediaPlayer getPlayBeep() {
		return playBeep;
	}

	public void setPlayBeep(MediaPlayer playBeep) {
		this.playBeep = playBeep;
	}

	public Vibrator getVibrate() {
		return vibrate;
	}

	public void setVibrate(Vibrator vibrate) {
		this.vibrate = vibrate;
	}


	public PowerManager.WakeLock getWl() {
		return wl;
	}


	public void setWl(PowerManager.WakeLock wl) {
		this.wl = wl;
	}


	public AppDataService getAppService() {
		return appService;
	}

	public void setAppService(AppDataService appService) {
		this.appService = appService;
	}

	public KeyguardLock getLock() {
		return lock;
	}

	public void setLock(KeyguardLock lock) {
		this.lock = lock;
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		switch(keyCode){
			case KeyEvent.KEYCODE_POWER:
				Log.i(TAG,"Tecla Power Press");
		}
		return false;
	}
}