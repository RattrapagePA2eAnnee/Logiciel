package com.example.demo1;

import com.example.demo1.model.InfoResponse;
import com.example.demo1.model.Services;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.Paragraph;
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
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

public class ServicesListWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    String token;

    public void showAndWait() {
        Stage stage = new Stage();
        stage.setTitle("Liste des services");

        ArrayList<Services> servicesList = new ArrayList<>();

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

            ApiClient.Services getServices = ApiClient.getRetrofitInstance().create(ApiClient.Services.class);
            JsonObject requestBody2 = new JsonObject();
            Call<ResponseBody> call2 = getServices.getservices(requestBody2, token);

            try {
                Response<ResponseBody> response2 = call2.execute();
                if (response2.isSuccessful()) {
                    assert response2.body() != null;
                    String responseString = response2.body().string();
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
                    if (jsonObject.has("services")) {
                        ArrayList<Services> serviceList = gson.fromJson(jsonObject.get("services"), new TypeToken<ArrayList<Services>>() {}.getType());
                        servicesList.addAll(serviceList);
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

        ObservableList<Services> service = FXCollections.observableArrayList();
        service.addAll(servicesList);

        TableView<Services> tableView = new TableView<>();
        tableView.setItems(service);

        TableColumn<Services, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Services, String> serviceNameColumn = new TableColumn<>("Nom du service");
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("service_name"));

        TableColumn<Services, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Services, String> priceColumn = new TableColumn<>("Prix");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.getColumns().addAll(idColumn, serviceNameColumn, descriptionColumn, priceColumn);

        Button exportToPDFButton = new Button("Exporter en PDF");
        Button exportToPDFButton2 = new Button("Exporter les participations");
        exportToPDFButton.setOnAction(e -> exportTableToPDF(tableView));
        exportToPDFButton2.setOnAction(e -> infoexport());

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(exportToPDFButton, exportToPDFButton2, tableView);

        Scene scene = new Scene(vbox, 900, 600);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @Override
    public void start(Stage stage) {
    }

    private void exportTableToPDF(TableView<Services> table) {
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
                for (TableColumn<Services, ?> col : table.getColumns()) {
                    pdfTable.addCell(col.getText());
                }

                for (Services serv : table.getItems()) {
                    pdfTable.addCell(String.valueOf(serv.getId()));
                    pdfTable.addCell(serv.getService_name());
                    pdfTable.addCell(serv.getDescription());
                    pdfTable.addCell(serv.getPrice());
                }

                document.add(pdfTable);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                document.close();
            }
        }
    }

    public void infoexport() {
        ArrayList<InfoResponse> infosResponse = new ArrayList<>();
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

            ApiClient.Infos getServices = ApiClient.getRetrofitInstance().create(ApiClient.Infos.class);
            JsonObject requestBody2 = new JsonObject();
            Call<ResponseBody> call2 = getServices.getinfos(requestBody2, token);

            try {
                Response<ResponseBody> response2 = call2.execute();
                if (response2.isSuccessful()) {
                    assert response2.body() != null;
                    String responseString = response2.body().string();
                    Gson gson = new Gson();

                    // Directly deserialize the response into InfoResponse
                    InfoResponse infoResponseObject = gson.fromJson(responseString, InfoResponse.class);

                    // Add the deserialized object to your list
                    infosResponse.add(infoResponseObject);
                    result2.set(true);
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

        // Run the export method on the JavaFX Application Thread
        javafx.application.Platform.runLater(() -> exportInfoToPDF(infosResponse));
    }


    

    private void exportInfoToPDF(ArrayList<InfoResponse> infosResponse) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Info as PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File saveFile = fileChooser.showSaveDialog(null);

        if (saveFile != null) {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(saveFile));
                document.open();

                // Define a bold font for the titles
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);

                for (InfoResponse info : infosResponse) {

                    // Title & Export CourseParticipation
                    document.add(new Paragraph("Inscription aux formations", titleFont));
                    document.add(new Paragraph("\n")); // Spacing
                    PdfPTable courseTable = new PdfPTable(4);
                    courseTable.addCell("Prénom");
                    courseTable.addCell("Nom");
                    courseTable.addCell("Nom de la formation");
                    courseTable.addCell("Prix frais d'inscription");
                    for (InfoResponse.CourseParticipation cp : info.getInfos().getCourse_participation()) {
                        courseTable.addCell(cp.getFirst_name());
                        courseTable.addCell(cp.getLast_name());
                        courseTable.addCell(cp.getCourse_name());
                        courseTable.addCell(cp.getRegistration_price());
                    }
                    document.add(courseTable);
                    document.add(new Paragraph("\n"));

                    // Title & Export ParkingReservation
                    document.add(new Paragraph("Reservation des parkings", titleFont));
                    document.add(new Paragraph("\n")); // Spacing
                    PdfPTable parkingTable = new PdfPTable(6);
                    parkingTable.addCell("Prénom");
                    parkingTable.addCell("Nom");
                    parkingTable.addCell("Numéro du parking");
                    parkingTable.addCell("Début");
                    parkingTable.addCell("Fin");
                    parkingTable.addCell("Prix");
                    for (InfoResponse.ParkingReservation pr : info.getInfos().getParking_reservation()) {
                        parkingTable.addCell(pr.getFirst_name());
                        parkingTable.addCell(pr.getLast_name());
                        parkingTable.addCell(String.valueOf(pr.getParking_number()));
                        parkingTable.addCell(pr.getStart_time());
                        parkingTable.addCell(pr.getEnd_time());
                        parkingTable.addCell(pr.getPrice());
                    }
                    document.add(parkingTable);
                    document.add(new Paragraph("\n"));

                    // Title & Export PlaneReservation
                    document.add(new Paragraph("Réservation des avions", titleFont));
                    document.add(new Paragraph("\n")); // Spacing
                    PdfPTable planeTable = new PdfPTable(7);
                    planeTable.addCell("Prénom");
                    planeTable.addCell("Nom");
                    planeTable.addCell("Nom de l'avion");
                    planeTable.addCell("Début");
                    planeTable.addCell("Fin");
                    planeTable.addCell("Prix");
                    planeTable.addCell("Type");
                    for (InfoResponse.PlaneReservation pl : info.getInfos().getPlane_reservation()) {
                        planeTable.addCell(pl.getFirst_name());
                        planeTable.addCell(pl.getLast_name());
                        planeTable.addCell(pl.getPlane_name());
                        planeTable.addCell(pl.getStart_time());
                        planeTable.addCell(pl.getEnd_time());
                        planeTable.addCell(pl.getPrice());
                        planeTable.addCell(pl.getType());
                    }
                    document.add(planeTable);
                    document.add(new Paragraph("\n"));

                    // Title & Export ServiceReservation
                    document.add(new Paragraph("Liste achat de services", titleFont));
                    document.add(new Paragraph("\n")); // Spacing
                    PdfPTable serviceTable = new PdfPTable(4);
                    serviceTable.addCell("Prénom");
                    serviceTable.addCell("Nom");
                    serviceTable.addCell("Nom du service");
                    serviceTable.addCell("Prix");
                    for (InfoResponse.ServiceReservation sr : info.getInfos().getService_reservation()) {
                        serviceTable.addCell(sr.getFirst_name());
                        serviceTable.addCell(sr.getLast_name());
                        serviceTable.addCell(sr.getService_name());
                        serviceTable.addCell(sr.getPrice());
                    }
                    document.add(serviceTable);
                    document.add(new Paragraph("\n"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                document.close();
            }
        }
    }



}
