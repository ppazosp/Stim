package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Plataforma;
import com.bdii.stimfx.aplicacion.Videojuego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class PlatformItemController implements Controller {

    FachadaGUI fg;
    Plataforma plat;
    Videojuego game;

    @FXML
    CheckBox checkBox;


    public void initializeWindow(Plataforma plat, Videojuego game) {
        this.plat = plat;
        this.game = game;

        checkBox.setText(plat.getNombre());

        if(game != null)
        {
            if(fg.fa.hasPlataforma(game, plat)) checkBox.setSelected(true);
        }
    }

    @FXML
    public void checkOnAction(ActionEvent event)
    {
        if(game != null)
        {
            if(checkBox.isSelected() && !(fg.fa.hasPlataforma(game, plat))) fg.fa.insertarPlataformaVideojuego(plat, game);
            else if(!(checkBox.isSelected()) && fg.fa.hasPlataforma(game, plat)) fg.fa.borrarPlataformaVideojuego(plat, game);
        }
    }

    public void setMainApp(FachadaGUI fg) {
        this.fg = fg;
    }
}
