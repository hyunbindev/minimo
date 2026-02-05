module com.hyunbindev.minimo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires eu.hansolo.tilesfx;

    opens com.hyunbindev.minimo to javafx.fxml;
    exports com.hyunbindev.minimo;
}