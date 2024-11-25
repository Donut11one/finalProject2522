package world;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents the game world with a visible border.
 */
public final class World {

    private final Pane root;
    private final int width;
    private final int height;
    private final Rectangle border;

    /**
     * Creates a new World with the given dimensions and a visible border.
     *
     * @param width  the width of the world
     * @param height the height of the world
     */
    public World(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.root = new Pane();

        // Create a visible border
        this.border = new Rectangle(0, 0, width, height);
        border.setStroke(Color.BLACK); // Border color
        border.setStrokeWidth(3);      // Border thickness
        border.setFill(null);          // Transparent fill

        // Add the border to the root
        root.getChildren().add(border);
    }

    /**
     * Adds an entity (Node) to the world.
     *
     * @param entity The entity to add.
     */
    public void addEntity(final javafx.scene.Node entity) {
        this.getRoot().getChildren().add(entity);
    }
    /**
     * Gets the root Pane of the world.
     *
     * @return the root Pane
     */
    public Pane getRoot() {
        return root;
    }

    /**
     * Gets the width of the world.
     *
     * @return the width of the world
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the world.
     *
     * @return the height of the world
     */
    public int getHeight() {
        return height;
    }
}
