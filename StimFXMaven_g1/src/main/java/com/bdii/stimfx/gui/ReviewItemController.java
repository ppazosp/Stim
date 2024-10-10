package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Resenha;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ReviewItemController implements Controller {

    FachadaGUI fg;
    Resenha res;

    @FXML
    TextArea reviewArea;
    @FXML
    ImageView iconImage;
    @FXML
    Label nameLabel;


    @FXML
    HBox starsHbox;
    @FXML
    HBox star1;
    @FXML
    HBox star2;
    @FXML
    HBox star3;
    @FXML
    HBox star4;
    @FXML
    HBox star5;


    @FXML
    ImageView heartImage;
    @FXML
    Label nLikesLabel;


    public void initializeWindow(Resenha res)
    {
        this.res = res;

        reviewArea.setText(res.getComentario());
        nameLabel.setText(res.getId_usuario());
        iconImage.setImage(fg.fa.consultarUsuario(res.getId_usuario()).getFotoPerfil());

        heartImage.setVisible(fg.fa.isLiked(fg.fa.usuario.getId(), res.getId_videojuego(), res.getIdResenha()));
        nLikesLabel.setText(String.valueOf(res.getLikes()));

        switch(res.getValoracion())
        {
            case 1:
                starsHbox.getChildren().remove(star2);
            case 2:
                starsHbox.getChildren().remove(star3);
            case 3:
                starsHbox.getChildren().remove(star4);
            case 4:
                starsHbox.getChildren().remove(star5);
            default:

        }
    }


    @FXML
    public void heartOnAction(MouseEvent e)
    {
        if(!heartImage.isVisible())
        {
            fg.fa.insertarMeGusta(fg.fa.usuario.getId(), res.getId_videojuego(), res.getIdResenha());
            fg.fa.updateLikes(res);
            nLikesLabel.setText(String.valueOf(res.getLikes()));
            heartImage.setVisible(true);
        }
        else
        {
            fg.fa.borrarMeGusta(fg.fa.usuario.getId(), res.getId_videojuego(), res.getIdResenha());
            fg.fa.updateLikes(res);
            nLikesLabel.setText(String.valueOf(res.getLikes()));
            heartImage.setVisible(false);

        }
    }




    public void setMainApp(FachadaGUI fg)
    {
        this.fg = fg;
    }
}
