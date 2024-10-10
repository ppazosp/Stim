package com.bdii.stimfx.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadGameWController implements Controller {
    FachadaGUI fg;

    @FXML
    WebView gameLoaded;

    public void initializeWindow(String url) {

        gameLoaded.getEngine().setJavaScriptEnabled(true);
        gameLoaded.getEngine().load(url);
    }

    public void setMainApp(FachadaGUI mainApp)
    {
        this.fg = mainApp;
    }
}
