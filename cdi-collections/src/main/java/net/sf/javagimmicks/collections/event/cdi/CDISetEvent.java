package net.sf.javagimmicks.collections.event.cdi;

import net.sf.javagimmicks.collections.event.ObservableEventSet;
import net.sf.javagimmicks.collections.event.SetEvent;

/**
 * A CDI compatible wrapper around a {@link SetEvent}.
 * <p>
 * CDI event objects may not have type parameters, so the type information needs
 * to be erased for the wrapped {@link SetEvent}.
 */
public class CDISetEvent implements SetEvent<Object>
{
   private final SetEvent<Object> _origin;

   /**
    * Create a new instance for the given {@link SetEvent}.
    * 
    * @param origin
    *           the original {@link SetEvent}
    */
   @SuppressWarnings("unchecked")
   public CDISetEvent(final SetEvent<?> origin)
   {
      _origin = (SetEvent<Object>) origin;
   }

   @Override
   public Type getType()
   {
      return _origin.getType();
   }

   @Override
   public Object getElement()
   {
      return _origin.getElement();
   }

   @Override
   public ObservableEventSet<Object> getSource()
   {
      return _origin.getSource();
   }

   public SetEvent<Object> getOriginalEvent()
   {
      return _origin;
   }
}
