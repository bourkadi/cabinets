package com.bourgadix.services;

public interface AccessService {

	public Boolean canUpdateClient(int u);

	public Boolean canUpdateMedicament(int u);


	public Boolean canUpdateRdv(int u);

	public Boolean canUpdateUsers(int u);

}
