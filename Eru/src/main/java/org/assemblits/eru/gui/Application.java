package org.assemblits.eru.gui;

import org.assemblits.eru.gui.component.StartUpWizard;
import org.assemblits.eru.gui.controller.EruController;
import org.assemblits.eru.gui.controller.EruPreloaderController;
import org.assemblits.eru.gui.service.ApplicationArgsPreparer;
import org.assemblits.eru.gui.service.ApplicationLoader;
import org.assemblits.eru.preferences.EruPreferences;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static com.sun.javafx.application.LauncherImpl.launchApplication;

@Slf4j
@EntityScan("org.assemblits.eru")
@SpringBootApplication
@ComponentScan("org.assemblits.eru")
@EnableJpaRepositories("org.assemblits.eru")
public class Application extends javafx.application.Application {

    private ConfigurableApplicationContext applicationContext;
    @Autowired
    private EruController eruController;
    private EruPreferences eruPreferences;

    public static void main(String[] args) {
        launchApplication(Application.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        if (!eruPreferences.getApplicationConfigured().getValue()) {
            StartUpWizard startUpWizard = new StartUpWizard(stage, eruPreferences);
            startUpWizard.startWizard();
        }
        ApplicationLoader applicationLoader = new ApplicationLoader(this, getClass(), getApplicationParameters());
        Preloader preloaderWindow = loadPreloader(applicationLoader);
        applicationLoader.setOnSucceeded(event -> {
            ApplicationLoader.Result loadResult = (ApplicationLoader.Result) event.getSource().getValue();

            applicationContext = loadResult.getApplicationContext();
            ApplicationContextHolder.setApplicationContext(applicationContext);
            eruController.startEru(loadResult.getProject(), stage);
            eruPreferences = applicationContext.getBeanFactory().getBean(EruPreferences.class);
        });

        preloaderWindow.start(stage);
        applicationLoader.start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.close();
    }

    @Override
    public void init() throws Exception {
        super.init();
        eruPreferences = new EruPreferences();
    }

    private Preloader loadPreloader(ApplicationLoader applicationLoader) {
        return new Preloader() {
            @Override
            public void start(Stage primaryStage) throws Exception {
                FXMLLoader loader = new FXMLLoader();
                loader.setController(new EruPreloaderController(applicationLoader));
                loader.setLocation(getClass().getResource("/views/Preloader.fxml"));
                Parent preLoader = loader.load();

                primaryStage.setScene(new Scene(preLoader));
                primaryStage.show();
            }
        };
    }

    private String[] getApplicationParameters() {
        ApplicationArgsPreparer environmentPreparer = new ApplicationArgsPreparer();
        final Parameters parametersObject = getParameters();
        return environmentPreparer.prepare(parametersObject.getRaw().toArray(new String[0]), eruPreferences);
    }


    public enum Theme {
        DEFAULT, DARK;
        public String getStyleSheetURL(){
            return "/views/styles/" + name() + ".css";
        }
    }
}