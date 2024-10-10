package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.DLC;
import com.bdii.stimfx.aplicacion.Videojuego;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;

public class LibraryGameWController implements Controller{
    FachadaGUI fg;
    Videojuego game;

    @FXML
    ImageView bannerImage;
    @FXML
    Label nameLabel;
    @FXML
    Label dateLabel;
    @FXML
    TextArea descrpArea;
    @FXML
    VBox dlcVbox;
    @FXML
    VBox extraContentVbox;
    @FXML
    VBox gamesVbox;

    public void initializeWindow(Videojuego game)
    {
        fg.loading();

        this.game = game;

        new Thread(() -> {

            List<Videojuego> userGames = fg.fa.consultarVideojuegosUsuario(fg.fa.usuario.getId());
            List<DLC> dlcList = fg.fa.consultarDLCsVideojuegoUsuario(game, fg.fa.usuario);

            Platform.runLater(() -> {

                gamesVbox.getChildren().clear();
                gamesVbox.setSpacing(2);

                try {
                    for (Videojuego v : userGames) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bdii/stimfx/gui/librarySearchItem.fxml"));
                        gamesVbox.getChildren().add(loader.load());

                        LibrarySearchItemController controller = loader.getController();
                        controller.setMainApp(fg);
                        controller.initializeWindow(v);
                        controller.setDisable(v.getId() == game.getId());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                bannerImage.setImage(game.getBanner());
                nameLabel.setText(game.getNombre());
                //CAMBIAR POR FECHA COMPRA
                dateLabel.setText("Fecha de adquisicion: "+game.getFechaSubida().toString());
                descrpArea.setText(game.getDescripcion());

                dlcVbox.getChildren().clear();
                for (DLC d : dlcList) {
                    Label dlcName = new Label(d.getNombre());
                    dlcName.setTextFill(Color.WHITE);
                    dlcVbox.getChildren().add(dlcName);
                }
                if (dlcList.isEmpty()) extraContentVbox.setVisible(false);

                fg.loaded();
            });
        }).start();
    }

    @FXML
    public void returnGame(MouseEvent event)
    {
        fg.loading();

        new Thread(() -> {

            fg.fa.devolverVideojuego(game, fg.fa.usuario);

            Platform.runLater(() -> fg.showLibraryScene());

        }).start();
    }

    @FXML
    public void showGameLibraryScene(MouseEvent event)
    {
        fg.showGameLibraryScene(game);
    }

    @FXML
    public void showProfileScene(MouseEvent event)
    {
        fg.showProfileScene();
    }

    @FXML
    public void showMainScene(MouseEvent event)
    {
        fg.showMainWindow(false);
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
