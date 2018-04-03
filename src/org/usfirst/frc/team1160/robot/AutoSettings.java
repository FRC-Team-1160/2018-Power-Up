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
	 * FAST
	 * true - fast
	 * false - not fast
	 * 
	 * if this is true and the bot is not in the center (ie. HARDCODED_POSITION != 2), this
	 * really won't matter - that is, until we make fast paths for everything (!)
	 */
	public static final boolean FAST = true;
	//April 2-- let's be honest this should always be true, slow paths are lame
	
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
	public static int choosePath(char switchPosition,char scalePosition) {
		if (HARDCODED_POSITION == 2) {
			if (TWO_CUBE) {
				if (FAST) {
					if (switchPosition == 'L') {
						/*
						 * CENTER POSITION
						 * LEFT SWITCH
						 * TWO CUBES
						 * FAST
						 */
						return 1;
					}
					else if (switchPosition == 'R') {
						/*
						 * CENTER POSITION
						 * RIGHT SWITCH
						 * TWO CUBES
						 * FAST
						 */
						return 2;
					}
				}
				else if (!FAST) {
					if (switchPosition == 'L') {
						/*
						 * CENTER POSITION
						 * LEFT SWITCH
						 * TWO CUBES
						 * SLOW
						 */
						return 3;
					}
					else if (switchPosition == 'R') {
						/*
						 * CENTER POSITION
						 * RIGHT SWITCH
						 * TWO CUBES
						 * SLOW
						 */
						return 4;
					}
				}
			}
			else if (!TWO_CUBE){
				if (FAST) {
					if (switchPosition == 'L') {
						/*
						 * CENTER POSITION
						 * LEFT SWITCH
						 * ONE CUBE
						 * FAST
						 */
						return 5;
					}
					else if (switchPosition == 'R') {
						/*
						 * CENTER POSITION
						 * RIGHT SWITCH
						 * ONE CUBE
						 * FAST
						 */
						return 6;
					}
				}
				else if (!FAST) {
					if (switchPosition == 'L') {
						/*
						 * CENTER POSITION
						 * LEFT SWITCH
						 * ONE CUBE
						 * SLOW
						 */
						return 7;
					}
					else if (switchPosition == 'R') {
						/*
						 * CENTER POSITION
						 * RIGHT SWITCH
						 * ONE CUBE
						 * SLOW
						 */
						return 8;
					}
				}
			}
		}
		else if (HARDCODED_POSITION == 1) {
			if (PRIORITIZE_SCALE) {
				if (TWO_CUBE) {
					if (FAST) {
						if (scalePosition == 'L') {
							/*
							 * LEFT POSITION
							 * LEFT SCALE
							 * TWO CUBE
							 * FAST
							 */
							return 9;
						}
						else if (scalePosition == 'R') {
							/*
							 * LEFT POSITION
							 * RIGHT SCALE
							 * TWO CUBE
							 * FAST
							 */
							return 10;
						}
					}
					else if (!FAST) {
						if (scalePosition == 'L') {
							/*
							 * LEFT POSITION
							 * LEFT SCALE
							 * TWO CUBE
							 * SLOW
							 */
							return 11;
							//This is probably nearly impossible
						}
						else if (scalePosition == 'R') {
							/*
							 * LEFT POSITION
							 * RIGHT SCALE
							 * TWO CUBE
							 * SLOW
							 */
							return 12;
							//This is definitely impossible
						}
					}
				}
				else if (!TWO_CUBE){
					if (FAST) {
						if (scalePosition == 'L') {
							/*
							 * LEFT POSITION
							 * LEFT SCALE
							 * ONE CUBE
							 * FAST
							 */
							return 13;
						}
						else if (scalePosition == 'R') {
							/*
							 * LEFT POSITION
							 * RIGHT SCALE
							 * ONE CUBE
							 * FAST
							 */
							return 14;
						}
					}
					else if (!FAST) {
						if (scalePosition == 'L') {
							/*
							 * LEFT POSITION
							 * LEFT SCALE
							 * ONE CUBE
							 * SLOW
							 */
							return 15;
						}
						else if (scalePosition == 'R') {
							/*
							 * LEFT POSITION
							 * RIGHT SCALE
							 * ONE CUBE
							 * SLOW
							 */
							return 16;
						}
					}
				}
			}
			else if (!PRIORITIZE_SCALE) {
				if (TWO_CUBE) {
					if (FAST) {
						if (switchPosition == 'L') {
							/*
							 * LEFT POSITION
							 * LEFT SWITCH
							 * TWO CUBE
							 * FAST
							 */
							return 17;
						}
						else if (switchPosition == 'R') {
							/*
							 * LEFT POSITION
							 * RIGHT SWITCH
							 * TWO CUBE
							 * FAST
							 */
							return 18;
						}
					}
					else if (!FAST) {
						if (switchPosition == 'L') {
							/*
							 * LEFT POSITION
							 * LEFT SWITCH
							 * TWO CUBE
							 * SLOW
							 */
							return 19;
						}
						else if (switchPosition == 'R') {
							/*
							 * LEFT POSITION
							 * RIGHT SWITCH
							 * TWO CUBE
							 * SLOW
							 */
							return 20;
						}
					}
				}
				else if (!TWO_CUBE){
					if (FAST) {
						if (switchPosition == 'L') {
							/*
							 * LEFT POSITION
							 * LEFT SWITCH
							 * ONE CUBE
							 * FAST
							 */
							return 21;
						}
						else if (switchPosition == 'R') {
							/*
							 * LEFT POSITION
							 * RIGHT SWITCH
							 * ONE CUBE
							 * FAST
							 */
							return 22;
						}
					}
					else if (!FAST) {
						if (switchPosition == 'L') {
							/*
							 * LEFT POSITION
							 * LEFT SWITCH
							 * ONE CUBE
							 * SLOW
							 */
							return 23;
						}
						else if (switchPosition == 'R') {
							/*
							 * LEFT POSITION
							 * RIGHT SWITCH
							 * ONE CUBE
							 * SLOW
							 */
							return 24;
						}
					}
				}
			}
		}
		else if (HARDCODED_POSITION == 3) {
			{
				if (PRIORITIZE_SCALE) {
					if (TWO_CUBE) {
						if (FAST) {
							if (scalePosition == 'L') {
								/*
								 * RIGHT POSITION
								 * LEFT SCALE
								 * TWO CUBE
								 * FAST
								 */
								return 25;
							}
							else if (scalePosition == 'R') {
								/*
								 * RIGHT POSITION
								 * RIGHT SCALE
								 * TWO CUBE
								 * FAST
								 */
								return 26;
							}
						}
						else if (!FAST){
							if (scalePosition == 'L') {
								/*
								 * RIGHT POSITION
								 * LEFT SCALE
								 * TWO CUBE
								 * SLOW
								 */
								return 27;
							}
							else if (scalePosition == 'R') {
								/*
								 * RIGHT POSITION
								 * RIGHT SCALE
								 * TWO CUBE
								 * SLOW
								 */
								return 28;
							}
						}
					}
					else if (!TWO_CUBE){
						if (FAST) {
							if (scalePosition == 'L') {
								/*
								 * RIGHT POSITION
								 * LEFT SCALE
								 * ONE CUBE
								 * FAST
								 */
								return 29;
							}
							else if (scalePosition == 'R') {
								/*
								 * RIGHT POSITION
								 * RIGHT SCALE
								 * ONE CUBE
								 * FAST
								 */
								return 30;
							}
						}
						else if (!FAST){
							if (scalePosition == 'L') {
								/*
								 * RIGHT POSITION
								 * LEFT SCALE
								 * ONE CUBE
								 * SLOW
								 */
								return 31;
							}
							else if (scalePosition == 'R') {
								/*
								 * RIGHT POSITION
								 * RIGHT SCALE
								 * ONE CUBE
								 * SLOW
								 */
								return 32;
							}
						}
					}
				}
				else if (!PRIORITIZE_SCALE){
					if (TWO_CUBE) {
						if (FAST) {
							if (switchPosition == 'L') {
								/*
								 * RIGHT POSITION
								 * LEFT SWITCH
								 * TWO CUBE
								 * FAST
								 */
								return 33;
							}
							else if (switchPosition == 'R') {
								/*
								 * RIGHT POSITION
								 * RIGHT SWITCH
								 * TWO CUBE
								 * FAST
								 */
								return 34;
							}
						}
						else if (!FAST) {
							if (switchPosition == 'L') {
								/*
								 * RIGHT POSITION
								 * LEFT SWITCH
								 * TWO CUBE
								 * SLOW
								 */
								return 35;
							}
							else if (switchPosition == 'R') {
								/*
								 * RIGHT POSITION
								 * RIGHT SWITCH
								 * TWO CUBE
								 * SLOW
								 */
								return 36;
							}
						}
					}
					else if (!TWO_CUBE) {
						if (FAST) {
							if (switchPosition == 'L') {
								/*
								 * RIGHT POSITION
								 * LEFT SWITCH
								 * ONE CUBE
								 * FAST
								 */
								return 37;
							}
							else if (switchPosition == 'R') {
								/*
								 * RIGHT POSITION
								 * RIGHT SWITCH
								 * ONE CUBE
								 * FAST
								 */
								return 38;
							}
						}
						else if (!FAST){
							if (switchPosition == 'L') {
								/*
								 * RIGHT POSITION
								 * LEFT SWITCH
								 * ONE CUBE
								 * SLOW
								 */
								return 39;
							}
							else if (switchPosition == 'R') {
								/*
								 * RIGHT POSITION
								 * RIGHT SWITCH
								 * ONE CUBE
								 * SLOW
								 */
								return 40;
							}
						}
					}
				}
			}
		}
		return 0;
	}
}
