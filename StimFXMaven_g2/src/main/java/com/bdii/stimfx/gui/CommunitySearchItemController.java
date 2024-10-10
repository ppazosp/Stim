package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Comunidad;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class CommunitySearchItemController implements Controller {

    FachadaGUI fg;
    Comunidad com;
    CommunityWController superController;

    @FXML
    HBox comHbox;
    @FXML
    ImageView iconImage;
    @FXML
    Label nameLabel;
    @FXML
    Label membersLabel;
    @FXML
    HBox enterHbox;

    public void initializeWindow(Comunidad com, CommunityWController superController)
    {
        this.superController = superController;
        this.com = com;
        iconImage.setImage(com.getEscudo());
        nameLabel.setText(com.getNombre());
        membersLabel.setText("Miembros: " + fg.fa.contarMiembrosEquipo(com));
    }


    @FXML
    public void enterCommunity(MouseEvent event)
    {
        if(fg.fa.tieneComunidad(fg.fa.usuario)) fg.fa.salirJugadorEquipo(fg.fa.usuario.getId());
        fg.fa.insertarJugadorEquipo(fg.fa.usuario.getId(), com);

        superController.loadCom();
    }

    public void setMainApp(FachadaGUI fg)
    {
        this.fg = fg;
    }
}
