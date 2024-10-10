/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class FachadaGUI extends Application {

    FachadaAplicacion fa;

    public FachadaGUI() {
        // Constructor sin argumentos necesario para JavaFX
    }

    private Stage primaryStage;
    private Scene primaryScene;
    private Scene loadingScene;

    @Override
    public void start(Stage primaryStage) {
        fa = new FachadaAplicacion(this);

        this.primaryStage = primaryStage;

        this.primaryStage.setMaxWidth(1000);
        this.primaryStage.setMinWidth(1000);
        this.primaryStage.setMaxHeight(605);
        this.primaryStage.setMinHeight(605);

        showLoginWindow();
    }

    private <T extends Controller> T loadFXML(String fxmlFile, String windowTitle, Stage stage, Class<T> controllerClass) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            T controllerInstance = controllerClass.getDeclaredConstructor().newInstance();
            controllerInstance.setMainApp(this);
            loader.setController(controllerInstance);
            Parent root = loader.load();

            primaryScene = new Scene(root);
            if (stage == null) stage = primaryStage;
            stage.setScene(primaryScene);
            if (windowTitle == null) windowTitle = "Stim";
            stage.setTitle(windowTitle);
            stage.show();

            return controllerInstance;
        } catch (IOException | NoSuchMethodException | IllegalAccessException |
                 InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createLoadingScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bdii/stimfx/gui/loadingW.fxml"));
            LoadingWController loadingWController = new LoadingWController();
            loader.setController(loadingWController);
            Parent root = loader.load();
            loadingScene = new Scene(root);
            loadingWController.setMainApp(this);
            loadingWController.initializeWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loading() {
        primaryStage.setScene(loadingScene);
    }

    public void loaded() {
        primaryStage.setScene(primaryScene);
    }

    public void showLoginWindow() {
        LogInWController logInWController = loadFXML("/com/bdii/stimfx/gui/loginW.fxml", "Iniciar sesión", null, LogInWController.class);
        assert logInWController != null;
        logInWController.setMainApp(this);
    }

    public void showSigninScene() {
        SignInWController signInWController = loadFXML("/com/bdii/stimfx/gui/signinW.fxml", "Registrarse", null, SignInWController.class);
        assert signInWController != null;
        signInWController.setMainApp(this);
    }

    public void showMainWindow(boolean createWindow) {
        createLoadingScene();

        Stage old = primaryStage;

        if(createWindow)
        {
            primaryStage = new Stage();
            this.primaryStage.setResizable(false);

            this.primaryStage.setMaxWidth(1250);
            this.primaryStage.setMinWidth(1250);
            this.primaryStage.setMaxHeight(785);
            this.primaryStage.setMinHeight(785);
        }
        SettingsWController settingsWController = loadFXML("/com/bdii/stimfx/gui/settingsW.fxml", null, null, SettingsWController.class);
        assert settingsWController != null;
        settingsWController.setMainApp(this);
        settingsWController.initializeWindow();

        if(createWindow) old.close();
    }

    public void showSettingsScene() {
        SettingsWController settingsWController = loadFXML("/com/bdii/stimfx/gui/settingsW.fxml", null, null, SettingsWController.class);
        assert settingsWController != null;
        settingsWController.setMainApp(this);
        settingsWController.initializeWindow();
    }

    public void showCommunityScene()
    {
        CommunityWController communityWController = loadFXML("/com/bdii/stimfx/gui/communityW.fxml", null, null, CommunityWController.class);
        assert communityWController != null;
        communityWController.setMainApp(this);
        communityWController.load();
    }

    public void showEditScene() {
        EditWController editWController = loadFXML("/com/bdii/stimfx/gui/editW.fxml", null, null, EditWController.class);
        assert editWController != null;
        editWController.setMainApp(this);
        editWController.initializeWindow();
    }

    public void showEditGameWindow(Videojuego v) {
        Stage s = new Stage();
        s.setResizable(false);

        s.setMaxWidth(950);
        s.setMinWidth(950);
        s.setMaxHeight(635);
        s.setMinHeight(635);


        EditGameWController editGameWController = loadFXML("/com/bdii/stimfx/gui/editGameW.fxml", null, s, EditGameWController.class);
        assert editGameWController != null;
        editGameWController.setMainApp(this);
        editGameWController.initializeWindow(v, s);
    }

    public void showAdminScene()
    {
        AdminWController adminWController = loadFXML("/com/bdii/stimfx/gui/adminW.fxml", null, null, AdminWController.class);
        assert adminWController != null;
        adminWController.setMainApp(this);
        adminWController.initializeWindow();
    }

    public void showEditDemoW(Demo d) {
        Stage s = new Stage();
        s.setResizable(false);

        s.setMaxWidth(525);
        s.setMinWidth(525);
        s.setMaxHeight(345);
        s.setMinHeight(345);


        AdminEditDemoWController adminDemoWController = loadFXML("/com/bdii/stimfx/gui/adminEditDemoW.fxml", null, s, AdminEditDemoWController.class);
        assert adminDemoWController != null;
        adminDemoWController.setMainApp(this);
        adminDemoWController.initializeWindow(d, s);
    }

    public void showEditCompW(Torneo t) {
        Stage s = new Stage();
        s.setResizable(false);

        s.setMaxWidth(525);
        s.setMinWidth(525);
        s.setMaxHeight(345);
        s.setMinHeight(345);


        AdminEditCompWController adminEditCompWController = loadFXML("/com/bdii/stimfx/gui/adminEditCompW.fxml", null, s, AdminEditCompWController.class);
        assert adminEditCompWController != null;
        adminEditCompWController.setMainApp(this);
        adminEditCompWController.initializeWindow(t, s);
    }

    public void showEditDLCW(DLC d, Videojuego v) {
        Stage s = new Stage();
        s.setResizable(false);

        s.setMaxWidth(450);
        s.setMinWidth(450);
        s.setMaxHeight(265);
        s.setMinHeight(265);


        DLCEditWController dlcEditWController = loadFXML("/com/bdii/stimfx/gui/dlcEditW.fxml", null, s, DLCEditWController.class);
        assert dlcEditWController != null;
        dlcEditWController.setMainApp(this);
        dlcEditWController.initializeWindow(d, v, s);
    }





    public static void main(String[] args) {
        launch(args);
    }


    // METHODS

    public boolean checkCredentials(String username, String password)
    {
        return this.fa.checkCredentials(username, password);
    }

    public boolean register(String id, String clave, String nombre, String email){
        return this.fa.registrar(id, clave, nombre, email);
    }
}
