package dev.lyze.hamballracers.screens.level.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import dev.lyze.hamballracers.Constants;
import dev.lyze.hamballracers.eventSystem.EventListener;
import dev.lyze.hamballracers.eventSystem.events.CountdownTimerFinishedEvent;
import dev.lyze.hamballracers.screens.level.Level;
import dev.lyze.hamballracers.screens.level.entities.Hitbox;
import lombok.Data;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;

@Data
public class Block {
    private final Level level;

    private boolean icy;
    private float speedMultiplier;
    private float speedMultiplierTime;
    private boolean disappearAfterStart;
    private boolean forceSpeedMultiplierPenalty;
    private boolean chargeNitro;

    private boolean disableCollision;

    private int x, y;

    private ArrayList<Rectangle> collisionRectangles = new ArrayList<>();

    public Block(Level level, int x, int y, TiledMapTileLayer.Cell cell) {
        this.level = level;
        this.x = x;
        this.y = y;

        update(cell);
    }

    public void debugRender(ShapeDrawer drawer) {
        drawer.setColor(Color.RED);
        for (Rectangle rectangle : collisionRectangles) {
            drawer.rectangle(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        }

        if (icy) {
            drawer.setColor(Color.TEAL);
            drawer.rectangle(x * 8f + 1, y * 8f + 1, 6, 6);
        }

        drawer.setColor(Color.GREEN);
        drawer.rectangle(hitboxRectangle);
    }

    public void update(TiledMapTileLayer.Cell cell) {
        if (cell == null)
            return;

        var collisionObjects = cell.getTile().getObjects();
        if (collisionObjects.getCount() > 0) {
            for (MapObject collisionObject : collisionObjects) {
                var rect = new Rectangle(((RectangleMapObject) collisionObject).getRectangle());

                if ((cell.getRotation() == TiledMapTileLayer.Cell.ROTATE_90)) {
                    rect.setPosition(15 - rect.getY(), rect.getX());
                    rect.setSize(rect.getHeight(), rect.getWidth());
                } else if (cell.getRotation() == TiledMapTileLayer.Cell.ROTATE_180 || (cell.getFlipHorizontally() && cell.getFlipVertically())) {
                    rect.setPosition(16 - rect.getX() - rect.getWidth(), 16 - rect.getY() - rect.getHeight());
                } else if (cell.getRotation() == TiledMapTileLayer.Cell.ROTATE_270) {
                    rect.setPosition(rect.getY(), 15 - rect.getX() - rect.getWidth());
                    rect.setSize(rect.getHeight(), rect.getWidth());
                }

                rect.setPosition(x * 8 + rect.getX() / 2f, y * 8 + rect.getY() / 2f); // from 16 to 8
                rect.setSize(rect.getWidth() / 2f, rect.getHeight() / 2f);

                collisionRectangles.add(rect);
            }
        }

        if (cell.getTile().getProperties().get("icy", false, Boolean.class))
            setIcy(true);

        if (cell.getTile().getProperties().get("disappearAfterStart", false, Boolean.class)) {
            Constants.eventManager.addListener(new EventListener<CountdownTimerFinishedEvent>(CountdownTimerFinishedEvent.class) {
                @Override
                protected void fire(CountdownTimerFinishedEvent event) {
                    cell.setTile(null);
                    disableCollision = true;
                }
            });
        }

        setSpeedMultiplier(cell.getTile().getProperties().get("speed", 1f, Float.class));
        setSpeedMultiplierTime(cell.getTile().getProperties().get("speedMultiplierTime", 0f, Float.class));

        if (cell.getTile().getProperties().get("forceSpeedMultiplierPenalty", false, Boolean.class))
            setForceSpeedMultiplierPenalty(true);

        if (cell.getTile().getProperties().get("chargeNitro", false, Boolean.class))
            setChargeNitro(true);
    }

    public boolean isCollisionTile() {
        return !disableCollision && !collisionRectangles.isEmpty();
    }

    private final Rectangle hitboxRectangle = new Rectangle();
    public boolean isCollision(float x, float y, Hitbox hitbox) {
        hitbox.generateRectangle(x, y, hitboxRectangle);

        for (Rectangle rect : collisionRectangles) {
            if (hitboxRectangle.overlaps(rect))
                return true;
        }

        return false;
    }
}
