package dev.lyze.hamballracers.eventSystem;

import dev.lyze.hamballracers.eventSystem.events.Event;

import java.util.ArrayList;
import java.util.HashMap;

public class EventManager
{
    private final HashMap<Class<? extends Event<?>>, ArrayList<EventListener<?>>> events = new HashMap<>();

    public <TEvent extends Event<?>> void addListener(EventListener<TEvent> listener)
    {
        if (!events.containsKey(listener.getClazz()))
            events.put(listener.getClazz(), new ArrayList<>());

        events.get(listener.getClazz()).add(listener);
    }

    public <TEvent extends Event<?>> void fire(TEvent event)
    {
        if (!events.containsKey(event.getClass()))
            return;

        for (EventListener<?> eventListener : events.get(event.getClass()))
        {
            if (eventListener.shouldCastAndFire(event))
                eventListener.castAndFire(event);
        }
    }

    public void removeListenersOfBind(Object bind)
    {
        events.forEach((clazz, listeners) -> listeners.removeIf(l -> bind.equals(l.getBind())));
    }
}
