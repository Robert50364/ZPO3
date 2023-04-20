module com.robson.lab3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.robson.lab3 to javafx.fxml;
    exports com.robson.lab3;
}