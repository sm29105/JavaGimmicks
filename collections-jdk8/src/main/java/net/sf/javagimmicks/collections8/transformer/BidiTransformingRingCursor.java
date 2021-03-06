package net.sf.javagimmicks.collections8.transformer;

import net.sf.javagimmicks.collections8.RingCursor;
import net.sf.javagimmicks.transform8.BidiFunction;
import net.sf.javagimmicks.transform8.BidiTransforming;

class BidiTransformingRingCursor<F, T>
   extends TransformingRingCursor<F, T>
   implements BidiTransforming<F, T>
{
   BidiTransformingRingCursor(RingCursor<F> ringCursor, BidiFunction<F, T> transformer)
   {
      super(ringCursor, transformer);
   }
   
   public BidiFunction<F, T> getTransformerBidiFunction()
   {
      return (BidiFunction<F, T>)getTransformerFunction();
   }

   @Override
   public void insertAfter(T value)
   {
      _internalRingCursor.insertAfter(getTransformerBidiFunction().applyReverse(value));
   }

   @Override
   public void insertBefore(T value)
   {
      _internalRingCursor.insertBefore(getTransformerBidiFunction().applyReverse(value));
   }
}
