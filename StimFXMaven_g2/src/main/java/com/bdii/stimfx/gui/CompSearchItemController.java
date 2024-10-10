package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Torneo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CompSearchItemController implements Controller {

    FachadaGUI fg;
    Torneo torn;
    CommunityWController superController;

    @FXML
    VBox itemVbox;
    @FXML
    ImageView iconImage;
    @FXML
    Label nameLabel;
    @FXML
    Label startLabel;
    @FXML
    Label endLabel;
    @FXML
    Label prizeLabel;
    @FXML
    HBox partHbox;
    @FXML
    Label hboxLabel;

    public void initializeWindow(Torneo torn, int opt, CommunityWController superController)
    {
        this.superController = superController;
        this.torn = torn;

        nameLabel.setText(torn.getNombre());
        if(opt == 0)
        {
            startLabel.setText(torn.getFecha_inicio().toString());
            endLabel.setText(torn.getFecha_final().toString());
        }else
        {
            startLabel.setText(torn.getFecha_final().toString());
            endLabel.setText(torn.getGanador());
            endLabel.setStyle("-fx-font-weight: bold;");
        }
        prizeLabel.setText(torn.getPremio() + "€");
        iconImage.setImage(torn.getVideojuego().getImagen());
    }


    @FXML
    public void interactTournament(MouseEvent event)
    {
        if (hboxLabel.getText().equals("Retirarse")) fg.fa.retirarseTorneo(fg.fa.usuario, torn);
        else fg.fa.participarTorneo(fg.fa.usuario, torn);

        superController.loadComp();
    }

    public void setMainApp(FachadaGUI fg)
    {
        this.fg = fg;
    }
}
