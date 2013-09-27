package net.sf.javagimmicks.sql.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Rule;
import org.junit.Test;

public class HsqlDbTestRuleTest
{
   @Rule
   public HsqlDbTestRule _db = new HsqlDbTestRule();

   @Test
   public void test() throws SQLException
   {
      assertTrue(_db.getDatabaseFolder().exists());

      assertEquals(0, _db.getDataSource().getNumActive());

      final Connection connection = _db.getConnection();
      assertNotNull(connection);
      assertEquals(1, _db.getDataSource().getNumActive());

      final Statement stmtCreate = connection.createStatement();
      assertNotNull(stmtCreate);

      stmtCreate.execute("CREATE TABLE temp (key INTEGER, value VARCHAR(255))");
      stmtCreate.close();

      final Statement stmtInsert = connection.createStatement();
      assertNotNull(stmtInsert);

      stmtInsert.execute("INSERT INTO temp VALUES (1, 'test')");
      stmtInsert.close();

      connection.close();

      assertEquals(0, _db.getDataSource().getNumActive());
   }
}
