package com.eru.gui.tables;

import com.eru.entities.Display;
import com.eru.gui.EruController;
import com.eru.scenebuilder.SceneBuilderStarter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.Optional;

@Log4j
public class DisplayTable extends EruTable<Display> {

    public DisplayTable(EruController eruController, SceneBuilderStarter sceneBuilderStarter) {
        super(eruController.getProject().getDisplays());

        TableColumn<Display, String> groupColumn = new TableColumn<>("Group");
        TableColumn<Display, String> userNameColumn = new TableColumn<>("Name");

        groupColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGroupName()));
        groupColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        groupColumn.prefWidthProperty().bind(this.widthProperty().multiply(0.50));

        userNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        userNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        userNameColumn.prefWidthProperty().bind(this.widthProperty().multiply(0.50));

        this.getColumns().addAll(
                groupColumn,
                userNameColumn);

        this.setEditable(false);
        this.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        this.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                sceneBuilderStarter.startSceneBuilder(this.getSelectionModel().getSelectedItem());
            }
        });
    }

    @Override
    public void addNewItem() {
        this.getSelectionModel().clearSelection();

        Dialog<Display> dialog = new Dialog<>();
        dialog.setTitle("Create new display");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField displayName = new TextField();
        displayName.setPromptText("Name");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(displayName, 1, 0);

        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);
        displayName.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                Display newDisplay = new Display();
                newDisplay.setGroupName("Displays");
                newDisplay.setName(displayName.getText());
                return newDisplay;
            }
            return null;
        });

        Optional<Display> result = dialog.showAndWait();


        result.ifPresent(display -> {
            this.items.add(display);
            this.getSelectionModel().select(display);
        });

        // *******************************************************************************
        // Implemented to solve : https://javafx-jira.kenai.com/browse/RT-32091
        // When a new object is added to the table, a new filteredList has to be created
        // and the items updated, because the filteredList is non-editable. So, despite the
        // filtered List is setted to the tableview, a list is used in the background. The
        // filtered list is only used to be able to filter using the textToFilter.
        //
        //Wrap ObservableList into FilteredList
        super.filteredItems = new FilteredList<>(this.items);
        super.setItems(this.filteredItems);

        // Check if a textToFilter is setted
        if (super.textToFilter != null) {
            setTextToFilter(textToFilter);
        }
    }

    @Override
    public void setTextToFilter(StringProperty textToFilter) {
        textToFilter.addListener(observable ->
                this.filteredItems.setPredicate(user ->
                        (textToFilter.getValue() == null
                                || textToFilter.getValue().isEmpty()
                                || user.getName().startsWith(textToFilter.getValue())
                                || user.getGroupName().startsWith(textToFilter.getValue()))
                )
        );
    }
}
