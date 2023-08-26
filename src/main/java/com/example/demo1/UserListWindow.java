package com.example.demo1;

import com.example.demo1.model.Users;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserListWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private String token;

    public void showAndWait() {
        Stage stage = new Stage();
        stage.setTitle("Liste des utilisateurs");

        ArrayList<Users> usersList = new ArrayList<>();

        CountDownLatch latch2 = new CountDownLatch(1);
        AtomicBoolean result2 = new AtomicBoolean(false);

        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            token = properties.getProperty("token");
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            ApiClient.Users getusers = ApiClient.getRetrofitInstance().create(ApiClient.Users.class);
            Call<ResponseBody> call2 = getusers.getusers(new JsonObject(), token);

            try {
                Response<ResponseBody> response2 = call2.execute();
                if (response2.isSuccessful()) {
                    assert response2.body() != null;
                    String responseString = response2.body().string();
                    Gson gson = createCustomGson();
                    UsersResponse usersResponse = gson.fromJson(responseString, UsersResponse.class);
                    if (usersResponse != null) {
                        usersList.addAll(usersResponse.getUsers());
                        result2.set(true);
                    }
                } else {
                    result2.set(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
                result2.set(false);
            } finally {
                latch2.countDown();
            }
        }).start();

        try {
            latch2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ObservableList<Users> users = FXCollections.observableArrayList();
        users.addAll(usersList);

        TableView<Users> tableView = new TableView<>();
        tableView.setItems(users);

        TableColumn<Users, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Users, String> lastNameColumn = new TableColumn<>("Nom de famille");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Users, String> firstNameColumn = new TableColumn<>("Prénom");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Users, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Users, String> roleColumn = new TableColumn<>("Rôle");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<Users, String> subscriptionColumn = new TableColumn<>("Abonnement");
        subscriptionColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStatus"));

        TableColumn<Users, String> pictureColumn = new TableColumn<>("Image de profil");
        pictureColumn.setCellValueFactory(new PropertyValueFactory<>("profilePicture"));

        TableColumn<Users, LocalDateTime> birthDateColumn = new TableColumn<>("Date de naissance");
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        TableColumn<Users, LocalDateTime> creationTimeColumn = new TableColumn<>("Heure de création du compte");
        creationTimeColumn.setCellValueFactory(new PropertyValueFactory<>("accountCreationTime"));

        tableView.getColumns().addAll(idColumn, lastNameColumn, firstNameColumn, emailColumn,
                roleColumn, subscriptionColumn, pictureColumn, birthDateColumn, creationTimeColumn);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(createExportButton(tableView), tableView);

        Scene scene = new Scene(vbox, 1200, 600);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // The start method is empty because we don't use it here
    }

    private Button createExportButton(TableView<Users> tableView) {
        Button exportButton = new Button("Exporter en PDF");
        exportButton.setOnAction(event -> exportTableToPDF(tableView));
        return exportButton;
    }

    private void exportTableToPDF(TableView<Users> table) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as PDF");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File saveFile = fileChooser.showSaveDialog(table.getScene().getWindow());

        if (saveFile != null) {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(saveFile));
                document.open();

                PdfPTable pdfTable = new PdfPTable(table.getColumns().size());
                for (TableColumn<Users, ?> col : table.getColumns()) {
                    pdfTable.addCell(col.getText());
                }

                for (Users user : table.getItems()) {
                    pdfTable.addCell(String.valueOf(user.getId()));
                    pdfTable.addCell(user.getLastName());
                    pdfTable.addCell(user.getFirstName());
                    pdfTable.addCell(user.getEmail());
                    pdfTable.addCell(user.getRole());
                    pdfTable.addCell(user.getSubscriptionStatus());
                    pdfTable.addCell(user.getProfilePicture());
                    pdfTable.addCell(user.getBirthDate().toString());
                    pdfTable.addCell(user.getAccountCreationTime().toString());
                }
                document.add(pdfTable);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                document.close();
            }
        }
    }


    private Gson createCustomGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> {
            String datetime = json.getAsJsonPrimitive().getAsString();
            return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        });
        return gsonBuilder.create();
    }

    private static class UsersResponse {
        private ArrayList<Users> users;

        public ArrayList<Users> getUsers() {
            return users;
        }
    }
}
