package net.sf.javagimmicks.concurrent.locks;

import java.util.Collection;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * An extension of {@link ReadWriteLock} that allows atomic locking on any
 * number of resources (which can be identified by resource identifiers).
 * <p>
 * Instances must be generated by a {@link MultiLockProvider} (which also takes
 * care about resource identifier management).
 * 
 * @param <K>
 *           The type of the internally used resource identifiers
 * @see MultiLockProvider#newLock(Iterable)
 * @see MultiLockProvider#newLock(Object...)
 */
public interface MultiReadWriteLock<K> extends ReadWriteLock
{
   /**
    * Returns the resource identifiers with which this instance is associated.
    * 
    * @return the resource identifiers with which this instance is associated.
    */
   Collection<K> getResourceIds();

   /**
    * Returns a read-only {@link MultiLock} that is associated with the resource
    * identifiers with which this instance is associated.
    * 
    * @return a read-only {@link MultiLock} for the associated resource
    *         identifiers
    */
   @Override
   MultiLock<K> readLock();

   /**
    * Returns a write-access {@link MultiLock} that is associated with the
    * resource identifiers with which this instance is associated.
    * 
    * @return a write-access {@link MultiLock} for the associated resource
    *         identifiers
    */
   @Override
   MultiLock<K> writeLock();
}
