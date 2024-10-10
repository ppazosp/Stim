package com.bdii.stimfx.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class LoadingWController implements Controller {
    FachadaGUI fg;

    @FXML
    HBox comMenu;
    @FXML
    HBox editMenu;
    @FXML
    HBox adminMenu;


    public void initializeWindow()
    {
        if(!(fg.fa.usuario.isCompetitivePlayer())) comMenu.setVisible(false);
        if(!(fg.fa.usuario.isEditor())) editMenu.setVisible(false);
        if(!(fg.fa.usuario.isAdmin())) adminMenu.setVisible(false);
    }

    public void setMainApp(FachadaGUI mainApp) {
        this.fg = mainApp;
    }
}
