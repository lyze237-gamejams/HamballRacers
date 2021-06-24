package dev.lyze.hamballracers.eventSystem.events;

import dev.lyze.hamballracers.eventSystem.data.EventData;

public abstract class Event<TData extends EventData>
{
    private final TData data;

    public Event(TData data)
    {
        this.data = data;
    }

    public TData getData()
    {
        return data;
    }
}
