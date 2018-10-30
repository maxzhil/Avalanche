package model;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageWorker {

	private ImageWorker() {

	}

	public static ImageIcon getBackgroundImage(Dimension size) {
		Image image = getImage(Resourcer.getString("images.menu.background"));
		return new ImageIcon(image);
	}

	private static Image getImage(String fileName) {
		File file = new File(Resourcer.getString("library.path") + fileName);
		Image image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
