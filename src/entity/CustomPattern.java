package entity;

import java.util.List;

/**
 * Interface for custom bullet patterns.
 */
@FunctionalInterface
public interface CustomPattern {
    List<Bullet> generate(double centerX, double centerY);
}