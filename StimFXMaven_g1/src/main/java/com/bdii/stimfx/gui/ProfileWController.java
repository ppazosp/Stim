package com.bdii.stimfx.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

public class ProfileWController implements Controller {
    FachadaGUI fg;


    @FXML
    Label purseLabel;
    @FXML
    TextField purseField;


    @FXML
    ImageView profileImage;
    @FXML
    Label userField;
    @FXML
    Label nGamesLabel;
    @FXML
    Label nWinsLabel;
    @FXML
    TextField nameField;
    @FXML
    PasswordField passField;
    @FXML
    TextField emailField;
    @FXML
    Label changesLabel;

    @FXML
    Timeline changesWait;

    public void initializeWindow() {

        changesWait = new Timeline(new KeyFrame(Duration.seconds(2), e -> hideChangesLabel()));

        if(fg.fa.usuario.getFotoPerfil() != null) profileImage.setImage(fg.fa.usuario.getFotoPerfil());
        userField.setText(fg.fa.usuario.getId());
        nGamesLabel.setText(String.valueOf(fg.fa.contarJuegosUsuario(fg.fa.usuario.getId())));
        nWinsLabel.setText(String.valueOf(fg.fa.torneosGanados(fg.fa.usuario)));
        nameField.setText(fg.fa.usuario.getNombre());
        passField.setText(fg.fa.usuario.getContrasena());
        emailField.setText(fg.fa.usuario.getEmail());

        purseLabel.setText(String.valueOf(fg.fa.usuario.getDinero()));
    }
    @FXML
    public void modificarUsuario(MouseEvent event) {

        fg.loading();

        new Thread(() -> {

            fg.fa.modificarUsuario(nameField.getText(), passField.getText(), emailField.getText(), profileImage.getImage());

            Platform.runLater(() -> {
                changesLabel.setVisible(true);
                changesWait.play();

                fg.loaded();
            });
        }).start();

    }

    @FXML
    public void addFounds(MouseEvent event)
    {
        fg.loading();

        new Thread(() -> {

            double value;
            try{
                value = Double.parseDouble(purseField.getText());
            }catch (NumberFormatException e)
            {
                value = 0;
            }

            fg.fa.insertarFondos(fg.fa.usuario, value);

            Platform.runLater(() ->{

                fg.showProfileScene();

                fg.loaded();
            });
        }).start();
    }

    @FXML
    public void showLoginWindow(MouseEvent event)
    {
        fg.showLoginWindow();
    }

    private void hideChangesLabel() {
        changesLabel.setVisible(false);
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
                if (file.getName().toLowerCase().endsWith(".png") || file.getName().toLowerCase().endsWith(".jpg")) {
                    javafx.scene.image.Image image = new Image(file.toURI().toString());
                    profileImage.setImage(image);
                }
            }
        }
        event.setDropCompleted(true);
        event.consume();
    }

    @FXML
    public void showMainScene(MouseEvent event)
    {
        fg.showMainWindow(false);
    }

    @FXML
    public void showLibraryScene(MouseEvent event)
    {
        fg.showLibraryScene();
    }

    @FXML
    public void showSocialScene(MouseEvent event)
    {
        fg.showSocialScene();
    }

    @Override
    public void setMainApp(FachadaGUI mainApp)
    {
        this.fg = mainApp;
    }
}
