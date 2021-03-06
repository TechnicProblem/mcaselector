package net.querz.mcaselector.ui.dialog;

import javafx.stage.Stage;
import net.querz.mcaselector.io.SelectionInfo;
import net.querz.mcaselector.text.Translation;
import net.querz.mcaselector.tiles.TileMap;

public class ImageExportConfirmationDialog extends ConfirmationDialog {

	public ImageExportConfirmationDialog(TileMap tileMap, SelectionInfo info, Stage primaryStage) {
		super(
				primaryStage,
				Translation.DIALOG_IMAGE_EXPORT_CONFIRMATION_TITLE,
				Translation.DIALOG_IMAGE_EXPORT_CONFIRMATION_HEADER_SHORT,
				"export"
		);

		headerTextProperty().unbind();
		setHeaderText(String.format(Translation.DIALOG_IMAGE_EXPORT_CONFIRMATION_HEADER_VERBOSE.toString(), info.getWidth() * 16, info.getHeight() * 16));
		tileMap.releaseAllKeys();
	}
}
