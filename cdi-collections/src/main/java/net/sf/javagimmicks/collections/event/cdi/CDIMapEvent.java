package net.sf.javagimmicks.collections.event.cdi;

import net.sf.javagimmicks.collections.event.MapEvent;
import net.sf.javagimmicks.collections.event.ObservableEventMap;

/**
 * A CDI compatible wrapper around a {@link MapEvent}.
 * <p>
 * CDI event objects may not have type parameters, so the type information needs
 * to be erased for the wrapped {@link MapEvent}.
 */
public class CDIMapEvent implements MapEvent<Object, Object>
{
   private final MapEvent<Object, Object> _origin;

   /**
    * Create a new instance for the given {@link MapEvent}.
    * 
    * @param origin
    *           the original {@link MapEvent}
    */
   @SuppressWarnings("unchecked")
   public CDIMapEvent(final MapEvent<?, ?> origin)
   {
      _origin = (MapEvent<Object, Object>) origin;
   }

   /**
    * Provides access to the wrapped {@link MapEvent}
    * 
    * @return the wrapped {@link MapEvent}
    */
   public MapEvent<Object, Object> getWrappedEvent()
   {
      return _origin;
   }

   @Override
   public ObservableEventMap<Object, Object> getSource()
   {
      return _origin.getSource();
   }

   @Override
   public Type getType()
   {
      return _origin.getType();
   }

   @Override
   public Object getKey()
   {
      return _origin.getKey();
   }

   @Override
   public Object getValue()
   {
      return _origin.getValue();
   }

   @Override
   public Object getNewValue()
   {
      return _origin.getNewValue();
   }
}
