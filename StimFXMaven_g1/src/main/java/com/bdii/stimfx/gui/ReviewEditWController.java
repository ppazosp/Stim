package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Resenha;
import com.bdii.stimfx.aplicacion.Videojuego;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ReviewEditWController implements Controller {
    FachadaGUI fg;
    //Resenha res;
    Stage window;

    Videojuego game;
    String search;
    int from;

    @FXML
    TextArea comentArea;
    @FXML
    TextField nameField;
    @FXML
    HBox starsHbox;
    @FXML
    ImageView istar1;
    @FXML
    ImageView istar2;
    @FXML
    ImageView istar3;
    @FXML
    ImageView istar4;
    @FXML
    ImageView istar5;

    int val = 5;

    public void initializeWindow(Resenha res, Stage window, Videojuego v, String search, int from) {

        this.window = window;
        this.game = v;
        this.search = search;
        this.from = from;

        /*if(res != null)
        {
            this.res = res;

            comentArea.setText(res.getComentario());

            switch(res.getValoracion())
            {
                case 1:
                    istar2.setVisible(false);
                case 2:
                    istar3.setVisible(false);
                case 3:
                    istar4.setVisible(false);
                case 4:
                    istar5.setVisible(false);
                    break;
                default:
            }
        }*/

    }

    @FXML
    public void starPressed(MouseEvent event)
    {
        HBox star = (HBox)(event.getSource());
        if((star.getChildren().contains(istar1)))
        {
            istar1.setVisible(true);
            istar2.setVisible(false);
            istar3.setVisible(false);
            istar4.setVisible(false);
            istar5.setVisible(false);

            val = 1;
        }else if((star.getChildren().contains(istar2)))
        {
            istar1.setVisible(true);
            istar2.setVisible(true);
            istar3.setVisible(false);
            istar4.setVisible(false);
            istar5.setVisible(false);

            val = 2;
        }else if((star.getChildren().contains(istar3)))
        {
            istar1.setVisible(true);
            istar2.setVisible(true);
            istar3.setVisible(true);
            istar4.setVisible(false);
            istar5.setVisible(false);

            val = 3;
        }else if (star.getChildren().contains(istar4))
        {
            istar1.setVisible(true);
            istar2.setVisible(true);
            istar3.setVisible(true);
            istar4.setVisible(true);
            istar5.setVisible(false);

            val = 4;
        }else if (star.getChildren().contains(istar5))
        {
            istar1.setVisible(true);
            istar2.setVisible(true);
            istar3.setVisible(true);
            istar4.setVisible(true);
            istar5.setVisible(true);

            val = 5;
        }
    }

    @FXML
    public void publishEdit(MouseEvent event) {
        window.close();
        fg.loading();

            new Thread(() -> {
                Resenha r = new Resenha(
                        comentArea.getText(),
                        val,
                        fg.fa.usuario.getId(),
                        game.getId());

                fg.fa.publicarResenha(r);

                Platform.runLater(() -> fg.showGameScene(game, search, from));
            }).start();
    }


    @FXML
    public void showProfileScene(MouseEvent event) {
        fg.showProfileScene();
    }

    @FXML
    public void showLibraryScene(MouseEvent event) {
        fg.showLibraryScene();
    }

    @FXML
    public void showSocialScene(MouseEvent event) {
        fg.showSocialScene();
    }

    @FXML
    public void showMainScene(MouseEvent event) {
        fg.showMainWindow(false);
    }

    public void setMainApp(FachadaGUI mainApp) {
        this.fg = mainApp;
    }
}
