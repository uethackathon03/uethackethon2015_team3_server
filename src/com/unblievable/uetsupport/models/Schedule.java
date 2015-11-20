package com.unblievable.uetsupport.models;

import javax.persistence.Embeddable;

@Embeddable
public class Schedule {
	
	public Integer ordinalNumbers;
	public String dayOfWeek;
	public Long timeFrom;
	public Long timeTo;
	public String subject;
	public String note;
	
}
