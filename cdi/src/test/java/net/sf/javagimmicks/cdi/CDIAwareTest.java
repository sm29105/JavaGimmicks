package net.sf.javagimmicks.cdi;

import net.sf.javagimmicks.cdi.injectable.A;
import net.sf.javagimmicks.cdi.injectable.B;

import org.junit.Assert;
import org.junit.Test;

public class CDIAwareTest extends WeldTestHelper
{
   @Test
   public void test()
   {
      Assert.assertEquals(B.MESSAGE, new NonCDIGeneratedClass().callA());
   }

   private static class NonCDIGeneratedClass extends CDIAware
   {
      // @Inject private A a;
      private final A a = lookup(A.class);

      public String callA()
      {
         return a.callB();
      }
   }
}
