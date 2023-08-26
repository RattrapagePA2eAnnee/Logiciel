package com.example.demo1;

import com.example.demo1.model.Planes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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

public class PlanesListWindow extends Application {

    String token;

    public static void main(String[] args) {
        launch(args);
    }

    public void showAndWait() {
        Stage stage = new Stage();
        stage.setTitle("Liste des Avions");

        ArrayList<Planes> planesList = new ArrayList<>();
        CountDownLatch latch2 = new CountDownLatch(1);
        AtomicBoolean result2 = new AtomicBoolean(false);

        new Thread(() -> {
            Properties properties = new Properties();

            try (FileInputStream input = new FileInputStream("config.properties")) {
                properties.load(input);
                token = properties.getProperty("token");
            } catch (IOException e) {
                e.printStackTrace();
            }
            ApiClient.Planes getplanes = ApiClient.getRetrofitInstance().create(ApiClient.Planes.class);
            JsonObject requestBody2 = new JsonObject();
            Call<ResponseBody> call2 = getplanes.getplanes(requestBody2, token);

            try {
                Response<ResponseBody> response2 = call2.execute();
                if (response2.isSuccessful()) {
                    assert response2.body() != null;
                    String responseString = response2.body().string();
                    Gson gson = createCustomGson();
                    JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
                    if (jsonObject.has("planes")) {
                        ArrayList<Planes> planeList = gson.fromJson(jsonObject.get("planes"), new TypeToken<ArrayList<Planes>>() {}.getType());
                        planesList.addAll(planeList);
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

        ObservableList<Planes> planes = FXCollections.observableArrayList();
        planes.addAll(planesList);

        TableView<Planes> tableView = new TableView<>();
        tableView.setItems(planes);

        TableColumn<Planes, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Planes, String> descriptionColumn = new TableColumn<>("Name");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("plane_name"));

        TableColumn<Planes, Integer> stockColumn = new TableColumn<>("Horometer");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("horometer"));

        TableColumn<Planes, Integer> priceColumn = new TableColumn<>("Type");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("plane_type"));

        TableColumn<Planes, String> pictureColumn = new TableColumn<>("Prix /h");
        pictureColumn.setCellValueFactory(new PropertyValueFactory<>("hourly_price"));

        tableView.getColumns().addAll(idColumn, descriptionColumn, stockColumn, priceColumn, pictureColumn);

        // Button for exporting data to PDF
        Button exportToPDFButton = new Button("Exporter en PDF");
        exportToPDFButton.setOnAction(e -> exportTableToPDF(tableView));

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(exportToPDFButton, tableView);

        Scene scene = new Scene(vbox, 900, 600);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @Override
    public void start(Stage stage) throws Exception {
    }

    private void exportTableToPDF(TableView<Planes> table) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File saveFile = fileChooser.showSaveDialog(table.getScene().getWindow());

        if (saveFile != null) {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(saveFile));
                document.open();

                PdfPTable pdfTable = new PdfPTable(table.getColumns().size());
                for (TableColumn<Planes, ?> col : table.getColumns()) {
                    pdfTable.addCell(col.getText());
                }

                for (Planes plane : table.getItems()) {
                    pdfTable.addCell(String.valueOf(plane.getId()));
                    pdfTable.addCell(plane.getPlane_name());
                    pdfTable.addCell(String.valueOf(plane.getHorometer()));
                    pdfTable.addCell(plane.getPlane_type());
                    pdfTable.addCell(plane.getHourly_price());
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
}
