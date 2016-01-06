package com.bourgadix.services;

import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Variable;

public class SettingsManagement implements SettingsService {

	private DaoService dao=new Dao();
	

	@Override
	public void updateSettings(String name, int startingHour, int endingHour, int firstDay, int lastDay) {
		// TODO Auto-generated method stub
		Variable nom_cabinet=dao.get(Variable.class, 1);
		Variable starting_hour=dao.get(Variable.class, 2);
		Variable ending_hour=dao.get(Variable.class, 3);
		Variable first_day=dao.get(Variable.class, 4);
		Variable last_day=dao.get(Variable.class, 5);
		
		if(!name.contentEquals(nom_cabinet.getValue())){
			nom_cabinet.setValue(name);
			dao.update(nom_cabinet);
			System.err.println(name+" NOT EQUAL "+nom_cabinet.getValue());
		}
		if(!starting_hour.getValue().contentEquals(String.valueOf(startingHour))){
			starting_hour.setValue(String.valueOf(startingHour));
			dao.update(starting_hour);
		}
		if(!ending_hour.getValue().contentEquals(String.valueOf(endingHour))){
			ending_hour.setValue(String.valueOf(endingHour));
			dao.update(ending_hour);
		}
		if(!first_day.getValue().contentEquals(String.valueOf(firstDay))){
			first_day.setValue(String.valueOf(firstDay));
			dao.update(first_day);
		}
		if(!last_day.getValue().contentEquals(String.valueOf(lastDay))){
			last_day.setValue(String.valueOf(lastDay));
			dao.update(last_day);
		}
	}

}
