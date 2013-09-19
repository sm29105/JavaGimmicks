package net.sf.javagimmicks.collections.event.cdi;

import java.util.Collection;

import net.sf.javagimmicks.collections.event.CollectionEvent;
import net.sf.javagimmicks.collections.event.ObservableEventCollection;

/**
 * A CDI compatible wrapper around a {@link CollectionEvent}.
 * <p>
 * CDI event objects may not have type parameters, so the type information needs
 * to be erased for the wrapped {@link CollectionEvent}.
 */
public class CDICollectionEvent implements CollectionEvent<Object>
{
   private final CollectionEvent<Object> _origin;

   /**
    * Create a new instance for the given {@link CollectionEvent}.
    * 
    * @param origin
    *           the original {@link CollectionEvent}
    */
   @SuppressWarnings("unchecked")
   public CDICollectionEvent(final CollectionEvent<?> origin)
   {
      _origin = (CollectionEvent<Object>) origin;
   }

   @Override
   public Type getType()
   {
      return _origin.getType();
   }

   @Override
   public Collection<Object> getElements()
   {
      return _origin.getElements();
   }

   @Override
   public ObservableEventCollection<Object> getSource()
   {
      return _origin.getSource();
   }

   public CollectionEvent<Object> getOriginalEvent()
   {
      return _origin;
   }
}
