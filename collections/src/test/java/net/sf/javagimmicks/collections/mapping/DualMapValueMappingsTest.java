package net.sf.javagimmicks.collections.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import net.sf.javagimmicks.collections.builder.MapBuilder;

import org.junit.Test;

public class DualMapValueMappingsTest
{
   private ValueMappings<String, Integer, String> createMappings()
   {
      return DualMapValueMappings.createHashTreeInstance();
   }
   
   @Test
   public void testPut()
   {
      ValueMappings<String, Integer, String> mappings = createMappings();
      
      mappings.put("A", 1, "A1");
      assertEquals(1, mappings.size());
      assertEquals("A1", mappings.get("A", 1));
      assertTrue(mappings.containsLeft("A"));
      assertTrue(mappings.containsRight(1));
      assertTrue(mappings.containsMapping("A", 1));
      
      mappings.putRight("A", MapBuilder.create(new HashMap<Integer, String>())
         .put(2, "A2")
         .put(3, "A3").toMap());
      assertEquals(3, mappings.size());
      assertEquals("A2", mappings.get("A", 2));
      assertEquals("A3", mappings.get("A", 3));
      assertTrue(mappings.containsLeft("A"));
      assertTrue(mappings.containsRight(2));
      assertTrue(mappings.containsRight(3));
      assertTrue(mappings.containsMapping("A", 2));
      assertTrue(mappings.containsMapping("A", 3));
      
      mappings.putLeft(4, MapBuilder.create(new HashMap<String, String>())
            .put("A", "A4")
            .put("B", "B4")
            .put("C", "C4")
            .put("D", "D4").toMap());
      assertEquals(7, mappings.size());
      assertEquals("A4", mappings.get("A", 4));
      assertEquals("B4", mappings.get("B", 4));
      assertEquals("C4", mappings.get("C", 4));
      assertEquals("D4", mappings.get("D", 4));
      assertTrue(mappings.containsLeft("A"));
      assertTrue(mappings.containsLeft("B"));
      assertTrue(mappings.containsLeft("C"));
      assertTrue(mappings.containsLeft("D"));
      assertTrue(mappings.containsRight(4));
      assertTrue(mappings.containsMapping("A", 4));
      assertTrue(mappings.containsMapping("B", 4));
      assertTrue(mappings.containsMapping("C", 4));
      assertTrue(mappings.containsMapping("D", 4));
      
      ValueMappings<Integer, String, String> inverseMappings = mappings.getInverseMappings();
      inverseMappings.put(1, "A", "A1");
      inverseMappings.putRight(2, MapBuilder.create(new HashMap<String, String>())
            .put("A", "A2")
            .put("B", "B2").toMap());
      inverseMappings.putLeft("C", MapBuilder.create(new HashMap<Integer, String>())
            .put(3, "C3")
            .put(4, "C4").toMap());
      
      // Build left reference map
      TreeMap<Integer, String> mapA = MapBuilder.create(new TreeMap<Integer, String>())
         .put(1, "A1")
         .put(2, "A2")
         .put(3, "A3")
         .put(4, "A4").toMap();
      
      TreeMap<Integer, String> mapB = MapBuilder.create(new TreeMap<Integer, String>())
         .put(2, "B2")
         .put(4, "B4").toMap();
      
      TreeMap<Integer, String> mapC = MapBuilder.create(new TreeMap<Integer, String>())
         .put(3, "C3")
         .put(4, "C4").toMap();
      
      TreeMap<Integer, String> mapD = MapBuilder.create(new TreeMap<Integer, String>())
         .put(4, "D4").toMap();
      
      HashMap<String, Map<Integer, String>> leftReferenceMap = MapBuilder.create(new HashMap<String, Map<Integer, String>>())
         .put("A", mapA)
         .put("B", mapB)
         .put("C", mapC)
         .put("D", mapD)
         .toMap();

      // Build right reference map
      HashMap<String, String> map1 = MapBuilder.create(new HashMap<String, String>())
         .put("A", "A1").toMap();
      
      HashMap<String, String> map2 = MapBuilder.create(new HashMap<String, String>())
         .put("A", "A2")
         .put("B", "B2").toMap();
      
      HashMap<String, String> map3 = MapBuilder.create(new HashMap<String, String>())
         .put("A", "A3")
         .put("C", "C3").toMap();
   
      HashMap<String, String> map4 = MapBuilder.create(new HashMap<String, String>())
         .put("A", "A4")
         .put("B", "B4")
         .put("C", "C4")
         .put("D", "D4").toMap();
      
      HashMap<Integer, Map<String, String>> rightReferenceMap = MapBuilder.create(new HashMap<Integer, Map<String, String>>())
         .put(1, map1)
         .put(2, map2)
         .put(3, map3)
         .put(4, map4)
         .toMap();
      
      assertEquals(leftReferenceMap, mappings.getLeftOuterMap());
      assertEquals(rightReferenceMap, mappings.getRightOuterMap());

      assertEquals(mapA, mappings.getRightInnerMap("A"));
      assertEquals(mapB, mappings.getRightInnerMap("B"));
      assertEquals(mapC, mappings.getRightInnerMap("C"));
      assertEquals(mapD, mappings.getRightInnerMap("D"));

      assertEquals(map1, mappings.getLeftInnerMap(1));
      assertEquals(map2, mappings.getLeftInnerMap(2));
      assertEquals(map3, mappings.getLeftInnerMap(3));
      assertEquals(map4, mappings.getLeftInnerMap(4));
      
      System.out.println(mappings.getMappingSet());
   }
   
   @Test
   public void testAddAfterRemoveLeft()
   {
      ValueMappings<String, Integer, String> mappings = createMappings();
      mappings.put("A", 1, "A1");
      
      Map<Integer, String> innerA = mappings.getRightInnerMap("A");
      mappings.removeRight("A");
      
      assertTrue(innerA.isEmpty());
      assertTrue(mappings.isEmpty());
      
      mappings.put("A", 2, "A2");
      
      try
      {
         innerA.put(99, "A99");
         fail(IllegalStateException.class.getName() + " expected!");
      }
      catch(IllegalStateException ex)
      {
      }
   }
   
   @Test
   public void testAddAfterRemoveRight()
   {
      ValueMappings<String, Integer, String> mappings = createMappings();
      mappings.put("A", 1, "A1");
      
      Map<String, String> inner1 = mappings.getLeftInnerMap(1);
      mappings.removeRight("A");

      assertTrue(inner1.isEmpty());
      assertTrue(mappings.isEmpty());
      
      mappings.put("A", 2, "A2");
      
      try
      {
         inner1.put("B", "B1");
         fail(IllegalStateException.class.getName() + " expected!");
      }
      catch(IllegalStateException ex)
      {
      }
   }

   @Test
   public void testClearInnerMapLeft()
   {
      ValueMappings<String, Integer, String> mappings = createMappings();
      mappings.put("A", 1, "A1");
      
      Map<String, String> inner1 = mappings.getLeftInnerMap(1);
      inner1.clear();
      
      assertFalse(mappings.containsRight(1));
      assertFalse(mappings.containsLeft("A"));
      
      try
      {
         inner1.put("Z", "Z1");
         fail(IllegalStateException.class.getName() + " expected!");
      }
      catch(IllegalStateException ex)
      {
      }
   }

   @Test
   public void testClearInnerMapRight()
   {
      ValueMappings<String, Integer, String> mappings = createMappings();
      mappings.put("A", 1, "A1");
      
      Map<Integer, String> innerA = mappings.getRightInnerMap("A");
      innerA.clear();
      
      assertFalse(mappings.containsRight(1));
      assertFalse(mappings.containsLeft("A"));
      
      try
      {
         innerA.put(99, "A99");
         fail(IllegalStateException.class.getName() + " expected!");
      }
      catch(IllegalStateException ex)
      {
      }
   }
   
   @Test
   public void testUpdateEntry()
   {
      ValueMappings<String, Integer, String> mappings = createMappings();
      mappings.put("A", 1, "A1");
      
      Entry<String, Map<Integer, String>> entry = mappings.getLeftOuterMap().entrySet().iterator().next();
      
      try
      {
         final Map<Integer, String> emptyMap = Collections.emptyMap();
         entry.setValue(emptyMap);
         fail(IllegalArgumentException.class.getName() + " expected!");
      }
      catch(IllegalArgumentException ex)
      {
         
      }

      try
      {
         entry.setValue(null);
         fail(IllegalArgumentException.class.getName() + " expected!");
      }
      catch(IllegalArgumentException ex)
      {
         
      }
      
      Map<Integer, String> oldSet = entry.setValue(Collections.singletonMap(2, "A2"));
      
      assertEquals(Collections.singletonMap(1, "A1"), oldSet);
      assertTrue(mappings.containsMapping("A", 2));
      assertEquals("A2", mappings.get("A", 2));
   }
}