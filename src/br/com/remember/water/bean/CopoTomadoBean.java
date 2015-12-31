package br.com.remember.water.bean;

import java.sql.Date;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;

@Table(name = "copotomado")
public class CopoTomadoBean {
	@PrimaryKey
	@Column(name="id")
	private long id;
	
	@Column(name="data")
	private Date data;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}