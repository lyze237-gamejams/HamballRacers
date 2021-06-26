package dev.lyze.hamballracers.eventSystem.data;

import dev.lyze.hamballracers.screens.level.entities.HamsterBall;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class LapFinishedEventData extends EventData {
    private int index;
    private HamsterBall hamsterBall;
}
