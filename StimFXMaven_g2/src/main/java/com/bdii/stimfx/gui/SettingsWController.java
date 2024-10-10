package com.bdii.stimfx.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingsWController implements Controller {
    FachadaGUI fg;

    //MENU BAR
    @FXML
    HBox comMenu;
    @FXML
    HBox editMenu;
    @FXML
    HBox adminMenu;


    @FXML
    VBox beEditorVbox;
    @FXML
    Label editorPreLabel;


    @FXML
    VBox bePlayerVbox;
    @FXML
    Label playerPreLabel;


    public void initializeWindow()
    {
        if(!(fg.fa.usuario.isCompetitivePlayer())) comMenu.setVisible(false);
        else
        {
            playerPreLabel.setText("Ya eres");
            bePlayerVbox.setDisable(true);
        }
        if(!(fg.fa.usuario.isEditor())) editMenu.setVisible(false);
        else
        {
            editorPreLabel.setText("Ya eres");
            beEditorVbox.setDisable(true);
        }
        if(!(fg.fa.usuario.isAdmin())) adminMenu.setVisible(false);
    }

    @FXML
    public void beEditor(MouseEvent event)
    {
        fg.loading();

        new Thread(() -> {

            fg.fa.hacerEditor(fg.fa.usuario);
            fg.fa.usuario.setEditor(true);

            Platform.runLater(() ->{
                fg.showSettingsScene();

                fg.loaded();
            });
        }).start();
    }

    @FXML
    public void bePlayer(MouseEvent event)
    {
        fg.loading();

        new Thread(() -> {

            fg.fa.hacerJugadorCompetitivo(fg.fa.usuario);
            fg.fa.usuario.setCompetitivePlayer(true);

            Platform.runLater(() ->{
                fg.showSettingsScene();

                fg.loaded();
            });
        }).start();
    }


    @FXML
    public void showCommunityScene(MouseEvent event)
    {
        fg.showCommunityScene();
    }

    @FXML
    public void showEditScene(MouseEvent event)
    {
        fg.showEditScene();
    }

    @FXML
    public void showAdminScene(MouseEvent event)
    {
        fg.showAdminScene();
    }

    @FXML void showLoginWindow(MouseEvent event)
    {
        fg.showLoginWindow();
    }

    public void setMainApp(FachadaGUI mainApp)
    {
        this.fg = mainApp;
    }
}
