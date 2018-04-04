package org.usfirst.frc.team1160.robot;

public interface AutoSettings {
	
	/* 
	 * HARDCODED_POSITION
	 * 1 - Left
	 * 2 - Center
	 * 3 - Right
	 */
	public static final int HARDCODED_POSITION = 3;
	
	/* PRIORITIZE_SCALE
	 * true - prioritize the scale
	 * false - prioritize the switch
	 */
	public static final boolean PRIORITIZE_SCALE = true;
	
	/* 
	 * TWO_CUBE
	 * true - 2 cubes xd
	 * false - 1 cube (sad reaccs)
	 * BIG QUESTIONS: does two cube mean two eggs in the same basket
	 */
	public static final boolean TWO_CUBE = false;
	
	/*
	 * PRIORITY LIST FOR choosePath()
	 *  TOP
	 *   |	HARDCODED_POSITION
	 *   |	PRIORITIZE_SCALE
	 *   |	TWO_CUBE
	 *   |	FAST
	 *   |
	 * BOTTOM
	 */
}
