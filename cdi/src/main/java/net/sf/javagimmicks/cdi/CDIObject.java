package net.sf.javagimmicks.cdi;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * A base class for Java beans which can not be instantiated via CDI (for
 * example because they are instantiated via reflection by some given framework)
 * but need access to the CDI context.
 * <p>
 * Upon constructor call this class automatically performs non-constructor
 * injections and post-construct callbacks based on {@link Inject} and
 * {@link PostConstruct} annotations.
 */
public abstract class CDIObject
{
   protected CDIObject()
   {
      CDIContext.initBean(this);
   }
}
