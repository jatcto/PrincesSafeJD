module com.champlain.soft.game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.champlain.soft.game to javafx.fxml;
    exports com.champlain.soft.game;
}