package com.googlecode.coss.common.utils.lang.DateTime;

/**
 * <p>
 * StopWatch
 * </p>
 * 
 * <pre>
 * StopWatch watch = new StopWatch();
 * watch.begin();
 * ...
 * System.out.println(watch.getInterval());
 * ...
 * System.out.println(watch.getInterval());
 * </pre>
 * 
 * 1:53:03 PM Sep 8, 2009
 */
public class StopWatch {

	// private long startTime = System.currentTimeMillis();

	private long from;
	private long to;

	public static StopWatch begin() {
		StopWatch sw = new StopWatch();
		sw.start();
		return sw;
	}

	public static StopWatch create() {
		return new StopWatch();
	}

	public static StopWatch run(Runnable atom) {
		StopWatch sw = begin();
		atom.run();
		sw.stop();
		return sw;
	}

	public long start() {
		from = System.currentTimeMillis();
		return from;
	}

	public long stop() {
		to = System.currentTimeMillis();
		return to;
	}

	public long getDuration() {
		return to - from;
	}

	public long getStartTime() {
		return from;
	}

	public long getEndTime() {
		return to;
	}

	@Override
	public String toString() {
		return String.format("Total: %dms : [%s]=>[%s]", this.getDuration(), new java.sql.Timestamp(from).toString(),
				new java.sql.Timestamp(to).toString());
	}

	/**
	 * <p>
	 * The Interval between start time to stop time
	 * </p>
	 * 
	 * @return System.currentTimeMillis() - startTime
	 */
	public long getInterval() {
		long lastStartTime = this.from;
		this.from = System.currentTimeMillis();
		return System.currentTimeMillis() - lastStartTime;
	}
}
