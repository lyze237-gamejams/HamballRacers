package dev.lyze.hamballracers.eventSystem;

import dev.lyze.hamballracers.eventSystem.events.Event;
import dev.lyze.hamballracers.utils.Logger;
import lombok.var;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventManager
{
    private static final Logger<EventManager> logger = new Logger<>(EventManager.class);

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

        logger.logInfo("Firing event " + event);

        for (EventListener<?> eventListener : events.get(event.getClass()))
        {
            if (eventListener.shouldCastAndFire(event))
                eventListener.castAndFire(event);
        }
    }

    public void removeListenersOfBind(Object bind)
    {
        var removed = 0;
        for (Map.Entry<Class<? extends Event<?>>, ArrayList<EventListener<?>>> entry : events.entrySet())
            removed += entry.getValue().removeIf(l -> bind.equals(l.getBind())) ? 1 : 0;

        logger.logInfo("Removed " + removed + " listeners for bind " + bind);
    }
}
