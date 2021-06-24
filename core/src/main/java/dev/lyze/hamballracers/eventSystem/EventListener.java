package dev.lyze.hamballracers.eventSystem;

import dev.lyze.hamballracers.eventSystem.events.Event;

public abstract class EventListener<TEvent extends Event<?>>
{
    private final Class<TEvent> clazz;
    private final Object bind;

    public EventListener(Class<TEvent> clazz)
    {
        this.clazz = clazz;
        this.bind = null;
    }

    /**
     *
     * @param clazz
     * @param bind Bind is used to group event listeners together and to remove them at the same time. (E.g. events for level 1, then remove them once the player plays level 2)
     *             Via {@link EventManager#removeListenersOfBind(Object)}
     */
    public EventListener(Class<TEvent> clazz, Object bind)
    {
        this.clazz = clazz;
        this.bind = bind;
    }

    protected abstract void fire(TEvent event);

    @SuppressWarnings("unchecked")
    final <T extends Event<?>> void castAndFire(T event)
    {
        fire((TEvent) event);
    }

    /**
     * A check to see if the event listener should get called
     * @param event
     * @return true if the event should call the listener, false if it should be ignored.
     */
    protected boolean shouldFire(TEvent event)
    {
        return true;
    }

    @SuppressWarnings("unchecked")
    final <T extends Event<?>> boolean shouldCastAndFire(T event)
    {
        return shouldFire((TEvent) event);
    }

    Class<TEvent> getClazz()
    {
        return clazz;
    }

    Object getBind()
    {
        return bind;
    }
}
