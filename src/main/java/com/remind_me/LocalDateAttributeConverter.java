package com.remind_me;


import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

	    @Override
	    public Date convertToDatabaseColumn(LocalDate locDate) {
	    	return (locDate == null ? null : Date.valueOf(locDate));
	    }

		@Override
	    public LocalDate convertToEntityAttribute(Date sqlDate) {
	    	
			System.out.println(LocalDate.parse(sqlDate.toString()));
			
	    	return (sqlDate == null ? null : sqlDate.toLocalDate());
	    	
	}

}
