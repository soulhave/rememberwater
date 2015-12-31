package br.com.remember.water.bean.definition;

import org.droidpersistence.dao.TableDefinition;

import br.com.remember.water.bean.CopoTomadoBean;

public class CopoTomadoDefinition extends TableDefinition<CopoTomadoBean>{

	public CopoTomadoDefinition(Class<CopoTomadoBean> model) {
		super(model);
	}
	
	public CopoTomadoDefinition() {
		super(CopoTomadoBean.class);
	}
	
}