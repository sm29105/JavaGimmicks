package net.sf.javagimmicks.collections8.decorators;

import java.util.Comparator;
import java.util.SortedSet;

/**
 * A basic class for unmodifiable {@link SortedSet} decorators that simply
 * forwards all read-calls to an internal delegate instance.
 */
public abstract class AbstractUnmodifiableSortedSetDecorator<E> extends AbstractUnmodifiableSetDecorator<E> implements
      SortedSet<E>
{
   private static final long serialVersionUID = -1294628897899404764L;

   protected AbstractUnmodifiableSortedSetDecorator(final SortedSet<E> decorated)
   {
      super(decorated);
   }

   @Override
   public Comparator<? super E> comparator()
   {
      return getDecorated().comparator();
   }

   @Override
   public E first()
   {
      return getDecorated().first();
   }

   @Override
   public E last()
   {
      return getDecorated().last();
   }

   @Override
   protected SortedSet<E> getDecorated()
   {
      return (SortedSet<E>) super.getDecorated();
   }
}
