package net.sf.javagimmicks.math.sequence;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import net.sf.javagimmicks.math.MathExt;

import org.junit.Test;

public class MathExtTest
{
   @Test
   public void test()
   {
      assertEquals(BigInteger.valueOf(15), MathExt.binominal(BigInteger.valueOf(6), BigInteger.valueOf(2)));
   }
}
