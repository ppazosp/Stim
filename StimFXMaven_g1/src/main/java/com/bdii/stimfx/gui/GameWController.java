package com.bdii.stimfx.gui;

import com.bdii.stimfx.aplicacion.DLC;
import com.bdii.stimfx.aplicacion.Resenha;
import com.bdii.stimfx.aplicacion.Videojuego;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameWController implements Controller {

    FachadaGUI fg;
    Application app;
    Videojuego game;

    String search;
    int from;

    @FXML
    ImageView photoImage;

    @FXML
    ImageView bannerImage;
    @FXML
    Label nameLabel;
    @FXML
    Label dateLabel;
    @FXML
    HBox buyHbox;
    @FXML
    Label priceLabel;
    @FXML
    TextArea descrpArea;
    @FXML
    Label creatorLabel;
    @FXML
    Label downloadsLabel;
    @FXML
    VBox checkVbox;
    @FXML
    HBox addHbox;
    @FXML
    Label buyLabel;
    @FXML
    Label moneyLabel;

    @FXML
    VBox buyVbox;
    @FXML
    VBox addVbox;
    @FXML
    ScrollPane reviewsPanel;
    @FXML
    VBox reviewsVbox;
    @FXML
    Label resLabel;
    @FXML
    Label valLabel;

    float dlcsPrice;

    @FXML
    List<DLCCheckItemController> itemsList;


    public void initializeWindow(Videojuego game, String search, int from)
    {
        fg.loading();

        new Thread(() -> {
            this.game = game;
            this.search = search;
            this.from = from;

            boolean tieneVideojuego = fg.fa.tieneVideojeugo(fg.fa.usuario, game);
            game.setResenhas(new ArrayList<>());
            fg.fa.consultarResenhas(game);
            List<DLC> dlcList = fg.fa.consultarDLCsVideojuego(game);
            float avgVal = fg.fa.consultarMediaResenhas(game);


            Platform.runLater(() -> {

                photoImage.setImage(fg.fa.usuario.getFotoPerfil());

                bannerImage.setImage(game.getBanner());
                nameLabel.setText(game.getNombre());
                dateLabel.setText("Fecha de publicacion: "+game.getFechaSubida().toString());
                if (game.getFechaSubida().toLocalDate().isAfter(LocalDate.now())) buyHbox.setVisible(false);
                else if (tieneVideojuego) {
                    buyLabel.setText("Comprado");
                    buyHbox.setDisable(true);
                }
                priceLabel.setText(game.getPrecio()+"€");
                descrpArea.setText(game.getDescripcion());
                moneyLabel.setText(String.valueOf(fg.fa.usuario.getDinero()));
                if(fg.fa.usuario.getDinero() < game.getPrecio())
                {
                    moneyLabel.setTextFill(Color.RED);
                    buyHbox.setDisable(true);
                }
                creatorLabel.setText("Creador: "+game.getEditor().getId());
                downloadsLabel.setText("Descargas: " + game.getNumDescargas());
                valLabel.setText(String.format("%.1f", avgVal));

                itemsList = new ArrayList<>();
                checkVbox.getChildren().clear();

                boolean hasAll = true;

                try {
                    for (DLC d : dlcList) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bdii/stimfx/gui/dlcCheckItem.fxml"));
                        checkVbox.getChildren().add(loader.load());

                        DLCCheckItemController controller = loader.getController();
                        controller.setMainApp(fg);
                        controller.initializeWindow(d, this);
                        hasAll = hasAll && controller.checkBox.isSelected();

                        itemsList.add(controller);
                    }
                    if (dlcList.isEmpty() || !tieneVideojuego) addHbox.setVisible(false);
                    if(hasAll) addHbox.setDisable(true);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                reviewsPanel.setVisible(true);
                reviewsVbox.getChildren().clear();

                try {
                    for (Resenha r : game.getResenhas()) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bdii/stimfx/gui/reviewItem.fxml"));
                        reviewsVbox.getChildren().add(loader.load());

                        ReviewItemController controller = loader.getController();
                        controller.setMainApp(fg);
                        controller.initializeWindow(r);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                reviewsPanel.setVisible(false);

                fg.loaded();
            });

        }).start();

    }

    @FXML
    public void back(MouseEvent event)
    {
        if(from == 0) fg.showMainWindow(false);
        else fg.showSearchScene(search);
    }

    @FXML
    public void myReview(MouseEvent event)
    {

        fg.showEditReviewW(null , game, search, from);
    }

    @FXML
    private void openTrailer(ActionEvent event) {
        app.getHostServices().showDocument(game.getTrailer());
    }

    @FXML
    public void buyGame(MouseEvent event)
    {
        fg.loading();

        new Thread(() -> {
            //METER AQUI IF/ELSE
            fg.fa.compraVideojuego(game);
            moneyLabel.setText(String.valueOf(fg.fa.usuario.getDinero()));

            Platform.runLater(() ->{
                buyLabel.setText("Comprado");
                buyHbox.setDisable(true);
                addHbox.setVisible(true);

                fg.loaded();
            });
        }).start();
    }

    @FXML
    public void addDLCs(MouseEvent event)
    {
        fg.loading();

        new Thread(() ->{
            boolean hasAll = true;
            ArrayList<DLCCheckItemController> cb = new ArrayList<>();
            for (DLCCheckItemController c : itemsList) {
                if (c.checkBox.isSelected() && !c.checkHbox.isDisable()) {
                    fg.fa.comprarDLC(c.dlc, fg.fa.usuario);
                    cb.add(c);
                }
                hasAll = hasAll && c.checkBox.isSelected();
            }
            final boolean finalHasAll = hasAll;
            Platform.runLater(() -> {
                moneyLabel.setText(String.valueOf(fg.fa.usuario.getDinero()));
                if(finalHasAll) addHbox.setDisable(true);

                for (DLCCheckItemController c : cb) {
                    c.checkHbox.setDisable(true);
                }

                fg.loaded();
            });
        }).start();
    }

    @FXML
    public void alterView()
    {
        if(!reviewsPanel.isVisible())
        {
            reviewsPanel.setVisible(true);
            buyVbox.setVisible(false);
            addVbox.setVisible(false);
            resLabel.setText("Ver info");

        }else
        {
            reviewsPanel.setVisible(false);
            buyVbox.setVisible(true);
            addVbox.setVisible(true);
            resLabel.setText("Ver reseñas");
        }
    }

    @FXML
    public void showMainScene(MouseEvent event)
    {
        fg.showMainWindow(false);
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

    public void setMainApp(FachadaGUI mainApp)
    {
        this.fg = mainApp;
    }

    public void setApp(Application app) {
        this.app = app;
    }
}