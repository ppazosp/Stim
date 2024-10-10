package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Videojuego;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class EditItemController implements Controller {

    FachadaGUI fg;
    Videojuego game;

    @FXML
    VBox itemVbox;
    @FXML
    ImageView iconImage;
    @FXML
    Label nameLabel;
    @FXML
    Label dateLabel;

    public void initializeWindow(Videojuego game) {
        this.game = game;

        iconImage.setImage(game.getImagen());
        nameLabel.setText(game.getNombre());
        dateLabel.setText(game.getFechaSubida().toString());
    }


    @FXML
    public void editGame(MouseEvent event) {
        fg.showEditGameWindow(game);
    }

    public void setMainApp(FachadaGUI fg) {
        this.fg = fg;
    }
}
