package dev.lyze.hamballracers.screens.level.map.spawners.properties;

import com.badlogic.gdx.maps.MapProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PlayerSpawnerMapProperties extends MapProperties {
    private int index;
}
