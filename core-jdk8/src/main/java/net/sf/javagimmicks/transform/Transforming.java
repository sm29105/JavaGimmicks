package net.sf.javagimmicks.transform;

import java.util.function.Function;

import javax.xml.transform.Transformer;

/**
 * An interface for objects that carry a {@link Function} for internally
 * transforming objects.
 * 
 * @param <F>
 *           the source object type of the contained {@link Function}
 * @param <T>
 *           the target object type of the contained {@link Function}
 */
public interface Transforming<F, T>
{
   /**
    * Returns the internal {@link Function}
    * 
    * @return the internal {@link Function}
    */
   public Function<F, T> getTransformer();

   /**
    * Returns the {@link Transformer} of a given object if it is
    * {@link Transforming}.
    * 
    * @param transforming
    *           the object to drag the {@link Transformer} out from
    * @return the {@link Transformer} contained in the given object
    * @throws IllegalArgumentException
    *            if the given object is not a {@link Transforming} instance
    * @see #isTransforming(Object)
    */
   public static Function<?, ?> getTransformer(final Object transforming)
   {
      if (!isTransforming(transforming))
      {
         throw new IllegalArgumentException("Object is not transforming!");
      }
   
      return ((Transforming<?, ?>) transforming).getTransformer();
   }

   /**
    * Checks if a given object is transforming (if it is an instance of
    * {@link Transforming}).
    * 
    * @param o
    *           the object to check
    * @return if the object is {@link Transforming}
    */
   public static boolean isTransforming(final Object o)
   {
      return o instanceof Transforming<?, ?>;
   }
}