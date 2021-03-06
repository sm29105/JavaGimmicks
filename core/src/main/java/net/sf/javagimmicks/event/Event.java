package net.sf.javagimmicks.event;

/**
 * A generic event interface which represents events that are fired by some
 * {@link Observable} and will be reported to some {@link EventListener}s.
 * 
 * @param <Evt>
 *           the concrete {@link Event} type
 */
public interface Event<Evt extends Event<Evt>>
{
   /**
    * The source {@link Observable} that fired this {@link Event}
    * 
    * @return the source of this {@link Event}
    */
   Observable<Evt> getSource();
}
