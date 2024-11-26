package world;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Node;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the world where entities, including the player, bullets, etc., exist.
 */
public final class World {

    private final List<Node> entities = new ArrayList<>();
    private final Pane root;
    private final Rectangle worldBorder;

    public World(final int width, final int height) {
        root = new Pane();
        root.setPrefSize(width, height);

        // Create a world border (a rectangle around the world)
        worldBorder = new Rectangle(0, 0, width, height);
        worldBorder.setStroke(Color.BLACK); // Border color
        worldBorder.setFill(Color.TRANSPARENT); // No fill, just a border
        worldBorder.setStrokeWidth(3); // Set the border thickness

        // Add the world border to the root Pane
        root.getChildren().add(worldBorder);
    }

    /**
     * Adds a Node (e.g., bullet or player) to the world.
     *
     * @param entity The entity to be added.
     */
    public void addEntity(Node entity) {
        entities.add(entity);
        root.getChildren().add(entity);
    }

    /**
     * Removes a Node (e.g., bullet or player) from the world.
     *
     * @param entity The entity to be removed.
     */
    public void removeEntity(Node entity) {
        entities.remove(entity);
        root.getChildren().remove(entity);
    }

    /**
     * Gets the root of the world (Pane).
     *
     * @return The root Pane of the world.
     */
    public Pane getRoot() {
        return root;
    }

    /**
     * Updates the world, can be used to update all entities.
     */
    public void update() {
        // Here you can update all entities (e.g., player, bullets)
    }
}
