package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class SocialWController implements Controller{

    FachadaGUI fg;


    @FXML
    TextField searchBar;

    @FXML
    Label resultsLabel;

    @FXML
    VBox followersVbox;
    @FXML
    VBox resultsVbox;


    public void load()
    {
        loadFollows();
        loadSearch();
    }

    public void loadFollows()
    {
        fg.loading();

        new Thread(() -> {

            List<Usuario> followers = fg.fa.consultarSeguidos(fg.fa.usuario);

            Platform.runLater(() -> {

                followersVbox.getChildren().clear();

                try {
                    for (Usuario u : followers) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bdii/stimfx/gui/SocialSearchItem.fxml"));
                        followersVbox.getChildren().add(loader.load());

                        SocialSearchItemController controller = loader.getController();
                        controller.setMainApp(fg);
                        controller.initializeWindow(u, 1, this);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                fg.loaded();
            });
        }).start();
    }

    public void loadSearch()
    {
        fg.loading();

        new Thread(() -> {
            List<Usuario> followers = fg.fa.consultarUsuariosNoSeguidos(fg.fa.usuario, searchBar.getText() );

            Platform.runLater(() -> {
                resultsLabel.setText("Resultados para \""+searchBar.getText()+"\"");
                resultsVbox.getChildren().clear();

                try {
                    for (Usuario u : followers) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bdii/stimfx/gui/SocialSearchItem.fxml"));
                        resultsVbox.getChildren().add(loader.load());

                        SocialSearchItemController controller = loader.getController();
                        controller.setMainApp(fg);
                        controller.initializeWindow(u, 0, this);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                fg.loaded();
            });
        }).start();
    }

    @FXML
    public void showSearchResults(ActionEvent event)
    {
        loadSearch();
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
    public void showMainScene(MouseEvent event)
    {
        fg.showMainWindow(false);
    }

    public void setMainApp(FachadaGUI mainApp)
    {
        this.fg = mainApp;
    }

}
