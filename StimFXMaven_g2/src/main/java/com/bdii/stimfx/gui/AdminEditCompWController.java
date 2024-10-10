package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Torneo;
import com.bdii.stimfx.aplicacion.Videojuego;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Date;
import java.util.List;

public class AdminEditCompWController implements Controller {
    FachadaGUI fg;
    Torneo torn;
    Stage window;

    @FXML
    ImageView iconImage;
    @FXML
    TextField nameField;
    @FXML
    DatePicker startPicker;
    @FXML
    DatePicker endPicker;
    @FXML
    TextField prizeField;
    @FXML
    ChoiceBox<String> choiceBox;


    public void initializeWindow(Torneo t, Stage window) {

        this.window = window;
        this.torn = t;

        List<String> allGames = fg.fa.consultarVideojuegos();

        choiceBox.getItems().addAll(allGames);

        if (t != null) {
            nameField.setText(torn.getNombre());
            startPicker.setValue(torn.getFecha_inicio().toLocalDate());
            endPicker.setValue(torn.getFecha_final().toLocalDate());
            prizeField.setText(String.valueOf(torn.getPremio()));
            choiceBox.getSelectionModel().select(choiceBox.getItems().indexOf(torn.getVideojuego().getNombre()));
        }
    }

    @FXML
    public void publishEdit(MouseEvent event) {

        if(startPicker.getValue() == null)
        {
            startPicker.show();
            return;
        }else if (endPicker.getValue() == null)
        {
            endPicker.show();
            return;
        }

        if(choiceBox.getSelectionModel().getSelectedItem() == null)
        {
            choiceBox.show();
            return;
        }

        window.close();
        fg.loading();

        new Thread(() -> {
            Videojuego v = fg.fa.consultarVideojuego(choiceBox.getValue());

            Torneo t = new Torneo(
                    (torn != null) ? torn.getId() : -1,
                    nameField.getText(),
                    Date.valueOf(startPicker.getValue()),
                    Date.valueOf(endPicker.getValue()),
                    (!prizeField.getText().isEmpty()) ? Integer.parseInt(prizeField.getText()) : 0,
                    v,
                    fg.fa.usuario);

            fg.fa.publicarTorneo(t);

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
