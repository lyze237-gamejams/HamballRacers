package dev.lyze.hamballracers.eventSystem.events;

import dev.lyze.hamballracers.eventSystem.data.LapFinishedEventData;

public class LapFinishedEvent extends Event<LapFinishedEventData> {
    public LapFinishedEvent(LapFinishedEventData data) {
        super(data);
    }
}
