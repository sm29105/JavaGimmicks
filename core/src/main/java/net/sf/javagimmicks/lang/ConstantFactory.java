package net.sf.javagimmicks.lang;

/**
 * An implementation of {@link Factory} which holds a constant object of the
 * required type and returns it upon every call to {@link #create()}
 */
public class ConstantFactory<E> implements Factory<E>
{
   private final E _value;

   /**
    * Create a new instance for the given constant value
    * 
    * @param value
    *           the value to use for every creation call
    */
   public ConstantFactory(final E value)
   {
      _value = value;
   }

   @Override
   public E create()
   {
      return _value;
   }
}