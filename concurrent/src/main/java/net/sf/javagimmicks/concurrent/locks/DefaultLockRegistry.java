package net.sf.javagimmicks.concurrent.locks;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.sf.javagimmicks.util.Supplier;

class DefaultLockRegistry<K> implements LockRegistry<K>, Serializable
{
   private static final long serialVersionUID = -785304313135882910L;

   private static final Supplier<Collection<Thread>> HASHSET_SHARED_COLLECTION_SUPPLIER = new HashSetSharedCollectionSupplier();

   public static <K> DefaultLockRegistry<K> createHashBasedInstance()
   {
      return new DefaultLockRegistry<K>(new HashMap<K, Thread>(), new HashMap<K, Collection<Thread>>(),
            HASHSET_SHARED_COLLECTION_SUPPLIER);
   }

   public static <K> DefaultLockRegistry<K> createTreeBasedInstance()
   {
      return new DefaultLockRegistry<K>(new TreeMap<K, Thread>(), new TreeMap<K, Collection<Thread>>(),
            HASHSET_SHARED_COLLECTION_SUPPLIER);
   }

   protected final Map<K, Thread> _exRegistry;
   protected final Map<K, Collection<Thread>> _shRegistry;
   protected final Supplier<Collection<Thread>> _shCollectionSupplier;

   public DefaultLockRegistry(final Map<K, Thread> exRegistry, final Map<K, Collection<Thread>> shRegistry,
         final Supplier<Collection<Thread>> sharedCollectionFactory)
   {
      _exRegistry = exRegistry;
      _shRegistry = shRegistry;
      _shCollectionSupplier = sharedCollectionFactory;
   }

   @Override
   public boolean isSharedFree(final Collection<K> resources)
   {
      for (final K resource : resources)
      {
         if (_shRegistry.containsKey(resource))
         {
            return false;
         }
      }

      return true;
   }

   @Override
   public void registerShared(final Collection<K> resources)
   {
      final Thread currentThread = Thread.currentThread();

      for (final K resource : resources)
      {
         Collection<Thread> threadsForResource = _shRegistry.get(resource);
         if (threadsForResource == null)
         {
            threadsForResource = _shCollectionSupplier.get();
            _shRegistry.put(resource, threadsForResource);
         }

         threadsForResource.add(currentThread);
      }
   }

   @Override
   public void unregisterShared(final Collection<K> resources)
   {
      final Thread currentThread = Thread.currentThread();

      for (final K resource : resources)
      {
         final Collection<Thread> threadsForResource = _shRegistry.get(resource);
         if (threadsForResource == null)
         {
            continue;
         }

         threadsForResource.remove(currentThread);

         if (threadsForResource.isEmpty())
         {
            _shRegistry.remove(resource);
         }
      }
   }

   @Override
   public boolean isExclusiveFree(final Collection<K> resources)
   {
      for (final K resource : resources)
      {
         if (_exRegistry.containsKey(resource))
         {
            return false;
         }
      }

      return true;
   }

   @Override
   public void registerExclusive(final Collection<K> resources)
   {
      final Thread currentThread = Thread.currentThread();

      for (final K resource : resources)
      {
         _exRegistry.put(resource, currentThread);
      }
   }

   @Override
   public void unregisterExclusive(final Collection<K> resources)
   {
      for (final K resource : resources)
      {
         _exRegistry.remove(resource);
      }
   }

   @Override
   public void dump(final Writer out) throws IOException
   {
      final PrintWriter pw = new PrintWriter(out);

      pw.println();
      pw.println("Exclusive Lock Registry entries:");
      for (final Entry<K, Thread> entry : _exRegistry.entrySet())
      {
         final String resource = entry.getKey().toString();
         final Thread thread = entry.getValue();

         pw.println(String.format("\t-Resource: %s is locked by Thread with name: '%s' and id: '%d'",
               resource,
               thread.getName(),
               thread.getId()));

      }

      pw.println();
      pw.println("Shared Lock Registry entries:");
      for (final Entry<K, Collection<Thread>> entry : _shRegistry.entrySet())
      {
         final String resource = entry.getKey().toString();
         final Collection<Thread> threads = entry.getValue();

         pw.println(String.format("\t-Resource: %s is locked by:", resource));
         for (final Thread thread : threads)
         {
            pw.println(String.format("\t\tThread with name: '%s' and id: '%d'", thread.getName(), thread.getId()));
         }
      }

      pw.flush();
   }

   private static final class HashSetSharedCollectionSupplier implements Supplier<Collection<Thread>>, Serializable
   {
      private static final long serialVersionUID = 3613642441786733902L;

      @Override
      public Collection<Thread> get()
      {
         return new HashSet<Thread>();
      }
   }
}
