package org.postgresql.test.jdbc2;

import org.postgresql.test.TestUtil;
import junit.framework.TestCase;
import java.sql.*;

/*
 * $PostgreSQL: DriverTest.java,v 1.6 2003/11/03 15:22:07 davec Exp $
 *
 * Tests the dynamically created class org.postgresql.Driver
 *
 */
public class DriverTest extends TestCase
{

	public DriverTest(String name)
	{
		super(name);
	}

	/*
	 * This tests the acceptsURL() method with a couple of well and poorly
	 * formed jdbc urls.
	 */
	public void testAcceptsURL() throws SQLException
	{
		// Load the driver (note clients should never do it this way!)
		org.postgresql.Driver drv = new org.postgresql.Driver();
		assertNotNull(drv);

		// These are always correct
		assertTrue(drv.acceptsURL("jdbc:postgresql:test"));
		assertTrue(drv.acceptsURL("jdbc:postgresql://localhost/test"));
		assertTrue(drv.acceptsURL("jdbc:postgresql://localhost:5432/test"));
		assertTrue(drv.acceptsURL("jdbc:postgresql://127.0.0.1/anydbname"));
		assertTrue(drv.acceptsURL("jdbc:postgresql://127.0.0.1:5433/hidden"));
		assertTrue(drv.acceptsURL("jdbc:postgresql://[::1]:5740/db"));

		// Badly formatted url's
		assertTrue(!drv.acceptsURL("jdbc:postgres:test"));
		assertTrue(!drv.acceptsURL("postgresql:test"));
		assertTrue(!drv.acceptsURL("db"));

	}

	/*
	 * Tests parseURL (internal)
	 */
	/*
	 * Tests the connect method by connecting to the test database
	 */
	public void testConnect() throws Exception
	{
		Class.forName("org.postgresql.Driver");

		// Test with the url, username & password
		Connection con = DriverManager.getConnection(TestUtil.getURL(), TestUtil.getUser(), TestUtil.getPassword());
		assertNotNull(con);
		con.close();

		// Test with the username in the url
		con = DriverManager.getConnection(TestUtil.getURL() + "&user=" + TestUtil.getUser() + "&password=" + TestUtil.getPassword());
		assertNotNull(con);
		con.close();
	}
}
