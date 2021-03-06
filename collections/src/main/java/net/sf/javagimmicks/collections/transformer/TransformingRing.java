package net.sf.javagimmicks.collections.transformer;

import net.sf.javagimmicks.collections.AbstractRing;
import net.sf.javagimmicks.collections.Ring;
import net.sf.javagimmicks.collections.RingCursor;
import net.sf.javagimmicks.transform.Transforming;
import net.sf.javagimmicks.util.Function;

class TransformingRing<F, T>
   extends AbstractRing<T>
   implements Transforming<F, T>
{
   protected final Ring<F> _internalRing;
   private final Function<F, T> _tansformer;

   /**
    * @deprecated Use TranformerUtils.decorate() instead
    */
   @Deprecated
   public TransformingRing(Ring<F> ring, Function<F, T> tansformer)
   {
      _internalRing = ring;
      _tansformer = tansformer;
   }
   
   public Function<F, T> getTransformerFunction()
   {
      return _tansformer;
   }

   public RingCursor<T> cursor()
   {
      return TransformerUtils.decorate(_internalRing.cursor(), getTransformerFunction());
   }

   @Override
   public int size()
   {
      return _internalRing.size();
   }
}
