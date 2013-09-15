package net.sf.javagimmicks.concurrent.impl;

import java.util.Collection;

public interface LockRegistry<K>
{
   boolean isSharedFree(Collection<K> resources);
   void registerShared(Collection<K> resources);
   void unregisterShared(Collection<K> resources);

   boolean isExclusiveFree(Collection<K> resources);
   void registerExclusive(Collection<K> resources);
   void unregisterExclusive(Collection<K> resources);
}