package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Usuario;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SocialSearchItemController implements Controller {

    FachadaGUI fg;
    Usuario user;
    SocialWController superController;

    @FXML
    HBox userHbox;
    @FXML
    ImageView iconImage;
    @FXML
    Label nameLabel;

    @FXML
    HBox buttonsHbox;
    @FXML
    VBox deleteVbox;
    @FXML
    VBox addVbox;

    public void initializeWindow(Usuario user, int opt, SocialWController superController)
    {
        this.user = user;
        this.superController = superController;
        iconImage.setImage(user.getFotoPerfil());
        nameLabel.setText(user.getId());
        if(opt == 0) buttonsHbox.getChildren().remove(deleteVbox);
        else buttonsHbox.getChildren().remove(addVbox);
    }


    @FXML
    public void followUser(MouseEvent event)
    {
        fg.loading();

        new Thread(() -> {
            fg.fa.seguir(fg.fa.usuario, user);

            Platform.runLater(() -> {
                superController.load();

                fg.loaded();
            });
        }).start();
    }

    @FXML
    public void unfollowUser(MouseEvent event)
    {
        fg.loading();

        new Thread(() -> {
            fg.fa.dejarSeguir(fg.fa.usuario, user);

            Platform.runLater(() -> {
                superController.load();

                fg.loaded();
            });
        }).start();
    }

    public void setMainApp(FachadaGUI fg)
    {
        this.fg = fg;
    }
}
