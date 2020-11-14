package Assignment2.nz.ac.massey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import com.sun.javafx.UnmodifiableArrayList;


public class MemAppender extends AppenderSkeleton{
	private static MemAppender memAppender = null;
	private long discardedLpgCount = 0;
	private List<LoggingEvent> logEvents;
	private int maxSize = 100;
	private Layout layout;
	private MemAppender(int maxSize, List<LoggingEvent> logEvents) {
		super();
		this.maxSize =maxSize;
		this.logEvents = logEvents;
	}
	public static MemAppender getInstance(int maxSize,List<LoggingEvent> logEvents) {
		if(memAppender==null) {
			memAppender = new MemAppender(maxSize, logEvents);
		}
		return memAppender;
	}
	
	

	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		this.closed=true;
	}
	@Override
	public boolean requiresLayout() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected void append(LoggingEvent event) {
		// TODO Auto-generated method stub
		if(logEvents.size()>=this.maxSize) {
			logEvents.remove(0);
			logEvents.add(event);
			discardedLpgCount+=1;
		}else {
			logEvents.add(event);
		}
	}
	public long getDiscardedLogCount() {
		return discardedLpgCount;
	}
	public List<LoggingEvent> getCurrentLogs(){
		List<LoggingEvent> unmotifiableList = Collections.unmodifiableList(this.logEvents);
		return unmotifiableList;
	}
	public List<String> getEventStrings(){
		List<String> unmodfiablelist = new ArrayList<String>();
		for (int i = 0; i<logEvents.size(); i++) {
			
			unmodfiablelist.add(layout.format(this.logEvents.get(i)));
		}
		return unmodfiablelist;
	}
	
	public void printLogs() {
		for (int i=0;i<logEvents.size();i++) {
			System.out.println(layout.format(logEvents.get(i)));
			}
		logEvents.clear();
	}
	
	public long getDiscardedLpgCount() {
		return discardedLpgCount;
	}
	public void setDiscardedLpgCount(long discardedLpgCount) {
		this.discardedLpgCount = discardedLpgCount;
	}
	public List<LoggingEvent> getLogEvents() {
		return logEvents;
	}
	public void setLogEvents(List<LoggingEvent> logEvents) {
		this.logEvents = logEvents;
		//discardedLpgCount=0;
	}
	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	public Layout getLayout() {
		return layout;
	}
	public void setLayout(Layout layout) {
		this.layout = layout;
	}
	
	
}
