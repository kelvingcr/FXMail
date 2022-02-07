module br.com.kelvingcr.emailautosend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;


    opens br.com.kelvingcr.emailautosend to javafx.fxml;
    exports br.com.kelvingcr.emailautosend;
}