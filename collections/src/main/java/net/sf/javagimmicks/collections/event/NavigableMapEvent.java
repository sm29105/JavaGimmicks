package net.sf.javagimmicks.collections.event;

public interface NavigableMapEvent<K, V> extends Event<NavigableMapEvent<K, V>, EventNavigableMapListener<K, V>>
{

   enum Type
   {
      ADDED, UPDATED, REMOVED
   }

   Type getType();

   K getKey();

   V getValue();

   V getNewValue();

}