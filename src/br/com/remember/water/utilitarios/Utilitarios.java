package br.com.remember.water.utilitarios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import br.com.remember.water.R;
import br.com.remember.water.activity.RememberWaterMainActivity;
import br.com.remember.water.service.RememberWaterService;

public class Utilitarios {

	public static final String INICIAR = "Iniciar";
	public static final String PARAR = "Parar";
	public static final String OK = "OK";
	public static final String SIM = "SIM";
	public static final String NAO = "NÃO";

	/**
	 * Monta o menu dos itens
	 * @param menu
	 * @param item
	 * @param ordem
	 * @param label
	 * @param imagem
	 * @return
	 */
    public static MenuItem montaItenMenu(Menu menu, int item, int ordem,String label,int imagem) {
		MenuItem menuItem = menu.add(item,ordem,0,label);
		menuItem.setIcon(imagem);
		return menuItem;
	}
	/**
	 * Somente número e ponto flutuante
	 * @param str
	 * @return
	 */
    public static String onlyNumbers(String str) {
		if (str != null) {
			return str.replaceAll("[^0123456789.]", "");
		} else {
			return "";
		}
	}
    
	public static String complementaZeroEsquerda(int numero, int tamanho) {
		String convertido = String.valueOf(numero);
		for (; convertido.length() < tamanho;)
			convertido = "0" + convertido;
		return convertido;
	}


	/**
	 * Encerra Notificação da tela de tarefas.
	 * @param service
	 */
	public static void encerraNotificacao(Service service){
	    NotificationManager manager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);  
	    manager.cancel(RememberWaterService.ID_THREAD);
	}
	
	/**
	 * Cria notificação na tela de tarefas do Android
	 * @param service
	 */
	public static void notificacaoEmAndamento(Service service) {
		//Vars
		Context context = service.getApplicationContext();
		Notification updateComplete = new Notification(R.drawable.logowater_small, context.getText(R.string.app_name).toString(), System.currentTimeMillis());
		Intent notificationIntent = new Intent(service, RememberWaterMainActivity.class);
		
		//Declara como em execução
		updateComplete.flags = Notification.FLAG_ONGOING_EVENT;
		NotificationManager manager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
		PendingIntent contentIntent = PendingIntent.getActivity(service, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		//Alters
		updateComplete.setLatestEventInfo(context, context.getText(R.string.app_name).toString(), context.getText(R.string.em_execucao).toString(), contentIntent);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
	    // Add as notification
	    manager.notify(RememberWaterService.ID_THREAD, updateComplete);
	}
	
	/**
	 * Vibra o telefone por tempo determinado.
	 * @param activity
	 * @param time
	 * @return 
	 */
	public static Vibrator vibrate(Activity activity){
		Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
		long[] pattern = { 0, 100, 500, 100, 500, 100, 500, 100, 500, 100, 500, 0,100, 500, 100, 500, 100, 500, 100, 500, 100, 500};
		v.vibrate(pattern,12);
		return v;
	}
    
	/**
	 * Toca beep, e retorna player para controle
	 * @param activity
	 * @param sound
	 * @param loop
	 */
	public static MediaPlayer playBeep(Activity activity,int sound, boolean loop) {
		MediaPlayer mediaPlayer = MediaPlayer.create(activity.getApplicationContext(), sound);
		mediaPlayer.setLooping(loop);
//		mediaPlayer.setVolume(0.04f,0.04f);
		mediaPlayer.setVolume(0.60f,0.60f);
    	mediaPlayer.start();
    	
    	return mediaPlayer;
	}
	
	/**
	 * Retorna a hora atual em milesegundos
	 * @param simpleDateFormat
	 * @return
	 * @throws ParseException
	 */
	public static long retornaHoraAtualMilesegundos(SimpleDateFormat simpleDateFormat) throws ParseException {
		return (simpleDateFormat.parse(simpleDateFormat.format(new Date(System.currentTimeMillis())))).getTime();
	}
	
	/**
	 * Retorna hora formatada em milesegundos
	 * @param horaMi
	 * @return
	 */
	public static String formataHora(double horaMi) {
		double l = (double)horaMi/1000d/60d/60d;
		long horad = (long)l;
		l = ((double)l - horad);
		long minuted = (long) (l*60);
		String hora = Long.toString(horad);
		String minute = Long.toString(minuted);
		return complementaZeroEsquerda(Integer.parseInt(hora),2)+":"+complementaZeroEsquerda(Integer.parseInt(minute),2);
	}

	public static final String PATTERN_HORA = "HH:mm";

}