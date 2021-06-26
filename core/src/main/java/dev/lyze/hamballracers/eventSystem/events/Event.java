package dev.lyze.hamballracers.eventSystem.events;

import dev.lyze.hamballracers.eventSystem.data.EventData;
import lombok.Data;

@Data
public abstract class Event<TData extends EventData>
{
    private final TData data;
}
