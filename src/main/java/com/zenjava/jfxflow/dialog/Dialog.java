package com.zenjava.jfxflow.dialog;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.math.BigDecimal;

public class Dialog {
    private ReadOnlyObjectWrapper<DialogOwner> owner;
    private Stage stage;
    private StringProperty title;
    private ObjectProperty<Parent> content;
    private BorderPane contentArea;
    private String stylesheet;
    private boolean headerVisible = true;
    private BorderPane root;

    public Dialog() {
        this(null, null, true);
    }

    public Dialog(String stylesheet) {
        this(null, stylesheet, true);
    }

    public Dialog(String stylesheet, boolean headerVisible) {
        this(null, stylesheet, headerVisible);
    }

    public Dialog(String title, String stylesheet, boolean headerVisible) {
        this.headerVisible = headerVisible;
        this.owner = new ReadOnlyObjectWrapper<DialogOwner>();
        this.stage = new Stage(StageStyle.TRANSPARENT);
        this.title = new SimpleStringProperty(title);
        this.stylesheet = stylesheet;
        if (stylesheet == null) {
            this.stylesheet = Dialog.class.getResource("/styles/jfxflow-dialog.css").toExternalForm();
        }
        this.content = new SimpleObjectProperty<Parent>();


        this.content.addListener(new ChangeListener<Parent>() {
            public void changed(ObservableValue<? extends Parent> source, Parent oldNode, Parent newNode) {
                contentArea.setCenter(newNode);
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
            }
        });

        this.stage.showingProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> source, Boolean oldValue, Boolean newValue) {
                DialogOwner owner = Dialog.this.owner.get();
                if (owner != null) {
                    if (newValue) {
                        owner.addDialog(Dialog.this);
                    } else {
                        owner.removeDialog(Dialog.this);
                    }
                }
            }
        });

        buildSkin();
    }

    public ReadOnlyBooleanProperty showingProperty() {
        return this.stage.showingProperty();
    }

    public ReadOnlyObjectProperty<DialogOwner> ownerProperty() {
        return owner;
    }

    public DialogOwner getOwner() {
        return owner.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public ObjectProperty<Parent> contentProperty() {
        return content;
    }

    public Node getContent() {
        return content.get();
    }

    public void setContent(Parent content) {
        this.content.set(content);
    }

    public void show(Node node) {
        DialogOwner owner = null;
        Node nextNode = node;
        while (nextNode != null) {
            if (nextNode instanceof DialogOwner) {
                owner = (DialogOwner) nextNode;
            }
            nextNode = nextNode.getParent();
        }

        if (owner != null) {
            this.owner.set(owner);
            final Window window = node.getScene().getWindow();
            stage.initOwner(window);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.sizeToScene();
            stage.show();
            stage.setX(window.getX() + (window.getWidth() / 2) - (stage.getWidth() / 2));
            stage.setY(BigDecimal.ZERO.max(BigDecimal.valueOf(window.getY() + (window.getHeight() / 2) - (stage.getHeight() / 2))).intValue());
        } else {
            throw new NoDialogOwnerException(String.format(
                    "Node '%s' must have a parent that implements DialogOwner to be able to show a Dialog", node));
        }
    }

    public void hide() {
        stage.hide();
    }

    protected void buildSkin() {
        root = new BorderPane();
        root.getStyleClass().add("dialog");
        root.getStylesheets().add(stylesheet);

        if (headerVisible) {
            BorderPane header = new BorderPane();
            header.getStyleClass().add("header");

            Label titleLabel = new Label(title.get());
            titleLabel.textProperty().bind(title);
            titleLabel.getStyleClass().add("title");
            header.setLeft(titleLabel);

            Button closeButton = new Button("Close");
            Label closeIcon = new Label();
            closeIcon.getStyleClass().add("close-icon");
            closeButton.setGraphic(closeIcon);
            closeButton.getStyleClass().add("close-button");
            closeButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    hide();
                }
            });
            header.setRight(closeButton);

            root.setTop(header);
        }

        contentArea = new BorderPane();
        contentArea.getStyleClass().add("content");
        root.setCenter(contentArea);
    }

    public String getStylesheet() {
        return stylesheet;
    }

    public void setStylesheet(String stylesheet) {
        this.stylesheet = stylesheet;
    }

    public boolean isHeaderVisible() {
        return headerVisible;
    }

    public void setHeaderVisible(boolean headerVisible) {
        this.headerVisible = headerVisible;
    }
}
