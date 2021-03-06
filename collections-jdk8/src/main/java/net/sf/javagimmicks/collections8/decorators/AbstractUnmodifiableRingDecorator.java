package net.sf.javagimmicks.collections8.decorators;

import java.io.Serializable;
import java.util.Collection;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

import net.sf.javagimmicks.collections8.AbstractRing;
import net.sf.javagimmicks.collections8.Ring;

/**
 * A basic class for unmodifiable {@link Ring} decorators that simply forwards
 * all read-calls to an internal delegate instance.
 */
public abstract class AbstractUnmodifiableRingDecorator<E> extends AbstractRing<E> implements Serializable
{
   private static final long serialVersionUID = -5807259095947621928L;

   protected final Ring<E> _decorated;

   protected AbstractUnmodifiableRingDecorator(final Ring<E> decorated)
   {
      _decorated = decorated;
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
   public boolean isEmpty()
   {
      return getDecorated().isEmpty();
   }

   @Override
   public int size()
   {
      return getDecorated().size();
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

   @Override
   public void forEach(final Consumer<? super E> action)
   {
      getDecorated().forEach(action);
   }

   @Override
   public Spliterator<E> spliterator()
   {
      return getDecorated().spliterator();
   }

   @Override
   public Stream<E> stream()
   {
      return getDecorated().stream();
   }

   @Override
   public Stream<E> parallelStream()
   {
      return getDecorated().parallelStream();
   }

   protected Ring<E> getDecorated()
   {
      return _decorated;
   }
}
