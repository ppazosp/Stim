package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.Demo;
import com.bdii.stimfx.aplicacion.Videojuego;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MainWController implements Controller {

    FachadaGUI fg;

    @FXML
    Demo demoGame;
    @FXML
    Videojuego top1Game;
    @FXML
    Videojuego top2Game;
    @FXML
    Videojuego top3Game;
    @FXML
    Videojuego nextLaunchGame;

    //PANE
    @FXML
    AnchorPane rightPane;

    @FXML
    TextField searchBar;

    //DEMO
    @FXML
    ImageView demoIconImage;
    @FXML
    Label demoNameLabel;

    //NEXT LAUNCH
    @FXML
    HBox nextLaunchVbox;
    @FXML
    ImageView launchIconImage;
    @FXML
    Label launchNameLabel;
    @FXML
    HBox launchIconsHbox;
    @FXML
    Label launchDaysLabel;


    @FXML
    VBox topSellersVbox;

    //TOP1
    @FXML
    HBox top1Hbox;
    @FXML
    ImageView top1IconImage;
    @FXML
    Label top1NameLabel;
    @FXML
    HBox top1IconsHbox;
    @FXML
    Label top1DateLabel;
    @FXML
    Label top1PriceLabel;

    //TOP2
    @FXML
    HBox top2Hbox;
    @FXML
    ImageView top2IconImage;
    @FXML
    Label top2NameLabel;
    @FXML
    HBox top2IconsHbox;
    @FXML
    Label top2DateLabel;
    @FXML
    Label top2PriceLabel;

    //TOP3
    @FXML
    HBox top3Hbox;
    @FXML
    ImageView top3IconImage;
    @FXML
    Label top3NameLabel;
    @FXML
    HBox top3IconsHbox;
    @FXML
    Label top3DateLabel;
    @FXML
    Label top3PriceLabel;

    public void initializeWindow() {

        fg.loading();

        new Thread(() ->{

            demoGame = fg.fa.consultarDemo(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
            nextLaunchGame = fg.fa.proximoVideojuego();
            List<Videojuego> topSellers = fg.fa.consultaVideoJuegosInicio();

            Platform.runLater(() -> {

                if (demoGame != null) {
                    demoIconImage.setImage(demoGame.getImagen());
                    demoNameLabel.setText(demoGame.getNombre());
                }

                if (nextLaunchGame != null) {
                    launchIconImage.setImage(nextLaunchGame.getImagen());
                    launchNameLabel.setText(nextLaunchGame.getNombre());
                    fg.showPlatforms(nextLaunchGame, launchIconsHbox);
                    long daysToLaunch = ChronoUnit.DAYS.between(LocalDate.now(ZoneId.systemDefault()), nextLaunchGame.getFechaSubida().toLocalDate());
                    launchDaysLabel.setText(Long.toString(daysToLaunch));
                } else {
                    rightPane.getChildren().remove(nextLaunchVbox);
                    nextLaunchVbox = null;
                    topSellersVbox.setLayoutX(200);
                }

                if (topSellers != null) {
                    if (topSellers.get(0) != null) {
                        top1Game = topSellers.get(0);
                        top1IconImage.setImage(top1Game.getImagen());
                        top1NameLabel.setText(top1Game.getNombre());
                        fg.showPlatforms(top1Game, top1IconsHbox);
                        top1DateLabel.setText(top1Game.getFechaSubida().toString());
                        top1PriceLabel.setText(top1Game.getPrecio() + "€");
                    } else {
                        topSellersVbox.getChildren().remove(top1Hbox);
                        top1Hbox = null;
                    }

                    if (topSellers.get(1) != null) {
                        top2Game = topSellers.get(1);
                        top2IconImage.setImage(top2Game.getImagen());
                        top2NameLabel.setText(top2Game.getNombre());
                        fg.showPlatforms(top2Game, top2IconsHbox);
                        top2DateLabel.setText(top2Game.getFechaSubida().toString());
                        top2PriceLabel.setText(top2Game.getPrecio() + "€");
                    } else {
                        topSellersVbox.getChildren().remove(top2Hbox);
                        top2Hbox = null;
                    }

                    if (topSellers.get(2) != null) {
                        top3Game = topSellers.get(2);
                        top3IconImage.setImage(top3Game.getImagen());
                        top3NameLabel.setText(top3Game.getNombre());
                        fg.showPlatforms(top3Game, top3IconsHbox);
                        top3DateLabel.setText(top3Game.getFechaSubida().toString());
                        top3PriceLabel.setText(top3Game.getPrecio() + "€");
                    } else {
                        topSellersVbox.getChildren().remove(top3Hbox);
                        top3Hbox = null;
                    }
                } else {
                    rightPane.getChildren().remove(topSellersVbox);
                    if (nextLaunchVbox != null) nextLaunchVbox.setLayoutX(390);
                }

                fg.loaded();
            });

        }).start();
    }


    @FXML
    public void loadGameWindow(MouseEvent event) {
        fg.loadGameWindow(demoGame.getUrl());
    }

    @FXML
    public void showProfileScene(MouseEvent event)
    {
        fg.showProfileScene();
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

    @FXML
    public void showSearchScene(ActionEvent event)
    {
        fg.showSearchScene(searchBar.getText());
    }

    @FXML
    public void showGameScene(MouseEvent event)
    {
        HBox clickedHBox = (HBox) event.getSource();

        if (clickedHBox == top1Hbox) {
            callShowGameScene(top1Game);
        } else if (clickedHBox == top2Hbox) {
            callShowGameScene(top2Game);
        } else if (clickedHBox == top3Hbox) {
            callShowGameScene(top3Game);
        }else {
            callShowGameScene(nextLaunchGame);
        }
    }

    public void callShowGameScene(Videojuego v)
    {
        fg.showGameScene(v, searchBar.getText(), 0);
    }

    public void setMainApp(FachadaGUI mainApp)
    {
        this.fg = mainApp;
    }
}