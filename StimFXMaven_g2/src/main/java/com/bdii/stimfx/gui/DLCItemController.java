package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.DLC;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class DLCItemController implements Controller {

    FachadaGUI fg;
    DLC dlc;
    EditGameWController superController;

    @FXML
    Label nameLabel;


    public void initializeWindow(DLC dlc, EditGameWController superControlller) {
        this.superController = superControlller;

        this.dlc = dlc;
        nameLabel.setText(dlc.getNombre());
    }

    @FXML
    public void editDLC(MouseEvent event)
    {
        fg.showEditDLCW(dlc, superController.game );
        superController.window.close();

    }

    public void setMainApp(FachadaGUI fg) {
        this.fg = fg;
    }
}
