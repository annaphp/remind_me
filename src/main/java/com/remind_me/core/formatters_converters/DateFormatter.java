package com.remind_me.core.formatters_converters;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class DateFormatter implements Formatter<LocalDate> {



	@Override
	public String print(LocalDate date, Locale arg1) {
		System.out.println("printing date to string");
		return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).toString();
	}

	@Override
	public LocalDate parse(String date, Locale arg1) throws ParseException {
		System.out.println("converting string date to localdate: date" +date);
		return LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	}

}