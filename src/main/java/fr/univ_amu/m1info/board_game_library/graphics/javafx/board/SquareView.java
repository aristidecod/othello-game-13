package fr.univ_amu.m1info.board_game_library.graphics.javafx.board;

import fr.univ_amu.m1info.board_game_library.graphics.Shape;
import fr.univ_amu.m1info.board_game_library.graphics.javafx.color.JavaFXColorMapper;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SquareView extends StackPane {
    private static final double STROKE_WIDTH = 1.0;
    private static final double SHAPE_RATIO = 0.8; // Taille de la forme par rapport à la case

    private final int column;
    private final int row;
    private final Rectangle squareBackground;
    private final Group shapes;
    private final int squareSize;

    public SquareView(int column, int row, int squareSize) {
        this.column = column;
        this.row = row;
        this.squareSize = squareSize;
        this.shapes = new Group();

        initializeView();
        this.squareBackground = createBackground();
        setupChildren();
    }

    private void initializeView() {
        setWidth(squareSize);
        setHeight(squareSize);
        getStyleClass().add("board-cell");
    }

    private Rectangle createBackground() {
        Rectangle background = new Rectangle(squareSize, squareSize);
        background.setStroke(Color.GREY);
        background.setStrokeWidth(STROKE_WIDTH);
        background.setFill(Color.WHITE);
        return background;
    }

    private void setupChildren() {
        getChildren().addAll(squareBackground, shapes);
    }

    public void setColor(Color backgroundColor) {
        squareBackground.setFill(backgroundColor);
    }

    public void setAction(BoardActionOnClick positionHandler) {
        if (positionHandler != null) {
            setOnMouseClicked(event -> positionHandler.onClick(row, column));
        }
    }

    public void addShapeWithCustomSize(Shape shape, fr.univ_amu.m1info.board_game_library.graphics.Color color, double sizeRatio) {
        double effectiveSize = squareSize * sizeRatio;
        javafx.scene.shape.Shape shapeFX = ShapeFactory.makeShape(shape, effectiveSize);
        shapeFX.setFill(JavaFXColorMapper.getJavaFXColor(color));
        shapeFX.setStroke(Color.BLACK);
        shapeFX.setStrokeWidth(STROKE_WIDTH);

        // Position absolue par rapport à la case
        double centerX = squareSize / 2.0 - effectiveSize / 2.0;
        double centerY = squareSize / 2.0 - effectiveSize / 2.0;

        shapeFX.setLayoutX(centerX);
        shapeFX.setLayoutY(centerY);

        // Réinitialiser les translations
        shapeFX.setTranslateX(0);
        shapeFX.setTranslateY(0);

        shapes.getChildren().add(shapeFX);
    }

    public void addShape(Shape shape, fr.univ_amu.m1info.board_game_library.graphics.Color color) {
        addShapeWithCustomSize(shape, color, SHAPE_RATIO);
    }

    public void removeShapes() {
        shapes.getChildren().clear();
    }

    public void clearShapes() {
        shapes.getChildren().clear();
    }
}