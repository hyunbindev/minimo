module com.hyunbindev.minimo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires kotlin.stdlib;
    requires org.slf4j;
    requires org.controlsfx.controls;
    requires eu.hansolo.tilesfx;
    requires java.desktop;

    requires javafx.swing;
    opens com.hyunbindev.minimo to javafx.fxml;
    exports com.hyunbindev.minimo;
}