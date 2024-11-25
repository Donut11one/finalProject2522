package player;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the player in the world.
 */
public final class Player {

    private static final int PLAYER_WIDTH   = 32;
    private static final int PLAYER_HEIGHT  = 32;
    private static final int PLAYER_SPEED   = 8;

    private final Rectangle player;
    private final Set<KeyCode> pressedKeys = new HashSet<>();

    /**
     * Initializes the player at a given position.
     *
     * @param startX The starting X position of the player.
     * @param startY The starting Y position of the player.
     */
    public Player(final double startX, final double startY) {
        this.player = new Rectangle(PLAYER_WIDTH, PLAYER_HEIGHT, Color.BLUE);
        this.player.setX(startX);
        this.player.setY(startY);
    }

    /**
     * Moves the player within the world boundaries.
     *
     * @param dx          Change in X position.
     * @param dy          Change in Y position.
     * @param worldWidth  The width of the world.
     * @param worldHeight The height of the world.
     */
    public void move(final double dx, final double dy, final double worldWidth, final double worldHeight) {
        final double newX = Math.max(0, Math.min(worldWidth - PLAYER_WIDTH, this.getPlayer().getX() + dx));
        final double newY = Math.max(0, Math.min(worldHeight - PLAYER_HEIGHT, this.getPlayer().getY() + dy));

        this.getPlayer().setX(newX);
        this.getPlayer().setY(newY);
    }

    /**
     * Enables controls for the player to move using WASD keys.
     * @param scene       The scene to listen for key presses.
     * @param worldWidth  The width of the world.
     * @param worldHeight The height of the world.
     * @param camera      The root Pane to move the camera.
     */
    public void enableControls(final Scene scene, final double worldWidth, final double worldHeight, final Pane camera) {
        scene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                double dx = 0, dy = 0;

                if (pressedKeys.contains(KeyCode.W)) dy -= PLAYER_SPEED;
                if (pressedKeys.contains(KeyCode.S)) dy += PLAYER_SPEED;
                if (pressedKeys.contains(KeyCode.A)) dx -= PLAYER_SPEED;
                if (pressedKeys.contains(KeyCode.D)) dx += PLAYER_SPEED;

                // Move player
                move(dx, dy, worldWidth, worldHeight);

                // Update camera to follow the player (unrestricted by boundaries)
                final double offsetX = -getPlayer().getX() + scene.getWidth() / 2 - PLAYER_WIDTH / 2;
                final double offsetY = -getPlayer().getY() + scene.getHeight() / 2 - PLAYER_HEIGHT / 2;
                camera.setTranslateX(offsetX);
                camera.setTranslateY(offsetY);
            }
        }.start();
    }

    /**
     * Gets the visual representation of the player.
     *
     * @return The player rectangle.
     */
    public Rectangle getPlayer() {
        return this.player;
    }
}
