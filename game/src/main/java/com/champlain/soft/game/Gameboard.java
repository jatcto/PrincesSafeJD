package com.champlain.soft.game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gameboard extends Application {
    // 🔹 These are the core constants you had set up
    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 800;
    private static final int COLUMNS = 10;
    private static final int ROWS = 10;
    private static final int NUM_BOMBS = 4;

    enum CellType {
        GRASS, PLAYER, PRINCESS, BOMB, WALL
    }

    // 🔹 The original matrix using your CellType enum
    private CellType[][] matrix = new CellType[ROWS][COLUMNS];

    private Image imgGrass, imgWall, imgPlayer, imgPrincess, imgBomb;

    private GridPane grid;

    @Override
    public void start(Stage stage) {
        loadImages();
        initMatrix();

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        drawBoard(grid);

        BorderPane root = new BorderPane();
        root.setCenter(grid);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        stage.setTitle("Rescue the Princess");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void loadImages() {
        imgGrass    = new Image(getClass().getResourceAsStream("/images/grass.png"));
        imgWall     = new Image(getClass().getResourceAsStream("/images/wall.png"));
        imgPlayer   = new Image(getClass().getResourceAsStream("/images/player.png"));
        imgPrincess = new Image(getClass().getResourceAsStream("/images/princess.png"));
        imgBomb     = new Image(getClass().getResourceAsStream("/images/bomb.png"));
    }

    private void initMatrix() {
        // 🔹 Filling the board with grass initially
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                matrix[r][c] = CellType.GRASS;
            }
        }

        for (int c = 0; c < COLUMNS; c++) {
            matrix[0][c]        = CellType.WALL;
            matrix[ROWS - 1][c] = CellType.WALL;
        }
        for (int r = 0; r < ROWS; r++) {
            matrix[r][0]           = CellType.WALL;
            matrix[r][COLUMNS - 1] = CellType.WALL;
        }

        matrix[1][1] = CellType.PLAYER;

        // 🔹 Your original object placements
        List<int[]> available = new ArrayList<>();
        for (int r = 1; r < ROWS - 1; r++) {
            for (int c = 1; c < COLUMNS - 1; c++) {
                if (!(r == 1 && c == 1)) {
                    available.add(new int[]{r, c});
                }
            }
        }

        Collections.shuffle(available);

        int[] p = available.remove(0);
        matrix[p[0]][p[1]] = CellType.PRINCESS;

        for (int i = 0; i < NUM_BOMBS; i++) {
            int[] b = available.remove(0);
            matrix[b[0]][b[1]] = CellType.BOMB;
        }
    }

    private void drawBoard(GridPane grid) {
        grid.getChildren().clear();

        // 🔹 Calculation for the tile size to fill the scene width
        double tileWidth  = (double) SCENE_WIDTH  / COLUMNS;
        double tileHeight = (double) SCENE_HEIGHT / ROWS;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {

                StackPane cell = new StackPane();
                cell.setPrefSize(tileWidth, tileHeight);

                // Logic to display the correct icon or color based on the matrix
                if (matrix[row][col] == CellType.WALL) {
                    cell.getChildren().add(makeImageView(imgWall, tileWidth, tileHeight));
                } else if (matrix[row][col] == CellType.PLAYER) {
                    cell.getChildren().add(makeImageView(imgGrass, tileWidth, tileHeight));
                    cell.getChildren().add(makeImageView(imgPlayer, tileWidth, tileHeight));
                } else if (matrix[row][col] == CellType.PRINCESS) {
                    cell.getChildren().add(makeImageView(imgGrass, tileWidth, tileHeight));
                    cell.getChildren().add(makeImageView(imgPrincess, tileWidth, tileHeight));
                } else if (matrix[row][col] == CellType.BOMB) {
                    cell.getChildren().add(makeImageView(imgGrass, tileWidth, tileHeight));
                    cell.getChildren().add(makeImageView(imgBomb, tileWidth, tileHeight));
                } else {
                    cell.getChildren().add(makeImageView(imgGrass, tileWidth, tileHeight));
                }

                grid.add(cell, col, row);
            }
        }
    }

    private ImageView makeImageView(Image img, double width, double height) {
        ImageView iv = new ImageView(img);
        iv.setFitWidth(width);
        iv.setFitHeight(height);
        iv.setPreserveRatio(false);
        return iv;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
