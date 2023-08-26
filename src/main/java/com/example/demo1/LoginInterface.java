package com.example.demo1;

import com.example.demo1.model.Logins;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginInterface extends Application {
    Logins login;
    private Button loginButton;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connexion");

        // Image intégrée
        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/login.png")));
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(150);
        logoView.setPreserveRatio(true);

        // Éléments de l'interface
        Label usernameLabel = new Label("Email:");
        TextField usernameTextField = new TextField();

        Label passwordLabel = new Label("Mot de passe:");
        PasswordField passwordField = new PasswordField();

        loginButton = new Button("Se connecter");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(logoView, 0, 0, 2, 1);
        GridPane.setHalignment(logoView, HPos.CENTER);
        grid.add(usernameLabel, 0, 1);
        grid.add(usernameTextField, 1, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(loginButton, 1, 3);

        loginButton.setOnAction(event -> {
            String username = usernameTextField.getText();
            String password = passwordField.getText();

            if (checkLogin(username, password)) {
                launchMainInterface();
            } else {
                // Gestion des erreurs
            }
        });

        Scene scene = new Scene(grid, 325, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean checkLogin(final String email, final String password) {
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean result = new AtomicBoolean(false);

        new Thread(() -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("email", email);
            jsonObject.addProperty("password", password);
            jsonObject.addProperty("origin", "software");

            String json = new Gson().toJson(jsonObject);
            ApiClient.Connection connection = ApiClient.getRetrofitInstance().create(ApiClient.Connection.class);
            Call<ResponseBody> call = connection.login(new Gson().fromJson(json, JsonObject.class));

            try {
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Gson gson = new Gson();
                    login = gson.fromJson(responseBody, Logins.class);

                    if (login.isSuccess()) {
                        setLoginStatus(login);
                        result.set(true);
                    }
                } else {
                    result.set(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
                result.set(false);
            } finally {
                latch.countDown();
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result.get();
    }

    private void setLoginStatus(Logins logins) {
        Properties properties = new Properties();

        try (FileOutputStream output = new FileOutputStream("config.properties")) {
            properties.setProperty("token", logins.getConnection().getConnection().getToken());
            properties.store(output, "Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void launchMainInterface() {
        MainInterface mainInterface = new MainInterface(login.getConnection().getConnection().getFirstName());
        Stage mainStage = new Stage();
        mainInterface.start(mainStage);

        Stage loginStage = (Stage) loginButton.getScene().getWindow();
        loginStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
