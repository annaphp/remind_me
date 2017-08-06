package com.remind_me;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LocalDateAttributeConverterTest {
	
	private LocalDateAttributeConverter converter;

	@Before
	public void setUp() throws Exception {
		converter = new LocalDateAttributeConverter();
	}

	

	@Test
	public void test() {
			LocalDate now = LocalDate.now();
			assertEquals(converter.convertToEntityAttribute(converter.convertToDatabaseColumn(now)), now);
	}

}
