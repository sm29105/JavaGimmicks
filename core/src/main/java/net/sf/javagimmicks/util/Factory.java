package net.sf.javagimmicks.util;

/**
 * This is a simple interface for factory classes - classes the generate
 * instances of (other) classes.
 * 
 * @param <E>
 *           the type of the instances to create
 */
public interface Factory<E> extends Supplier<E>
{
   /**
    * Create a new instance of the respective type
    * 
    * @return the resulting instance
    */
   @Override
   public E get();
}