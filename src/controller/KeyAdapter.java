package controller;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import model.Resourcer;

public class KeyAdapter {

	private KeyAdapter() {
	}

	public static int getKeyCode(String keyName) {
		int keyCode = -1;
		try {
			Field field = KeyEvent.class.getDeclaredField(Resourcer
					.getString("key.VK") + keyName);
			keyCode = field.getInt(field);
		} catch (NoSuchFieldException e) {
		} catch (IllegalAccessException e) {
		}
		return keyCode;
	}
}
