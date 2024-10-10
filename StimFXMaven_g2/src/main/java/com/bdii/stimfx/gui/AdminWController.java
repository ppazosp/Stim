package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Demo;
import com.bdii.stimfx.aplicacion.Torneo;
import com.bdii.stimfx.aplicacion.Usuario;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class AdminWController implements Controller {
    FachadaGUI fg;

    //MENU BAR
    @FXML
    HBox comMenu;
    @FXML
    HBox editMenu;
    @FXML
    HBox adminMenu;



    @FXML
    VBox compVbox;
    @FXML
    VBox addCompVbox;

    @FXML
    VBox demoVbox;
    @FXML
    VBox addDemoVbox;

    @FXML
    VBox userVbox;

    public void initializeWindow()
    {
        fg.loading();

        new Thread(() -> {

            List<Torneo> myTorns = fg.fa.consultarTorneosAdmin(fg.fa.usuario);
            List <Demo> myDemos = fg.fa.consultarDemoAdmin(fg.fa.usuario);
            List<Usuario> myUsers = fg.fa.consultarUsuariosNoAdmins();

            Platform.runLater(() -> {

                if(!(fg.fa.usuario.isCompetitivePlayer())) comMenu.setVisible(false);
                if(!(fg.fa.usuario.isEditor())) editMenu.setVisible(false);
                if(!(fg.fa.usuario.isAdmin())) adminMenu.setVisible(false);

                compVbox.getChildren().clear();
                try {
                    int count = 0;
                    HBox row = new HBox();
                    row.setSpacing(5);
                    for (Torneo t : myTorns) {
                        if (count > 3) {
                            compVbox.getChildren().add(row);
                            count = 0;
                            row = new HBox();
                            row.setSpacing(5);
                        }
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bdii/stimfx/gui/adminCompItem.fxml"));
                        row.getChildren().add(loader.load());

                        AdminCompItemController controller = loader.getController();
                        controller.setMainApp(fg);
                        controller.initializeWindow(t,this);

                        count++;
                    }
                    if (count > 3) {
                        compVbox.getChildren().add(row);
                        row = new HBox();
                        row.setSpacing(5);
                    }
                    row.getChildren().add(addCompVbox);
                    compVbox.getChildren().add(row);




                    count = 0;
                    row = new HBox();
                    row.setSpacing(5);
                    row.setPadding(new Insets(5, 5, 5, 5));
                    for (Demo d : myDemos) {
                        if (count > 3) {
                            demoVbox.getChildren().add(row);
                            count = 0;
                            row = new HBox();
                            row.setSpacing(5);
                        }
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bdii/stimfx/gui/adminDemoItem.fxml"));
                        row.getChildren().add(loader.load());

                        AdminDemoItemController controller = loader.getController();
                        controller.setMainApp(fg);
                        controller.initializeWindow(d, this);

                        count++;
                    }
                    if (count > 3) {
                        demoVbox.getChildren().add(row);
                        row = new HBox();
                        row.setSpacing(5);
                    }
                    row.getChildren().add(addDemoVbox);
                    demoVbox.getChildren().add(row);




                    count = 0;
                    row = new HBox();
                    row.setSpacing(5);
                    for (Usuario u : myUsers) {
                        if(u.getId().equals(fg.fa.usuario.getId())) continue;

                        if (count > 2) {
                            userVbox.getChildren().add(row);
                            count = 0;
                            row = new HBox();
                            row.setSpacing(5);
                        }
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bdii/stimfx/gui/adminUserItem.fxml"));
                        row.getChildren().add(loader.load());

                        AdminUserItemController controller = loader.getController();
                        controller.setMainApp(fg);
                        controller.initializeWindow(u, this);

                        count++;
                    }
                    userVbox.getChildren().add(row);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                fg.loaded();
            });
        }).start();
    }


    @FXML
    public void editDemo(MouseEvent event) {
        fg.showEditDemoW(null);
    }

    @FXML
    public void editComp(MouseEvent event) {
        fg.showEditCompW(null);
    }

    @FXML
    public void showCommunityScene(MouseEvent event) {
        fg.showCommunityScene();
    }

    @FXML
    public void showEditScene(MouseEvent event) {
        fg.showEditScene();
    }

    @FXML
    public void showSettingsScene(MouseEvent event) {
        fg.showSettingsScene();
    }

    @FXML
    public void showMainScene(MouseEvent event) {
        fg.showMainWindow(false);
    }

    public void setMainApp(FachadaGUI mainApp) {
        this.fg = mainApp;
    }
}
