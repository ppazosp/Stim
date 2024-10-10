package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Videojuego;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class GameSearchItemController implements Controller {

    FachadaGUI fg;
    Videojuego game;
    MainSearchWController superController;


    @FXML
    ImageView iconImage;
    @FXML
    Label nameLabel;
    @FXML
    HBox iconsHbox;
    @FXML
    Label dateLabel;
    @FXML
    Label priceLabel;

    public void initializeWindow(Videojuego game, MainSearchWController superController)
    {
        this.game = game;
        this.superController = superController;

        iconImage.setImage(game.getImagen());
        nameLabel.setText(game.getNombre());
        fg.showPlatforms(game, iconsHbox);
        dateLabel.setText(game.getFechaSubida().toString());
        priceLabel.setText(game.getPrecio()+"€");
    }

    @FXML
    public void showGameScene ()
    {
        fg.showGameScene(game, superController.lastSearch, 1);
    }

    public void setMainApp(FachadaGUI fg)
    {
        this.fg = fg;
    }
}
