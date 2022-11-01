module ca.bcit.comp2522.termproject.secretwonders {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.bcit.comp2522.termproject.secretwonders to javafx.fxml;
    exports ca.bcit.comp2522.termproject.secretwonders;
}