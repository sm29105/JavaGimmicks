package net.sf.javagimmicks.collections.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.sf.javagimmicks.collections.event.ListEvent.Type;
import net.sf.javagimmicks.event.EventListener;
import net.sf.javagimmicks.event.Observable;
import net.sf.javagimmicks.event.ObservableBase;

/**
 * A {@link List} decorator that serves as an {@link Observable} for
 * {@link ListEvent}s.
 */
public class ObservableEventList<E> extends AbstractEventList<E> implements
      Observable<ListEvent<E>>
{
   private static final long serialVersionUID = -6317396247733734848L;

   protected final ObservableBase<ListEvent<E>> _helper = new ObservableBase<ListEvent<E>>();

   /**
    * Wraps a new {@link ObservableEventList} around a given {@link List}.
    * 
    * @param decorated
    *           the {@link List} to wrap around
    */
   public ObservableEventList(final List<E> decorated)
   {
      super(decorated);
   }

   @Override
   public <L extends EventListener<ListEvent<E>>> void addEventListener(final L listener)
   {
      _helper.addEventListener(listener);
   }

   @Override
   public <L extends EventListener<ListEvent<E>>> void removeEventListener(final L listener)
   {
      _helper.removeEventListener(listener);
   }

   @Override
   public ObservableEventList<E> subList(final int fromIndex, final int toIndex)
   {
      return new ObservableEventSubList<E>(this, fromIndex, toIndex);
   }

   @Override
   protected void fireElementsAdded(final int index, final Collection<? extends E> elements)
   {
      final ListEvent<E> event = new ListEventImpl(
            Type.ADDED,
            index,
            index + elements.size(),
            Collections.unmodifiableList(new ArrayList<E>(elements)));

      _helper.fireEvent(event);
   }

   @Override
   protected void fireElementUpdated(final int index, final E element, final E newElement)
   {
      final ListEvent<E> event = new ListEventImpl(
            Type.UPDATED,
            index,
            index,
            Collections.singletonList(element),
            Collections.singletonList(newElement));

      _helper.fireEvent(event);
   }

   @Override
   protected void fireElementRemoved(final int index, final E element)
   {
      final ListEvent<E> event = new ListEventImpl(
            Type.REMOVED,
            index,
            index,
            Collections.singletonList(element));

      _helper.fireEvent(event);
   }

   private class ListEventImpl implements ListEvent<E>
   {
      protected final Type _type;
      protected final int _fromIndex;
      protected final int _toIndex;
      protected final List<E> _elements;
      protected final List<E> _newElements;

      public ListEventImpl(final Type type, final int fromIndex, final int toIndex, final List<E> element,
            final List<E> newElement)
      {
         _type = type;
         _fromIndex = fromIndex;
         _toIndex = toIndex;

         _elements = element;
         _newElements = newElement;
      }

      public ListEventImpl(final Type type, final int fromIndex, final int toIndex, final List<E> element)
      {
         this(type, fromIndex, toIndex, element, null);
      }

      @Override
      public Type getType()
      {
         return _type;
      }

      @Override
      public int getFromIndex()
      {
         return _fromIndex;
      }

      @Override
      public int getToIndex()
      {
         return _toIndex;
      }

      @Override
      public List<E> getElements()
      {
         return _elements;
      }

      @Override
      public List<E> getNewElements()
      {
         return _newElements;
      }

      @Override
      public ObservableEventList<E> getSource()
      {
         return ObservableEventList.this;
      }
   }

   protected static class ObservableEventSubList<E> extends ObservableEventList<E>
   {
      private static final long serialVersionUID = 1996968483016862598L;

      protected final ObservableEventList<E> _parent;
      protected final int _offset;

      protected ObservableEventSubList(final ObservableEventList<E> parent, final int fromIndex, final int toIndex)
      {
         super(parent._decorated.subList(fromIndex, toIndex));

         _parent = parent;
         _offset = fromIndex;
      }

      @Override
      protected void fireElementsAdded(final int index, final Collection<? extends E> elements)
      {
         super.fireElementsAdded(index, elements);
         _parent.fireElementsAdded(index + _offset, elements);
      }

      @Override
      protected void fireElementRemoved(final int index, final E element)
      {
         super.fireElementRemoved(index, element);
         _parent.fireElementRemoved(index + _offset, element);
      }

      @Override
      protected void fireElementUpdated(final int index, final E element, final E newElement)
      {
         super.fireElementUpdated(index, element, newElement);
         _parent.fireElementUpdated(index + _offset, element, newElement);
      }
   }
}
