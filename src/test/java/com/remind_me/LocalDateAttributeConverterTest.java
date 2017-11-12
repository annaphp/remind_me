package com.remind_me;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.remind_me.core.formatters_converters.LocalDateAttributeConverter;

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
