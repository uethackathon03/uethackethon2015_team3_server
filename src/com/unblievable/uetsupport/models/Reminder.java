package com.unblievable.uetsupport.models;

import javax.persistence.Embeddable;

@Embeddable
public class Reminder {
	
	public Integer reminderId;
	public Long timeReminder;
	public Long beforeReminder;
	public Integer numberOfReminder;
	public String title;
	public String note;
	
}
