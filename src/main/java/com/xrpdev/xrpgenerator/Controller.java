package com.xrpdev.xrpgenerator;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.xrpl.xrpl4j.wallet.DefaultWalletFactory;
import org.xrpl.xrpl4j.wallet.SeedWalletGenerationResult;
import org.xrpl.xrpl4j.wallet.Wallet;
import org.xrpl.xrpl4j.wallet.WalletFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller implements Initializable {

    @FXML
    private Button btn_search;

    @FXML
    private TextField tf_account;

    @FXML
    private TableView<AccountData> tv_matches;

    @FXML
    private TableColumn<AccountData, String> tc_address;

    @FXML
    private TableColumn<AccountData, String> tc_seed;

    @FXML
    private ImageView iv_code;

    private static final WalletFactory walletFactory = DefaultWalletFactory.getInstance();

    public static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tc_address.setCellValueFactory(new PropertyValueFactory<>("account"));
        tc_seed.setCellValueFactory(new PropertyValueFactory<>("secret"));

        registerListeners();
    }

    private void registerListeners() {
        btn_search.setOnAction(event -> {
            executorService.submit(() ->
            {
                String search = tf_account.getText();
                while (true) {
                    SeedWalletGenerationResult result = walletFactory.randomWallet(false);
                    Wallet testWallet = result.wallet();
                    String address = testWallet.classicAddress().toString();
                    String seed = result.seed();
                    if (address.toLowerCase().substring(1).startsWith(search.toLowerCase())) {
                        Platform.runLater(() -> {
                            tv_matches.getItems().add(new AccountData(address, seed));
                        });
                    }
                }
            });
            btn_search.setDisable(true);
        });


        tv_matches.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    ByteArrayOutputStream out = QRCode.from(newValue.getSecret()).to(ImageType.PNG).withSize(200, 200).stream();
                    ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

                    Image image = new Image(in);
                    iv_code.setImage(image);
                }
        );
    }
}
