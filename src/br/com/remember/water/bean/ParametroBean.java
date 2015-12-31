package br.com.remember.water.bean;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;

@Table(name = "parametro")
public class ParametroBean {

	public ParametroBean(Double litrosDia, Double coposDia, String idioma, String dataIncioSono, String dataFinalSono) {
		super();
		this.litrosDia = litrosDia;
		this.coposMlDia = coposDia;
		this.idioma = idioma;
		this.dataIncioSono = dataIncioSono;
		this.dataFinalSono = dataFinalSono;
	}

	public ParametroBean() {
	
	}
	
	@PrimaryKey
	@Column(name="id")
	private long id;
	
	@Column(name="litrosdia")
	private Double litrosDia;

	@Column(name="coposdia")
	private Double coposMlDia;
	
	@Column(name="idioma")
	private String idioma;
	
	@Column(name="dataIncioSono")
	private String dataIncioSono;

	@Column(name="dataFinalSono")
	private String dataFinalSono;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getLitrosDia() {
		return litrosDia;
	}

	public void setLitrosDia(Double litrosDia) {
		this.litrosDia = litrosDia;
	}


	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getDataFinalSono() {
		return dataFinalSono;
	}

	public void setDataFinalSono(String dataFinalSono) {
		this.dataFinalSono = dataFinalSono;
	}

	public String getDataIncioSono() {
		return dataIncioSono;
	}

	public void setDataIncioSono(String dataIncioSono) {
		this.dataIncioSono = dataIncioSono;
	}

	public Double getCoposMlDia() {
		return coposMlDia;
	}

	public void setCoposMlDia(Double coposMlDia) {
		this.coposMlDia = coposMlDia;
	}
}