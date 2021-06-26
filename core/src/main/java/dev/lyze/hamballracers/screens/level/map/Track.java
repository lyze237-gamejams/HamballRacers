package dev.lyze.hamballracers.screens.level.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Track {
    private TextureRegion thumbnail;
    private String map;
    private String name;
}
