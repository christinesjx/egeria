/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.openmetadata.accessservices.cognos.test.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.odpi.openmetadata.accessservices.cognos.model.module.DataSource;
import org.odpi.openmetadata.accessservices.cognos.model.module.Table;
import org.odpi.openmetadata.accessservices.cognos.test.utils.TestUtilities;

public class DataSourceTest {

	private static final String DTASOURCE_NAME = "DTASOURCE_NAME";
	private static final String SCHEMA = "SCHEMA";
	private static final String CATALOG = "CATALOG";

	String master = "{\r\n" +
			"  \"schema\" : \"SCHEMA\",\r\n" +
			"  \"catalog\" : \"CATALOG\",\r\n" +
			"  \"name\" : \"DTASOURCE_NAME\",\r\n" +
			"  \"table\" : [ { }, { } ]\r\n" +
			"}";

	String master_empty = "{ }";

	List<Table> tables = Arrays.asList(new Table(), new Table());

	@Test
	public void toJson() {
		DataSource obj = new DataSource();

		obj.setName(DTASOURCE_NAME);
		obj.setCatalog(CATALOG);
		obj.setSchema(SCHEMA);
		obj.setTable(tables);

		TestUtilities.assertObjectJson(obj, master);
	}

	@Test
	public void toJsonEmpty() {
		TestUtilities.assertObjectJson(new DataSource(), master_empty);
	}

	@Test
	public void fromJson() {

		DataSource obj = TestUtilities.readObjectJson(master, DataSource.class);

		assertEquals(DTASOURCE_NAME, obj.getName());
		assertEquals(SCHEMA, obj.getSchema());
		assertEquals(CATALOG, obj.getCatalog());
		assertEquals(2, obj.getTable().size());

		TestUtilities.assertObjectJson(obj, master);
	}
}
