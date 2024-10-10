package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Videojuego;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class LibrarySearchItemController implements Controller {

    FachadaGUI fg;
    Videojuego game;

    @FXML
    HBox thisHbox;
    @FXML
    ImageView iconImage;
    @FXML
    Label nameLabel;

    public void initializeWindow(Videojuego game)
    {
        this.game = game;
        iconImage.setImage(game.getImagen());
        nameLabel.setText(game.getNombre());
    }


    @FXML
    public void showGameLibraryScene(MouseEvent event)
    {
        fg.showGameLibraryScene(game);
    }

    public void setDisable(boolean b)
    {
        thisHbox.setDisable(b);
    }

    public void setMainApp(FachadaGUI fg)
    {
        this.fg = fg;
    }
}
