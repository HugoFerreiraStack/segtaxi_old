package br.com.seg.econotaxi.enums;

import java.util.Locale;

public enum LocaleEnum {

	PT ("pt", new Locale("pt", "BR")),
	EN ("en", Locale.US),
	ES ("es", new Locale("es", "ES"));
	
	private String key;
	private Locale locale;
	
	private LocaleEnum(String key, Locale locale) {
		this.key = key;
		this.locale = locale;
	}
	
	public String getKey() {
		return key;
	}
	
	public Locale getLocale() {
		return locale;
	}
	
}