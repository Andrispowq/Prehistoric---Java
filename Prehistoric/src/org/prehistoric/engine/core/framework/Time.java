package org.prehistoric.engine.core.framework;

public class Time
{
    private static final long SECOND = 1000000000L;
    
    public static double totalPassedTime = 0;

    public static double GetTime()
    {
	return (double) System.nanoTime() / (double) SECOND;
    }
}
