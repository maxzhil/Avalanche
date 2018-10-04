package controller;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import model.Resourcer;

public class KeyAdapter {

	public static int getKeyCode(String resourceName) {
		String keyName = Resourcer.getString(resourceName);
		try {
			return Integer.parseInt(keyName);
		} catch (NumberFormatException e) {
			return getKeyCodeFromKeyEvent(keyName);
		}
	}

	private static int getKeyCodeFromKeyEvent(String keyName) {
		int result = -1;
		try {
			Field field = KeyEvent.class.getDeclaredField(Resourcer
					.getString("key.VK") + keyName);
			result = field.getInt(field);
		} catch (NoSuchFieldException e) {
		} catch (IllegalAccessException e) {
		}
		return result;
	}
}
