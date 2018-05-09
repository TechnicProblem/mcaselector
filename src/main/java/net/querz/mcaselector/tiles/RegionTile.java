package net.querz.mcaselector.tiles;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import net.querz.mcaselector.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class RegionTile extends Tile {

	public RegionTile(Point2i location) {
		//pass block with lowest x / z coordinate
		super(512, Helper.regionToBlock(Helper.blockToRegion(location)));
	}

	@Override
	public void loadImage() {
		Point2i p = Helper.blockToRegion(getLocation());
		String res = String.format("out/r.%d.%d.png", p.getX(), p.getY());
		System.out.println("loading region from cache: " + res);
		InputStream resourceStream = RegionTile.class.getClassLoader().getResourceAsStream(res);
		if (resourceStream == null) {
			System.out.println("resource " + res + " not cached, generating image...");
			MCALoader loader = new MCALoader();
			File file = new File("src/main/resources/r." + p.getX() + "." + p.getY() + ".mca");
			MCAFile mcaFile = loader.read(file);

			BufferedImage bufferedImage = mcaFile.createImage(new Anvil112ChunkDataProcessor(), new Anvil112ColorMapping());

			image = SwingFXUtils.toFXImage(bufferedImage, null);

			try {
				ImageIO.write(bufferedImage, "png", new File("src/main/resources/" + res));
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			image = new Image(resourceStream);
		}
	}
}