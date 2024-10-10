package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Demo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AdminDemoItemController implements Controller {

    FachadaGUI fg;
    Demo demo;
    AdminWController superController;

    @FXML
    ImageView iconImage;
    @FXML
    Label nameLabel;
    @FXML
    Label dateLabel;

    public void initializeWindow(Demo demo, AdminWController superController)
    {
        this.demo = demo;
        this.superController = superController;

        iconImage.setImage(demo.getImagen());
        nameLabel.setText(demo.getNombre());
        dateLabel.setText(demo.getAno() + "  " + demo.getMes());
    }

    @FXML
    public void editDemo(MouseEvent event) {
        fg.showEditDemoW(demo);
    }

    public void setMainApp(FachadaGUI fg)
    {
        this.fg = fg;
    }
}
