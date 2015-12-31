package br.com.remember.water.bean.definition;

import org.droidpersistence.dao.TableDefinition;

import br.com.remember.water.bean.ParametroBean;

public class ParametroDefinition extends TableDefinition<ParametroBean>{

	public ParametroDefinition(Class<ParametroBean> model) {
		super(model);
	}
	public ParametroDefinition() {
		super(ParametroBean.class);
	}
	
}