package com.bourgadix.services;

public interface SettingsService {

	public void updateSettings(String name, int startingHour, int endingHour, int firstDay, int lastDay);
}
