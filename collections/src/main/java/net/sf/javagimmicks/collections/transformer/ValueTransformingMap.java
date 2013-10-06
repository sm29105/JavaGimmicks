package net.sf.javagimmicks.collections.transformer;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import net.sf.javagimmicks.lang.Transformer;
import net.sf.javagimmicks.lang.Transforming;

class ValueTransformingMap<K, VF, VT>
   extends AbstractMap<K, VT>
   implements Transforming<VF, VT>
{
   protected final Map<K, VF> _internalMap;
   private final Transformer<VF, VT> _transformer;
   
   /**
    * @deprecated Use TranformerUtils.decorateValueBased() instead
    */
   @Deprecated
   public ValueTransformingMap(Map<K, VF> map, Transformer<VF, VT> valueTransformer)
   {
      _internalMap = map;
      _transformer = valueTransformer;
   }
   
   public Transformer<VF, VT> getTransformer()
   {
      return _transformer;
   }

   public void clear()
   {
      _internalMap.clear();
   }

   public boolean containsKey(Object key)
   {
      return _internalMap.containsKey(key);
   }

   public Set<Entry<K, VT>> entrySet()
   {
      return TransformerUtils.decorate(
         _internalMap.entrySet(),
         new ValueTransformingEntryTransformer<K, VF, VT>(getTransformer()));
   }
   
   public VT get(Object key)
   {
      VF result = _internalMap.get(key);
      
      if(result == null && !containsKey(key))
      {
         return null;
      }
      
      return transform(result);
   }

   public boolean isEmpty()
   {
      return _internalMap.isEmpty();
   }

   public Set<K> keySet()
   {
      return _internalMap.keySet();
   }

   public VT remove(Object key)
   {
      if(!containsKey(key))
      {
         return null;
      }
      
      VF result = _internalMap.remove(key);
      return transform(result);
   }

   public int size()
   {
      return _internalMap.size();
   }

   public Collection<VT> values()
   {
      return TransformerUtils.decorate(
         _internalMap.values(),
         getTransformer());
   }
   
   protected VT transform(VF element)
   {
      return getTransformer().transform(element);
   }
   
   protected static class ValueTransformingEntry<K, VF, VT>
      implements Entry<K, VT>, Transforming<VF, VT>
   {
      protected final Transformer<VF, VT> _transformer;
      protected final Entry<K, VF> _internalEntry;
      
      public ValueTransformingEntry(Entry<K, VF> entry, Transformer<VF, VT> transformer)
      {
         _transformer = transformer;
         _internalEntry = entry;
      }
      
      public Transformer<VF, VT> getTransformer()
      {
         return _transformer;
      }

      public K getKey()
      {
         return _internalEntry.getKey();
      }

      public VT getValue()
      {
         return getTransformer().transform(_internalEntry.getValue());
      }

      public VT setValue(VT value)
      {
         throw new UnsupportedOperationException();
      }
   }
   
   protected static class ValueTransformingEntryTransformer<K, VF, VT>
      implements Transformer<Entry<K, VF>, Entry<K, VT>>
   {
      protected final Transformer<VF, VT> _transformer;
      
      public ValueTransformingEntryTransformer(Transformer<VF, VT> transformer)
      {
         _transformer = transformer;
      }

      public Entry<K, VT> transform(Entry<K, VF> source)
      {
         return new ValueTransformingEntry<K, VF, VT>(source, _transformer);
      }
   }
}
