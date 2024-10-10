package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Torneo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AdminCompItemController implements Controller {

    FachadaGUI fg;
    Torneo torn;
    AdminWController superController;

    @FXML
    ImageView iconImage;
    @FXML
    Label nameLabel;
    @FXML
    Label dateLabel;

    public void initializeWindow(Torneo torn, AdminWController superController)
    {
        this.torn = torn;
        this.superController = superController;

        iconImage.setImage(torn.getVideojuego().getImagen());
        nameLabel.setText(torn.getNombre());
        dateLabel.setText(torn.getFecha_inicio().toString());

    }

    @FXML
    public void editComp(MouseEvent event) {
        fg.showEditCompW(torn);
    }

    public void setMainApp(FachadaGUI fg)
    {
        this.fg = fg;
    }
}
