package com.bdii.stimfx.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LogInWController implements Controller{

    FachadaGUI fg;

    @FXML
    Label credentialsErrorLabel;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;

    //Añadir xml para enlace que te lleve a Steam
    @FXML
    public void showSiginScene(MouseEvent event)
    {
        fg.showSigninScene();
    }

    public void loginCheck(MouseEvent event)
    {

        if(fg.checkCredentials(usernameField.getText(), passwordField.getText()))
        {
            fg.showMainWindow(true);
        }
        else{
            usernameField.clear();
            passwordField.clear();
            credentialsErrorLabel.setVisible(true);

        }
    }

    public void setMainApp(FachadaGUI mainApp)
    {
        this.fg = mainApp;
    }
}