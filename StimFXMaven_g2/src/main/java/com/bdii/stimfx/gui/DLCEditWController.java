package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.DLC;
import com.bdii.stimfx.aplicacion.Videojuego;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DLCEditWController implements Controller {
    FachadaGUI fg;
    DLC dlc;
    Stage window;
    Videojuego game;

    @FXML
    TextField nameField;
    @FXML
    TextField priceField;


    public void initializeWindow(DLC dlc, Videojuego game, Stage window) {

        this.window = window;
        this.game = game;

        if(dlc != null)
        {
            this.dlc = dlc;

            nameField.setText(dlc.getNombre());
            priceField.setText(String.valueOf(dlc.getPrecio()));
        }

    }

    @FXML
    public void publishEdit(MouseEvent event) {

        this.window.close();

        DLC d = new DLC(
                game.getId(),
                (dlc != null) ? dlc.getIdDLC() : -1,
                nameField.getText(),
                Double.parseDouble(priceField.getText()));

        fg.fa.publicarDLC(d);

        fg.showEditGameWindow(game);
    }


    @FXML
    public void showCommunityScene(MouseEvent event) {
        fg.showCommunityScene();
    }

    @FXML
    public void showSettingsScene(MouseEvent event) {
        fg.showSettingsScene();
    }

    @FXML
    public void showAdminScene(MouseEvent event) {

    }

    public void setMainApp(FachadaGUI mainApp) {
        this.fg = mainApp;
    }
}
