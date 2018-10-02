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
			Field field = KeyEvent.class.getDeclaredField("VK_" + keyName);
			result = field.getInt(field);
		} catch (NoSuchFieldException e) {
		} catch (IllegalAccessException e) {
		} finally {
			return result;
		}
	}

}
