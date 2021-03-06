/**
 * 
 */
package net.sf.javagimmicks.collections8.transformer;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Function;

import net.sf.javagimmicks.collections8.RingCursor;
import net.sf.javagimmicks.transform8.Transforming;

class TransformingRingCursor<F, T>
   implements Transforming<F, T>, RingCursor<T>
{
   protected final RingCursor<F> _internalRingCursor;
   private final Function<F, T> _transformer;
   
   TransformingRingCursor(RingCursor<F> ringCursor, Function<F, T> transformer)
   {
      _internalRingCursor = ringCursor;
      _transformer = transformer;
   }
   
   public Function<F, T> getTransformerFunction()
   {
      return _transformer;
   }
   
   public int size()
   {
      return _internalRingCursor.size();
   }

   public T get()
   {
      return getTransformerFunction().apply(_internalRingCursor.get());
   }

   public void insertAfter(T value)
   {
      throw new UnsupportedOperationException();
   }

   public void insertBefore(T value)
   {
      throw new UnsupportedOperationException();
   }

   public T next()
   {
      return getTransformerFunction().apply(_internalRingCursor.next());
   }

   public T previous()
   {
      return getTransformerFunction().apply(_internalRingCursor.previous());
   }

   public T remove()
   {
      return getTransformerFunction().apply(_internalRingCursor.remove());
   }

   public RingCursor<T> cursor()
   {
      return TransformerUtils.decorate(_internalRingCursor.cursor(), getTransformerFunction());
   }

   public Iterator<T> iterator()
   {
      return TransformerUtils.decorate(_internalRingCursor.iterator(), getTransformerFunction());
   }

   public Spliterator<T> spliterator()
   {
      return TransformerUtils.decorate(_internalRingCursor.spliterator(), getTransformerFunction());
   }
}