package com.unblievable.uetsupport.models;

import javax.persistence.Embeddable;

@Embeddable
public class Reminder {
	
	public Long timeRemider;
	public Long beforeRemiber;
	public Integer numberOfReminder;
	public String title;
	public String note;
	
}
