package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.DLC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DLCCheckItemController implements Controller{
    FachadaGUI fg;
    GameWController superController;
    DLC dlc;

    @FXML
    HBox checkHbox;
    @FXML
    CheckBox checkBox;
    @FXML
    Label priceLabel;

    public void initializeWindow(DLC dlc, GameWController superController) {
        this.dlc = dlc;
        this.superController = superController;

        checkBox.setText(dlc.getNombre());
        priceLabel.setText(dlc.getPrecio() + "€");
        if (fg.fa.tieneDLC(fg.fa.usuario, dlc)) {
            checkBox.setSelected(true);
            checkHbox.setDisable(true);
        }
    }

    @FXML
    public void OnAction(ActionEvent event)
    {
        if(checkBox.isSelected()) superController.dlcsPrice += (float) dlc.getPrecio();
        else superController.dlcsPrice -= (float) dlc.getPrecio();
        superController.addHbox.setDisable(superController.dlcsPrice > fg.fa.usuario.getDinero());
    }

    @Override
    public void setMainApp(FachadaGUI fg) {
        this.fg = fg;
    }
}
