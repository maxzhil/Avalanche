package model;

import java.util.ResourceBundle;

public class Resourcer {

	private final static ResourceBundle resourceBundle = ResourceBundle
			.getBundle("resources.settings");

	private Resourcer() {
	}

	public static String getString(String key) {
		return resourceBundle.getString(key);
	}
}
