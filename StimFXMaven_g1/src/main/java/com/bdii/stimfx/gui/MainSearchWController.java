package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Videojuego;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class MainSearchWController implements Controller {

    FachadaGUI fg;

    String lastSearch;



    @FXML
    TextField searchBar;
    @FXML
    Label resultsLabel;
    @FXML
    VBox searchVbox;


    public void setSearchBar(String text) {
        searchBar.setText(text);
    }

    @FXML
    public void showSearchResults()
    {
        fg.loading();

        new Thread(() -> {
            List<Videojuego> gamesList = fg.fa.consultarVideojuegos(searchBar.getText());

            Platform.runLater(() -> {

                searchVbox.getChildren().clear();
                lastSearch = searchBar.getText();
                searchBar.clear();
                resultsLabel.setText("Resultados para \"" + lastSearch + "\"");


                try {
                    for (Videojuego v : gamesList) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bdii/stimfx/gui/gameSearchItem.fxml"));
                        searchVbox.getChildren().add(loader.load());

                        GameSearchItemController controller = loader.getController();
                        controller.setMainApp(fg);
                        controller.initializeWindow(v, this);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                fg.loaded();
            });
        }).start();
    }
    @FXML
    public void showMainScene(MouseEvent event)
    {
        fg.showMainWindow(false);
    }

    @FXML
    public void showProfileScene(MouseEvent event)
    {
        fg.showProfileScene();
    }

    @FXML
    public void showLibraryScene(MouseEvent event)
    {
        fg.showLibraryScene();
    }

    @FXML
    public void showSocialScene(MouseEvent event)
    {
        fg.showSocialScene();
    }

    public void setMainApp(FachadaGUI mainApp)
    {
        this.fg = mainApp;
    }
}