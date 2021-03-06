package net.sf.javagimmicks.collections.decorators;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;

/**
 * A basic class for unmodifiable {@link List} decorators that simply forwards
 * all read-calls to an internal delegate instance.
 */
public abstract class AbstractUnmodifiableListDecorator<E> extends AbstractList<E> implements Serializable
{
   private static final long serialVersionUID = 8953157735186723748L;

   protected final List<E> _decorated;

   protected AbstractUnmodifiableListDecorator(final List<E> decorated)
   {
      _decorated = decorated;
   }

   @Override
   public E get(final int index)
   {
      return getDecorated().get(index);
   }

   @Override
   public int size()
   {
      return getDecorated().size();
   }

   @Override
   public boolean contains(final Object o)
   {
      return getDecorated().contains(o);
   }

   @Override
   public boolean containsAll(final Collection<?> c)
   {
      return getDecorated().containsAll(c);
   }

   @Override
   public int indexOf(final Object o)
   {
      return getDecorated().indexOf(o);
   }

   @Override
   public boolean isEmpty()
   {
      return getDecorated().isEmpty();
   }

   @Override
   public int lastIndexOf(final Object o)
   {
      return getDecorated().lastIndexOf(o);
   }

   @Override
   public Object[] toArray()
   {
      return getDecorated().toArray();
   }

   @Override
   public <T> T[] toArray(final T[] a)
   {
      return getDecorated().toArray(a);
   }

   @Override
   public String toString()
   {
      return getDecorated().toString();
   }

   protected List<E> getDecorated()
   {
      return _decorated;
   }
}
