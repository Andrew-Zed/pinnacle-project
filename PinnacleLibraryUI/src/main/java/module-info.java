module com.pinochio.helloworldfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires static lombok;
    requires com.google.gson;
    requires java.net.http;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;

    opens com.pinnacle.library to javafx.fxml;
    exports com.pinnacle.library;
    exports com.pinnacle.library.controller;
    opens com.pinnacle.library.controller to javafx.fxml;
}