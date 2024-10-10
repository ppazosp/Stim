package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Demo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class AdminEditDemoWController implements Controller {
    FachadaGUI fg;
    Demo demo;
    Stage window;

    @FXML
    ImageView iconImage;
    @FXML
    TextField nameField;
    @FXML
    TextField monthField;
    @FXML
    TextField yearField;
    @FXML
    TextField urlField;


    public void initializeWindow(Demo d, Stage window) {
        this.window = window;

        if (d != null) {
            this.demo = d;

            iconImage.setImage(demo.getImagen());
            nameField.setText(demo.getNombre());
            monthField.setText(String.valueOf(demo .getMes()));
            monthField.setDisable(true);
            yearField.setText(String.valueOf(demo .getAno()));
            yearField.setDisable(true);
            urlField.setText(demo.getUrl());
        }
    }

    @FXML
    public void publishEdit(MouseEvent event) {

        if(monthField.getText().isEmpty())
        {
            monthField.requestFocus();
            return;
        }else if(yearField.getText().isEmpty())
        {
            yearField.requestFocus();
            return;
        }

        int month;
        int year;
        try{
           month = Integer.parseInt(monthField.getText());
        }catch (NumberFormatException e)
        {
            monthField.requestFocus();
            return;
        }

        try{
            year = Integer.parseInt(yearField.getText());
        }catch (NumberFormatException e)
        {
            yearField.requestFocus();
            return;
        }

        window.close();
        fg.loading();

        new Thread(() -> {
            Demo d = new Demo(nameField.getText(),
                    month,
                    year,
                    iconImage.getImage(),
                    fg.fa.usuario.getId(),
                    urlField.getText());

            fg.fa.publicarDemo(d);

            Platform.runLater(() -> fg.showAdminScene());
        }).start();
    }

    @FXML
    private void onDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    @FXML
    private void onDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasFiles()) {
            List<File> files = dragboard.getFiles();
            if (!files.isEmpty()) {
                File file = files.get(0);
                String fileName = file.getName().toLowerCase();
                if (fileName.endsWith(".png") || fileName.endsWith(".jpg")) {
                    Image image = new Image(file.toURI().toString());
                    iconImage.setImage(image);
                }
            }
        }
        event.setDropCompleted(true);
        event.consume();
    }


    @FXML
    public void showCommunityScene(MouseEvent event) {
        fg.showCommunityScene();
    }

    @FXML
    public void showSettingsScene(MouseEvent event) {
        fg.showSettingsScene();
    }

    @FXML
    public void showAdminScene(MouseEvent event) {

    }

    @FXML
    public void showMainScene(MouseEvent event) {
        fg.showMainWindow(false);
    }

    public void setMainApp(FachadaGUI mainApp) {
        this.fg = mainApp;
    }
}
