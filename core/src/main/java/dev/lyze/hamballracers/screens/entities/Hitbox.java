package dev.lyze.hamballracers.screens.entities;

import com.badlogic.gdx.math.Rectangle;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
public class Hitbox {
    private float drawWidth, drawHeight;
    private float halfHitboxWidth, halfHitboxHeight;
    private float hitboxOffsetX, hitboxOffsetY;

    public Hitbox(float drawWidth, float drawHeight, float halfHitboxWidth, float halfHitboxHeight) {
        this.drawWidth = drawWidth;
        this.drawHeight = drawHeight;
        this.halfHitboxWidth = halfHitboxWidth;
        this.halfHitboxHeight = halfHitboxHeight;
    }

    public Hitbox(float drawWidth, float drawHeight, float halfHitboxWidth, float halfHitboxHeight, float hitboxOffsetX, float hitboxOffsetY) {
        this.drawWidth = drawWidth;
        this.drawHeight = drawHeight;
        this.halfHitboxWidth = halfHitboxWidth;
        this.halfHitboxHeight = halfHitboxHeight;
        this.hitboxOffsetX = hitboxOffsetX;
        this.hitboxOffsetY = hitboxOffsetY;
    }

    public Rectangle generateRectangle(float x, float y) {
        return new Rectangle(x - halfHitboxWidth / 2f + hitboxOffsetX, y - halfHitboxHeight / 2f + hitboxOffsetY, halfHitboxWidth, halfHitboxHeight);
    }

    public void debugDraw(ShapeDrawer drawer, float x, float y) {
        drawer.rectangle(x - halfHitboxWidth / 2f + hitboxOffsetX, y - halfHitboxHeight / 2f + hitboxOffsetY, halfHitboxWidth, halfHitboxHeight);
    }
}
