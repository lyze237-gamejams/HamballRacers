package dev.lyze.hamballracers.eventSystem.events;

import dev.lyze.hamballracers.eventSystem.data.LapStartedEventData;

public class LapStartedEvent extends Event<LapStartedEventData> {
    public LapStartedEvent(LapStartedEventData data) {
        super(data);
    }
}
