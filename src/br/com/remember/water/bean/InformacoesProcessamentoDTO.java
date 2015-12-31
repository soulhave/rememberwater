package br.com.remember.water.bean;

/**
 * Dto com todas as informações necessárias para o processamento do service
 * @author ramon
 *
 */
public class InformacoesProcessamentoDTO {
	private Double frequencia;
	private String horaInicio;
	private String horaFim;
	private Double coposTomados;

	public InformacoesProcessamentoDTO(Double frequencia, String horaInicio, String horaFim, Double coposTomados) {
		super();
		this.frequencia = frequencia;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.coposTomados = coposTomados;
	}
	
	public InformacoesProcessamentoDTO() {}

	public Double getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(Double frequencia) {
		this.frequencia = frequencia;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}

	public Double getCoposTomados() {
		return coposTomados;
	}

	public void setCoposTomados(Double coposTomados) {
		this.coposTomados = coposTomados;
	}
}