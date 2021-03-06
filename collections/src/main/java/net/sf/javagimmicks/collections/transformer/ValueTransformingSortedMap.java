package net.sf.javagimmicks.collections.transformer;

import java.util.Comparator;
import java.util.SortedMap;

import net.sf.javagimmicks.util.Function;

class ValueTransformingSortedMap<K, VF, VT> extends ValueTransformingMap<K, VF, VT> implements SortedMap<K, VT>
{
   /**
    * @deprecated Use TranformerUtils.decorateValueBased() instead
    */
   @Deprecated
   public ValueTransformingSortedMap(SortedMap<K, VF> map, Function<VF, VT> valueTransformer)
   {
      super(map, valueTransformer);
   }
   
   public Comparator<? super K> comparator()
   {
      return getSortedMap().comparator();
   }

   public K firstKey()
   {
      return getSortedMap().firstKey();
   }

   public SortedMap<K, VT> headMap(K toKey)
   {
      return TransformerUtils.decorateValueBased(
         getSortedMap().headMap(toKey),
         getTransformerFunction());
   }

   public K lastKey()
   {
      return getSortedMap().lastKey();
   }

   public SortedMap<K, VT> subMap(K fromKey, K toKey)
   {
      return TransformerUtils.decorateValueBased(
         getSortedMap().subMap(fromKey, toKey),
         getTransformerFunction());
   }

   public SortedMap<K, VT> tailMap(K fromKey)
   {
      return TransformerUtils.decorateValueBased(
         getSortedMap().tailMap(fromKey),
         getTransformerFunction());
   }

   protected SortedMap<K, VF> getSortedMap()
   {
      return (SortedMap<K, VF>)_internalMap;
   }
}
