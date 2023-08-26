package com.example.demo1;

import com.example.demo1.model.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainInterface extends Application {

    public MainInterface() {
        // Constructeur par défaut
    }


    private String message;
    private BarChart<String, Number> chart;
    private final double BASE_WINDOW_WIDTH = 1100;
    private final double BASE_WINDOW_HEIGHT = 610;
    private Label lastClickedButton;

    String token;

    public MainInterface(String message) {
        this.message = message;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Main Interface");

        // Créez un conteneur pour les éléments de la fenêtre
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(10);
        root.setAlignment(Pos.CENTER); // Centrer le contenu verticalement et horizontalement

        // Ajoutez des contraintes de taille de colonne pour que les rectangles se redimensionnent proportionnellement
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setHgrow(Priority.SOMETIMES);
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints2.setHgrow(Priority.ALWAYS);
        root.getColumnConstraints().addAll(columnConstraints1, columnConstraints2);

        // Ajoutez des contraintes de taille de ligne pour que les rectangles se redimensionnent proportionnellement
        for (int i = 0; i < 10; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.ALWAYS);
            root.getRowConstraints().add(rowConstraints);
        }

        // Créez le label pour afficher le texte de bienvenue
        Label welcomeLabel = new Label("Bonjour ! Bienvenue dans l'interface de modération AEN                     " + message + " !");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        GridPane.setConstraints(welcomeLabel, 0, 0, 2, 1); // Span sur 2 colonnes
        root.getChildren().add(welcomeLabel);

        // Créez un rectangle pour la ligne noire
        Rectangle line = new Rectangle();
        line.setWidth(Double.MAX_VALUE);
        line.setHeight(1);
        line.setFill(Color.BLACK);

        // Ajouter le rectangle de la ligne noire à la grille
        GridPane.setConstraints(line, 0, 1, 2, 1, HPos.CENTER, VPos.CENTER); // Span sur 2 colonnes
        root.getChildren().add(line);

        // Ajouter une ligne sous le texte de bienvenue
        Line lineSeparator = new Line();
        lineSeparator.startXProperty().bind(root.widthProperty().multiply(0));
        lineSeparator.endXProperty().bind(root.widthProperty().multiply(1));
        lineSeparator.setStroke(Color.BLACK);

        // Ajouter la ligne en dessous du texte de bienvenue à la grille
        GridPane.setConstraints(lineSeparator, 0, 2, 2, 1, HPos.CENTER, VPos.CENTER); // Span sur 2 colonnes
        root.getChildren().add(lineSeparator);

        // Créez un conteneur pour les rectangles et les étiquettes
        GridPane rectanglesContainer = new GridPane();
        rectanglesContainer.setVgap(9);

        // Créez 9 rectangles cliquables avec les étiquettes correspondantes
        Label label1 = createClickableLabel("évolution des Utilisateurs");
        Label label2 = createClickableLabel("évolution des Location d'avions");
        Label label4 = createClickableLabel("évolution des Location de parking");
        Label label5 = createClickableLabel("évolution des inscription Licences");
        Label label6 = createClickableLabel("Liste des Avions");
        Label label7 = createClickableLabel("Liste des Services");
        Label label8 = createClickableLabel("Liste des utilisateurs");
        Label label9 = createClickableLabel("Quitter l'application");

        // Ajoutez les rectangles et les étiquettes dans le conteneur
        rectanglesContainer.addRow(0, label1);
        rectanglesContainer.addRow(1, label2);
        rectanglesContainer.addRow(2, label4);
        rectanglesContainer.addRow(3, label5);
        rectanglesContainer.addRow(4, label6);
        rectanglesContainer.addRow(5, label7);
        rectanglesContainer.addRow(6, label8);
        rectanglesContainer.addRow(7, label9);

        // Ajoutez le conteneur des rectangles à la grille
        GridPane.setConstraints(rectanglesContainer, 0, 3); // Placez-le à la première colonne, à partir de la ligne 4
        root.getChildren().add(rectanglesContainer);

        // Créez un conteneur pour le graphique
        GridPane chartContainer = new GridPane();
        chartContainer.setAlignment(Pos.CENTER); // Centrer le contenu verticalement et horizontalement
        chartContainer.setPadding(new Insets(9));
        chartContainer.setMinWidth(300); // Définissez la largeur minimale du conteneur du graphique
        chartContainer.setMaxWidth(300); // Définissez la largeur maximale du conteneur du graphique
        GridPane.setConstraints(chartContainer, 1, 3, 1, 9); // Placez-le à la deuxième colonne, à partir de la ligne 4 et prenez 9 lignes de hauteur
        GridPane.setHalignment(chartContainer, HPos.CENTER); // Centrer horizontalement le graphique dans la grille
        root.getChildren().add(chartContainer);

        // Créez le graphique
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Graphique");
        chart.setMinWidth(500); // Définissez la largeur minimale du graphique
        chart.setMaxWidth(500); // Définissez la largeur maximale du graphique
        chartContainer.getChildren().add(chart);

        // Définir la taille minimale de la fenêtre
        primaryStage.setMinWidth(BASE_WINDOW_WIDTH);
        primaryStage.setMinHeight(BASE_WINDOW_HEIGHT);

        // Appliquer le style au dernier bouton cliqué lors de l'initialisation
        if (lastClickedButton != null) {
            lastClickedButton.setStyle("-fx-background-color: gray; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 1;");
        }

        Scene scene = new Scene(root, BASE_WINDOW_WIDTH, BASE_WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label createClickableLabel(String labelText) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 12px;");
        label.setPrefWidth(200);
        label.setPrefHeight(50);
        label.setAlignment(Pos.CENTER);

        // Appliquer les styles CSS directement dans le code
        label.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");

        // Vérifiez si le texte du label commence par "Liste"
        if (labelText.equals("Liste des utilisateurs")) {
            label.setOnMouseClicked(event -> {
                chart.setVisible(false);
                openUserListWindow();
            });
        } else if (labelText.equals("Liste des Services")) {
            label.setOnMouseClicked(event -> {
                chart.setVisible(false);
                openServicesListWindow(); // Ouvrir la fenêtre de la liste des commandes
            });
        } else if (labelText.equals("Liste des Avions")) {
            label.setOnMouseClicked(event -> {
                chart.setVisible(false);
                openPlanesListWindow(); // Ouvrir la fenêtre de la liste des articles
            });
        } else if (labelText.equals("évolution des Utilisateurs")){
            label.setOnMouseClicked(event -> {
                // Mettez à jour le graphique avec les données de la base de données
                chart.setVisible(true);
                updateChartUsers();
            });
        } else if (labelText.equals("évolution des Location d'avions")) {
            label.setOnMouseClicked(event -> {
                // Mettez à jour le graphique avec les données de la base de données
                chart.setVisible(true);
               updateChartPlanes();
            });
        }else if (labelText.equals("évolution des Location de parking")) {
            label.setOnMouseClicked(event -> {
                // Mettez à jour le graphique avec les données de la base de données
                chart.setVisible(true);
                   updateChartParkings();
            });
        }else if (labelText.equals("évolution des inscription Licences")) {
            label.setOnMouseClicked(event -> {
                // Mettez à jour le graphique avec les données de la base de données
                chart.setVisible(true);
              updateChartLicences();
            });
        }else if (labelText.equals("Quitter l'application")) {
            label.setOnMouseClicked(event -> {
                System.exit(0);
            });

        }




        // Ajouter les événements de souris pour l'effet de clic et la couleur du bouton
        label.setOnMousePressed(event -> {
            lastClickedButton = label;
            label.setStyle("-fx-background-color: gray; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 1;");
        });

        label.setOnMouseReleased(event -> {
            lastClickedButton = null;
            label.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");
        });

        return label;
    }

    private void openUserListWindow() {
        UserListWindow userListWindow = new UserListWindow();
        userListWindow.showAndWait(); // Utilisez la méthode showAndWait() au lieu de show()
    }

    private void openPlanesListWindow() {
        PlanesListWindow planesListWindow = new PlanesListWindow();
        planesListWindow.showAndWait();
    }

    private void openServicesListWindow() {
        ServicesListWindow servicesListWindow = new ServicesListWindow();
        servicesListWindow.showAndWait();
    }

    private void updateChartUsers() {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean success = new AtomicBoolean(false);

        new Thread(() -> {
            Properties properties = new Properties();

            try (FileInputStream input = new FileInputStream("config.properties")) {
                properties.load(input);
                token = properties.getProperty("token");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Response<ResponseBody> response = ApiClient.getRetrofitInstance()
                        .create(ApiClient.Users.class)
                        .getusers(new JsonObject(),token)
                        .execute();

                if (response.isSuccessful()) {
                    Gson gson = createCustomGson();
                    JsonObject jsonObject = gson.fromJson(response.body().string(), JsonObject.class);
                    if (jsonObject.has("users")) {
                        ArrayList<Users> usersList = gson.fromJson(jsonObject.get("users"), new TypeToken<ArrayList<Users>>() {}.getType());
                        TreeMap<Month, Integer> usersByMonth = new TreeMap<>();
                        // Obtenez l'année actuelle
                        int currentYear = Year.now().getValue();
                        for (Users user : usersList) {
                            YearMonth creationDate = YearMonth.from(user.getAccountCreationTime());
                            if (creationDate.getYear() == currentYear) {
                                Month month = creationDate.getMonth();
                                usersByMonth.put(month, usersByMonth.getOrDefault(month, 0) + 1);
                            }
                        }
                        XYChart.Series<String, Number> series = new XYChart.Series<>();
                        series.setName("Utilisateurs créés par mois");
                        for (Month month : usersByMonth.keySet()) {
                            String monthName = month.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault());
                            int count = usersByMonth.get(month);
                            series.getData().add(new XYChart.Data<>(monthName, count));
                        }
                        Platform.runLater(() -> {
                            chart.getData().clear();
                            chart.getData().add(series);
                        });
                        success.set(true);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void updateChartPlanes() {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean success = new AtomicBoolean(false);

        new Thread(() -> {
            Properties properties = new Properties();

            try (FileInputStream input = new FileInputStream("config.properties")) {
                properties.load(input);
                token = properties.getProperty("token");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Response<ResponseBody> response = ApiClient.getRetrofitInstance()
                        .create(ApiClient.Planes.class)
                        .getplanesreservations(new JsonObject(),token)
                        .execute();

                if (response.isSuccessful()) {
                    Gson gson = createCustomGson();
                    JsonObject jsonObject = gson.fromJson(response.body().string(), JsonObject.class);
                    if (jsonObject.has("planereservations")) {
                        ArrayList<PlanesReservation> reservationsList = gson.fromJson(jsonObject.get("planereservations"), new TypeToken<ArrayList<PlanesReservation>>() {}.getType());
                        TreeMap<Month, Integer> activitiesByMonth = new TreeMap<>();
                        // Obtenez l'année actuelle
                        int currentYear = Year.now().getValue();
                        for (PlanesReservation reservation : reservationsList) {
                            YearMonth startDate = YearMonth.from(reservation.getStart_time());
                            if (startDate.getYear() == currentYear) {
                                Month month = startDate.getMonth();
                                activitiesByMonth.put(month, activitiesByMonth.getOrDefault(month, 0) + 1);
                            }
                        }
                        XYChart.Series<String, Number> series = new XYChart.Series<>();
                        series.setName("Activités par mois");
                        for (Month month : activitiesByMonth.keySet()) {
                            String monthName = month.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault());
                            int count = activitiesByMonth.get(month);
                            series.getData().add(new XYChart.Data<>(monthName, count));
                        }
                        Platform.runLater(() -> {
                            chart.getData().clear();
                            chart.getData().add(series);
                        });
                        success.set(true);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void updateChartParkings() {
        ArrayList<ParkingReservation> parkingsList = new ArrayList<>();

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
            ApiClient.Parking getParking = ApiClient.getRetrofitInstance().create(ApiClient.Parking.class);
            JsonObject requestBody2 = new JsonObject();
            Call<ResponseBody> call2 = getParking.getreservation(requestBody2,token);

            try {
                Response<ResponseBody> response2 = call2.execute();
                if (response2.isSuccessful()) {
                    assert response2.body() != null;
                    String responseString = response2.body().string();
                    Gson gson = createCustomGson(); // Utiliser la méthode pour créer une instance de Gson avec la désérialisation personnalisée
                    JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
                    if (jsonObject.has("parkingreservations")) {
                        ArrayList<ParkingReservation> parkingList = gson.fromJson(jsonObject.get("parkingreservations"), new TypeToken<ArrayList<ParkingReservation>>() {
                        }.getType());
                        parkingsList.addAll(parkingList);
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

        // Créez une TreeMap pour stocker le nombre d'utilisateurs créés par mois
        TreeMap<Month, Integer> ordersByMonth = new TreeMap<>();

// Obtenez l'année actuelle
        int currentYear = Year.now().getValue();

// Parcourez la liste des commandes et comptez le nombre de commandes par mois pour l'année actuelle
        for (ParkingReservation reservation : parkingsList) {
            YearMonth sendDate = YearMonth.from(reservation.getStart_time());
            if (sendDate.getYear() == currentYear) {
                Month month = sendDate.getMonth();
                ordersByMonth.put(month, ordersByMonth.getOrDefault(month, 0) + 1);
            }
        }

// Créez une série de données pour le graphique
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Commandes effectuées par mois");

// Parcourez la TreeMap et créez des objets XYChart.Data avec le nom du mois et le nombre de commandes
        for (Month month : ordersByMonth.keySet()) {
            String monthName = month.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault());
            int count = ordersByMonth.get(month);
            series.getData().add(new XYChart.Data<>(monthName, count));
        }

// Effacez les données existantes du graphique
        chart.getData().clear();

// Ajoutez la nouvelle série de données au graphique
        chart.getData().add(series);

    }

    private void updateChartLicences() {
        ArrayList<CourseParticipation> coursesList = new ArrayList<>();

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
            ApiClient.Course getcourses = ApiClient.getRetrofitInstance().create(ApiClient.Course.class);
            JsonObject requestBody2 = new JsonObject();
            Call<ResponseBody> call2 = getcourses.getreservation(requestBody2,token);

            try {
                Response<ResponseBody> response2 = call2.execute();
                if (response2.isSuccessful()) {
                    assert response2.body() != null;
                    String responseString = response2.body().string();
                    Gson gson = createCustomGson(); // Utiliser la méthode pour créer une instance de Gson avec la désérialisation personnalisée
                    JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
                    if (jsonObject.has("courseparticipations")) {
                        ArrayList<CourseParticipation> courseList = gson.fromJson(jsonObject.get("courseparticipations"), new TypeToken<ArrayList<CourseParticipation>>() {
                        }.getType());
                        coursesList.addAll(courseList);
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

        // Créez une TreeMap pour stocker le nombre d'utilisateurs créés par mois
        TreeMap<Month, Integer> usersByMonth = new TreeMap<>();

// Obtenez l'année actuelle
        int currentYear = Year.now().getValue();

// Parcourez la liste des diplômes et comptez le nombre d'utilisateurs par mois pour l'année actuelle
        for (CourseParticipation diplomes : coursesList) {
            YearMonth dateObtention = YearMonth.from(diplomes.getParticipation_date_time());
            if (dateObtention.getYear() == currentYear) {
                Month month = dateObtention.getMonth();
                usersByMonth.put(month, usersByMonth.getOrDefault(month, 0) + 1);
            }
        }

// Créez une série de données pour le graphique
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Diplômes obtenus par mois");

// Parcourez la TreeMap et créez des objets XYChart.Data avec le nom du mois et le nombre d'utilisateurs
        for (Month month : usersByMonth.keySet()) {
            String monthName = month.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault());
            int count = usersByMonth.get(month);
            series.getData().add(new XYChart.Data<>(monthName, count));
        }

// Effacez les données existantes du graphique
        chart.getData().clear();

// Ajoutez la nouvelle série de données au graphique
        chart.getData().add(series);

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
