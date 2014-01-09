package com.zenjava.jfxflow.util;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FXUtils {

    public static Popup getAlwaysOnTop(Node root) {
        return PopupBuilder.create()
                .content(root)
                .build();
    }

    public static <X, Y> ObservableList<XYChart.Data<X, Y>> getFirstSeries(XYChart<X, Y> chart) {
        return chart.getData().get(0).getData();
    }

    /**
     * Find a {@link javafx.scene.Node} within a {@link javafx.scene.Parent} by it's ID.
     * <p/>
     * This might not cover all possible {@link javafx.scene.Parent} implementations but it's
     * a decent crack. Implementations all seem to have their
     * own method of storing children along side the usual
     * {@link javafx.scene.Parent#getChildrenUnmodifiable()} method.
     *
     * @param parent The parent of the node you're looking for.
     * @param id     The ID of node you're looking for.
     * @return The {@link javafx.scene.Node} with a matching ID or {@code null}.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getChildByID(Parent parent, String id) throws WidgetNotFoundException {
        T child = findChildByID(parent, id);
        if (child == null) {
            throw new WidgetNotFoundException(parent, id);
        } else {
            return child;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getChildByStyleClass(Parent parent, String id) throws WidgetNotFoundException {
        T child = findChildByStyleClass(parent, id);
        if (child == null) {
            throw new WidgetNotFoundException(parent, id);
        } else {
            return child;
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T findChildByID(Parent parent, String id) throws WidgetNotFoundException {

        String nodeId;

        if (parent instanceof TitledPane) {
            TitledPane titledPane = (TitledPane) parent;
            Node content = titledPane.getContent();
            nodeId = content.idProperty().get();

            if (nodeId != null && nodeId.equals(id)) {
                return (T) content;
            }

            if (content instanceof Parent) {
                T child = findChildByID((Parent) content, id);
                if (child != null) {
                    return child;
                }
            }
        }

        for (Node node : parent.getChildrenUnmodifiable()) {
            nodeId = node.idProperty().get();
            if (nodeId != null && nodeId.equals(id)) {
                return (T) node;
            }

            if (node instanceof SplitPane) {
                SplitPane splitPane = (SplitPane) node;
                for (Node itemNode : splitPane.getItems()) {
                    nodeId = itemNode.idProperty().get();

                    if (nodeId != null && nodeId.equals(id)) {
                        return (T) itemNode;
                    }

                    if (itemNode instanceof Parent) {
                        T child = findChildByID((Parent) itemNode, id);

                        if (child != null) {
                            return child;
                        }
                    }
                }
            } else if (node instanceof Accordion) {
                Accordion accordion = (Accordion) node;
                for (TitledPane titledPane : accordion.getPanes()) {
                    nodeId = titledPane.idProperty().get();

                    if (nodeId != null && nodeId.equals(id)) {
                        return (T) titledPane;
                    }

                    T child = findChildByID(titledPane, id);

                    if (child != null) {
                        return child;
                    }
                }
            } else if (node instanceof Parent) {
                T child = findChildByID((Parent) node, id);

                if (child != null) {
                    return child;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> T findChildByStyleClass(Parent parent, String styleClass) throws WidgetNotFoundException {

        ObservableList<String> styleClasses;

        if (parent instanceof TitledPane) {
            TitledPane titledPane = (TitledPane) parent;
            Node content = titledPane.getContent();
            styleClasses = content.getStyleClass();

            if (styleClasses != null && styleClasses.contains(styleClass)) {
                return (T) content;
            }

            if (content instanceof Parent) {
                T child = findChildByStyleClass((Parent) content, styleClass);
                if (child != null) {
                    return child;
                }
            }
        }

        for (Node node : parent.getChildrenUnmodifiable()) {
            styleClasses = node.getStyleClass();
            if (styleClasses != null && styleClasses.contains(styleClass)) {
                return (T) node;
            }

            if (node instanceof SplitPane) {
                SplitPane splitPane = (SplitPane) node;
                for (Node itemNode : splitPane.getItems()) {
                    styleClasses = itemNode.getStyleClass();

                    if (styleClasses != null && styleClasses.contains(styleClass)) {
                        return (T) itemNode;
                    }

                    if (itemNode instanceof Parent) {
                        T child = findChildByStyleClass((Parent) itemNode, styleClass);

                        if (child != null) {
                            return child;
                        }
                    }
                }
            } else if (node instanceof Accordion) {
                Accordion accordion = (Accordion) node;
                for (TitledPane titledPane : accordion.getPanes()) {
                    styleClasses = titledPane.getStyleClass();

                    if (styleClasses != null && styleClasses.contains(styleClass)) {
                        return (T) titledPane;
                    }

                    T child = findChildByStyleClass(titledPane, styleClass);

                    if (child != null) {
                        return child;
                    }
                }
            } else if (node instanceof Parent) {
                T child = findChildByStyleClass((Parent) node, styleClass);

                if (child != null) {
                    return child;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static void addChildsByStyleClass(Parent parent, String styleClass, Collection result) throws WidgetNotFoundException {

        ObservableList<String> styleClasses;

        if (parent instanceof TitledPane) {
            TitledPane titledPane = (TitledPane) parent;
            Node content = titledPane.getContent();
            styleClasses = content.getStyleClass();

            if (styleClasses != null && styleClasses.contains(styleClass)) {
                result.add(content);
            }

            if (content instanceof Parent) {
                addChildsByStyleClass((Parent) content, styleClass, result);
            }
        }

        for (Node node : parent.getChildrenUnmodifiable()) {
            styleClasses = node.getStyleClass();
            if (styleClasses != null && styleClasses.contains(styleClass)) {
                result.add(node);
            }

            if (node instanceof SplitPane) {
                SplitPane splitPane = (SplitPane) node;
                for (Node itemNode : splitPane.getItems()) {
                    styleClasses = itemNode.getStyleClass();

                    if (styleClasses != null && styleClasses.contains(styleClass)) {
                        result.add(itemNode);
                    }

                    if (itemNode instanceof Parent) {
                        addChildsByStyleClass((Parent) itemNode, styleClass, result);
                    }
                }
            } else if (node instanceof Accordion) {
                Accordion accordion = (Accordion) node;
                for (TitledPane titledPane : accordion.getPanes()) {
                    styleClasses = titledPane.getStyleClass();

                    if (styleClasses != null && styleClasses.contains(styleClass)) {
                        result.add(titledPane);
                    }

                    addChildsByStyleClass(titledPane, styleClass, result);
                }
            } else if (node instanceof Parent) {
                addChildsByStyleClass((Parent) node, styleClass, result);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Collection<T> findChildsByStyleClass(Parent parent, String styleClass) throws WidgetNotFoundException {
        List<T> result = new ArrayList<T>();
        addChildsByStyleClass(parent, styleClass, result);
        return result;
    }

    public static void changeFontSize(Labeled node, int change) {
        node.setFont(new Font(node.getFont().getName(), Font.getDefault().getSize() + change));
    }

    public static void changeFontSize(Parent parent, String styleClass, int change) {
        Collection<Labeled> nodes = findChildsByStyleClass(parent, styleClass);
        for (Labeled node : nodes) {
            changeFontSize(node, change);
        }
    }

    public static void changePrefHeightOfChildTextFields(Parent parent, String styleClass, int prefHeight) {
        Collection<TextField> textFields = findChildsByStyleClass(parent, styleClass);
        for (TextField textField : textFields) {
            textField.setPrefHeight(prefHeight);
        }
    }


}
