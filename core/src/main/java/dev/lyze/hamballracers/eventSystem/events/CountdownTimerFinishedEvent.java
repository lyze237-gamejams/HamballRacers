package dev.lyze.hamballracers.eventSystem.events;

import dev.lyze.hamballracers.eventSystem.data.CountdownTimerFinishedEventData;

public class CountdownTimerFinishedEvent extends Event<CountdownTimerFinishedEventData>{
    public CountdownTimerFinishedEvent(CountdownTimerFinishedEventData data) {
        super(data);
    }
}
