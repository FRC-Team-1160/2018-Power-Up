package org.usfirst.frc.team1160.robot;

public interface AutoSettings {
	
	/* 
	 * HARDCODED_POSITION
	 * 1 - Left
	 * 2 - Center
	 * 3 - Right
	 */
	public static final int HARDCODED_POSITION = 2;
	
	/* PRIORITIZE_SCALE
	 * true - prioritize the scale
	 * false - prioritize the switch
	 */
	public static final boolean PRIORITIZE_SCALE = false;
	
	/* 
	 * TWO_CUBE
	 * true - 2 cubes xd
	 * false - 1 cube (sad reaccs)
	 * BIG QUESTIONS: does two cube mean two eggs in the same basket
	 */
	public static final boolean TWO_CUBE = false;
	public static final boolean TWO_CUBE_SCALE = false; //somewhat provisional
	public static final boolean TWO_CUBE_SWITCH = false; //somewhat provisional as well
	public static final boolean ONE_CUBE_SCALE_ONE_CUBE_SWITCH = false; //provisional as fuck
	
	/**
	 * PRIORITY LIST FOR choosePath()
	 *  TOP
	 *   |	HARDCODED_POSITION
	 *   |	PRIORITIZE_SCALE
	 *   |	TWO_CUBE
	 *   |
	 * BOTTOM
	 *
	 * autoChoice() cases:
	 * 1: LEFT | LEFT SCALE   | TWO CUBES
	 * 2: LEFT | LEFT SCALE   | ONE CUBE
	 * 3: LEFT | RIGHT SCALE  | TWO CUBES
	 * 4: LEFT | RIGHT SCALE  | ONE CUBE
	 * 5: LEFT | LEFT SWITCH  | TWO CUBES
	 * 6: LEFT | LEFT SWITCH  | ONE CUBE
	 *X 7: LEFT | RIGHT SWITCH | TWO CUBES 
	 *X 8: LEFT | RIGHT SWITCH | ONE CUBE
	 * 9:  MIDDLE | LEFT SWITCH | TWO CUBES
	 * 10: MIDDLE | LEFT SWITCH | ONE CUBE
	 * 11: MIDDLE | RIGHT SWITCH| TWO CUBES
	 * 12: MIDDLE | RIGHT SWITCH| ONE CUBE
	 * 13: RIGHT | LEFT SCALE  | TWO CUBES
	 * 14: RIGHT | LEFT SCALE  | ONE CUBE
	 * 15: RIGHT | RIGHT SCALE | TWO CUBES
	 * 16: RIGHT | RIGHT SCALE | ONE CUBE
	 *X 17: RIGHT | LEFT SWITCH | TWO CUBES
	 *X 18: RIGHT | LEFT SWITCH | ONE CUBE
	 * 19: RIGHT | RIGHT SWITCH| TWO CUBES
	 * 20: RIGHT | RIGHT SWITCH| ONE CUBE
	 */
	
	
	public static int chooseAuto(char switchPosition, char scalePosition) {
		switch(HARDCODED_POSITION) {
		case 1:
			return chooseAutoLeft(switchPosition,scalePosition);
		case 2:
			return chooseAutoMiddle(switchPosition,scalePosition);
		case 3:
			return chooseAutoRight(switchPosition,scalePosition);
			
		}
		return 0;
	}
	public static int chooseAutoLeft(char switchPosition, char scalePosition) {
		if (PRIORITIZE_SCALE) {
			if (scalePosition == 'L') {
				if (TWO_CUBE) {
					/*
					 * Left position to left scale
					 * Two cubes
					 */
					return 1;
				}
				else if (!TWO_CUBE) {
					/*
					 * Left position to left scale
					 * One cube
					 */
					return 2;
				}
			}
			else if (scalePosition == 'R') {
				if (TWO_CUBE) {
					/*
					 * Left position to right scale
					 * Two cubes
					 */
					return 3;
				}
				else if (!TWO_CUBE) {
					/*
					 * Left position to right scale
					 * One cube
					 */
					return 4;
				}
				
			}
		}
		else if (!PRIORITIZE_SCALE) {
			if (switchPosition == 'L') {
				if (TWO_CUBE) {
					/*
					 * Left position to left switch
					 * Two cube
					 */
					return 5;
				}
				else if (!TWO_CUBE) {
					/*
					 * Left position to left switch
					 * One cube
					 */
					return 6;
				}
			}
		}
		return 0;
	}
	public static int chooseAutoMiddle(char switchPosition, char scalePosition) {
		if (switchPosition == 'L') {
			if (TWO_CUBE) {
				/*
				 * Middle position to left switch
				 * Two cube
				 */
				return 9;
			}
			else if (!TWO_CUBE) {
				/*
				 * Middle position to left switch
				 * One cube
				 */
				return 10;
			}
		}
		else if (switchPosition == 'R') {
			if (TWO_CUBE) {
				/*
				 * Middle position to right switch
				 * Two cube
				 */
				return 11;
			}
			else if (!TWO_CUBE) {
				/*
				 * Middle position to right switch
				 * One cube
				 */
				return 12;
			}
		}
		return 0;
	}
	
	public static int chooseAutoRight(char switchPosition, char scalePosition) {
		if (PRIORITIZE_SCALE) {
			if (scalePosition == 'L') {
				if (TWO_CUBE) {
					/*
					 * Right position to left scale
					 * Two cubes
					 */
					return 13;
				}
				else if (!TWO_CUBE) {
					/*
					 * Right position to left scale
					 * One cube
					 */
					return 14;
				}
			}
			else if (scalePosition == 'R') {
				if (TWO_CUBE) {
					/*
					 * Right position to right scale
					 * Two cubes
					 */
					return 15;
				}
				else if (!TWO_CUBE) {
					/*
					 * Right position to right scale
					 * One cube
					 */
					return 16;
				}
				
			}
		}
		else if (!PRIORITIZE_SCALE) {
			if (switchPosition == 'R') {
				if (TWO_CUBE) {
					/*
					 * Right position to right switch
					 * Two cube
					 */
					return 19;
				}
				else if (!TWO_CUBE) {
					/*
					 * Right position to right switch
					 * One cube
					 */
					return 20;
				}
			}
		}
		return 0;
	}
}
		
