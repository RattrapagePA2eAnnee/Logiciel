module com.example.demo1 {
    exports com.example.demo1.model;
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires okhttp3;
    requires gson;
    requires java.sql;
    requires itextpdf;

    opens com.example.demo1 to javafx.fxml, gson;
    opens com.example.demo1.model to gson;
    exports com.example.demo1;

}
