package net.sf.javagimmicks.collections.transformer;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

import net.sf.javagimmicks.transform.Transforming;
import net.sf.javagimmicks.util.Function;

class TransformingCollection<F, T>
	extends AbstractCollection<T>
	implements Transforming<F, T>
{
   protected final Collection<F> _internalCollection;
   private final Function<F, T> _transformer;
   
   /**
    * @deprecated Use TranformerUtils.decorate() instead
    */
   @Deprecated
   public TransformingCollection(Collection<F> collection, Function<F, T> transformer)
   {
      _internalCollection = collection;
      _transformer = transformer;
   }
   
   public Function<F, T> getTransformerFunction()
	{
		return _transformer;
	}

   @Override
   public Iterator<T> iterator()
   {
      return TransformerUtils.decorate(_internalCollection.iterator(), getTransformerFunction());
   }

   @Override
   public int size()
   {
      return _internalCollection.size();
   }

   @Override
   public void clear()
   {
      _internalCollection.clear();
   }
}
