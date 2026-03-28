package util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class Date {
	private Date() {}
	public static String relativeTime(LocalDateTime createdAt) {
		LocalDateTime now=LocalDateTime.now();
		Duration d=Duration.between(createdAt,now);
		Period p=Period.between(createdAt.toLocalDate(),now.toLocalDate());
		if(p.getYears()>0) {
			return p.getYears()+((p.getYears()==1)?" year ago":" years ago");
		}
		else if(p.getMonths()>0) {
			return p.getMonths()+((p.getMonths()==1)?" month ago":" months ago");
		}
		else if(p.getDays()>0) {
			return p.getDays()+((p.getDays()==1)?" day ago":" days ago");
		}
		else if(d.toHours()>0) {
			return d.toHours() + (d.toHours() == 1 ? " hour ago" : " hours ago");
		}
		else if(d.toMinutes()>0) {
			return d.toMinutes() + (d.toMinutes() == 1 ? " minute ago" : " minutes ago");
		}
		return "just now";
	}
}
