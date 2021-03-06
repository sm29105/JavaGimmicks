package net.sf.javagimmicks.collections8.event;

import java.util.List;

import net.sf.javagimmicks.event.Event;
import net.sf.javagimmicks.event.Observable;

/**
 * Represents a change in a {@link Observable} {@link List} - like
 * {@link ObservableEventList}.
 */
public interface ListEvent<E> extends Event<ListEvent<E>>
{
   /**
    * The possible types of {@link ListEvent}s.
    */
   enum Type
   {
      /**
       * One ore more elements were added
       */
      ADDED,

      /**
       * One ore more elements were updated
       */
      UPDATED,

      /**
       * One or more elements were removed
       */
      REMOVED
   }

   /**
    * The type of the event
    * 
    * @return the type of the event
    * @see Type
    */
   Type getType();

   /**
    * The starting index (inclusive) of the range within the {@link List} that
    * was changed
    * 
    * @return the starting index of the changed range
    */
   int getFromIndex();

   /**
    * The end index (exclusive) of the range within the {@link List} that was
    * changed
    * 
    * @return the end index of the changed range
    */
   int getToIndex();

   /**
    * Returns the removed/added elements in case of {@link Type#REMOVED} or
    * {@link Type#ADDED} - or the replaced elements in case of
    * {@link Type#UPDATED}.
    * 
    * @return the removed/added elements or the replaced elements of an update
    */
   List<E> getElements();

   /**
    * In case of {@link Type#UPDATED}) returns the new values
    * 
    * @return the new elements of an update
    */
   List<E> getNewElements();
}