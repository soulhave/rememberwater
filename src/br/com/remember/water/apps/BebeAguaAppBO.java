package br.com.remember.water.apps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import br.com.remember.water.activity.RememberWaterBebaAguaActivity;
import br.com.remember.water.bean.InformacoesProcessamentoDTO;
import br.com.remember.water.bean.ParametroBean;
import br.com.remember.water.utilitarios.Utilitarios;

public class BebeAguaAppBO  {


	private static final String TAG = "BebeAguaAppBO";
	
	/**
	 * Busca as informações para serem processadas.
	 * @param bean
	 * @throws ParseException
	 */
	public InformacoesProcessamentoDTO recuperarInformacoesProcessamento(ParametroBean bean) throws ParseException {
		//Informações importantes inicadas
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Utilitarios.PATTERN_HORA);
		
		//Vars usadas
		long horaAtual = Utilitarios.retornaHoraAtualMilesegundos(simpleDateFormat);
		long horaAcordar = 0;
		long horaDormir = 0;
		double coposDia = 0;
		double tempoUtil = 0;
		double intervalo = 0;
		
		try {
			horaAcordar = simpleDateFormat.parse(bean.getDataFinalSono()).getTime();
			horaDormir = simpleDateFormat.parse(bean.getDataIncioSono()).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Verifica primeiro qual horário incial;
		if(horaAtual>horaAcordar)
			horaAcordar = horaAtual;

		String acordar = simpleDateFormat.format(new Date(horaAcordar));
		System.out.println("Hora Inicio: "+acordar);
		
		//Horário fim
		String dormir = simpleDateFormat.format(new Date(horaDormir));
		System.out.println("Hora Fim: "+dormir);
		
		//Litros de água por ML
		coposDia = (bean.getLitrosDia()*1000)/bean.getCoposMlDia();
		System.out.println("Copos/Dia: "+coposDia);
		
		//Tempo Útil
		if(horaAcordar>horaDormir)
			tempoUtil = Math.abs(horaAcordar - horaDormir - 86400000);
		else
			tempoUtil = Math.abs(horaDormir - horaAcordar);
		
		System.out.println("Hora Útil: "+Utilitarios.formataHora(tempoUtil));
		
		//Tempo para cada copo
		intervalo = tempoUtil/coposDia;
		
		System.out.println("Intervalo: "+Utilitarios.formataHora(intervalo));
		
		return new InformacoesProcessamentoDTO(intervalo, acordar, dormir, coposDia);

	}
	
	public void criarScheduler(Activity activity){
		//Create an offset from the current time in which the alarm will go off.
        Calendar cal = Calendar.getInstance();
        PendingIntent pendingIntent = getPedingIntent(activity);
        AlarmManager am = (AlarmManager)activity.getSystemService(Activity.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis()+30000,pendingIntent);
		
        Log.i(TAG,"SCHEDULER REALIZADO");
	}

	/**
	 * 
	 * @param activity
	 * @return
	 */
	private PendingIntent getPedingIntent(Activity activity) {
		Intent intent = new Intent(activity.getApplicationContext(), RememberWaterBebaAguaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(),0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		return pendingIntent;
	}
	
	/**
	 *  
	 * @param activity
	 */
	public void stopScheduler(Activity activity){
        AlarmManager am = (AlarmManager)activity.getSystemService(Activity.ALARM_SERVICE);
        am.cancel(getPedingIntent(activity));
	}

}
