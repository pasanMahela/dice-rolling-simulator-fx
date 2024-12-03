package org.example.dicerollsimulator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Random;

public class DiceSimulator extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Main Layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // Title
        Label title = new Label("🎲 Dice Simulator 🎲");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.DARKBLUE);

        // Input Section
        HBox inputSection = new HBox(10);
        inputSection.setAlignment(Pos.CENTER);

        TextField diceInput = new TextField();
        diceInput.setPromptText("Enter number of dice");
        diceInput.setPrefWidth(150);

        Button rollButton = new Button("Roll Dice");
        rollButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        inputSection.getChildren().addAll(diceInput, rollButton);

        // Results Section
        Label resultTitle = new Label("Results:");
        resultTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        resultTitle.setTextFill(Color.DARKGREEN);

        FlowPane diceDisplay = new FlowPane();
        diceDisplay.setHgap(10);
        diceDisplay.setVgap(10);
        diceDisplay.setAlignment(Pos.CENTER);
        diceDisplay.setPadding(new Insets(10));
        diceDisplay.setStyle("-fx-background-color: #F5F5F5; -fx-border-color: #CCCCCC; -fx-border-width: 2px;");

        // Error Label
        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        // Roll Dice Functionality (Action Handling)
        Runnable rollDiceAction = () -> {
            diceDisplay.getChildren().clear();
            errorLabel.setText("");

            String input = diceInput.getText();
            try {
                int numberOfDice = Integer.parseInt(input);

                if (numberOfDice <= 0) {
                    errorLabel.setText("Please enter a positive number.");
                } else {
                    Random rand = new Random();
                    for (int i = 0; i < numberOfDice; i++) {
                        int rollNumber = rand.nextInt(6) + 1;
                        diceDisplay.getChildren().add(createDiceGraphic(rollNumber));
                    }
                }
            } catch (NumberFormatException ex) {
                errorLabel.setText("That is not a valid number.");
            }

            // Refocus on the input field
            diceInput.requestFocus();
            diceInput.selectAll();
        };

        // Button Click Action
        rollButton.setOnAction(e -> rollDiceAction.run());

        // Enter Key Handling for TextField
        diceInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                rollDiceAction.run();
            }
        });

        // Adding Components to Root
        root.getChildren().addAll(title, inputSection, resultTitle, diceDisplay, errorLabel);

        // Setting the Scene
        Scene scene = new Scene(root, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dice Simulator");
        primaryStage.show();
    }

    // Method to Create Dice Graphic
    private VBox createDiceGraphic(int value) {
        VBox diceBox = new VBox();
        diceBox.setAlignment(Pos.CENTER);
        diceBox.setPadding(new Insets(10));
        diceBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        diceBox.setPrefSize(80, 80);

        Label diceLabel = new Label();
        diceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        diceLabel.setText(getDiceFace(value));
        diceLabel.setTextFill(Color.DARKBLUE);

        diceBox.getChildren().add(diceLabel);
        return diceBox;
    }

    // Dice Face Characters
    private String getDiceFace(int value) {
        return switch (value) {
            case 1 -> "⚀";
            case 2 -> "⚁";
            case 3 -> "⚂";
            case 4 -> "⚃";
            case 5 -> "⚄";
            case 6 -> "⚅";
            default -> "?";
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}

