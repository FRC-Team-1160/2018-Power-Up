package org.usfirst.frc.team1160.robot;

public interface AutoSettings {
	
	/**
	 * SHIT TO TAKE CARE OF BEFORE EACH MATCH (WOO LOGIC)
	 * 
	 * HARDCODED_POSITION
		 * 1 - Left
		 * 2 - Center
		 * 3 - Right
	 * PRIORITIZE_OPPOSITE_SCALE
	 	 * true - go for the opposite scale above all else!
	 	 * false - lol forget about it
 	 * PRIORITIZE_SAME_SCALE
 	 	 * true - go for the same side scale above all else!
 	 	 * false - ranking points matter!
 	 * PRIORITIZE_ALL_SCALE
 	 	 * true - go for the scale NO MATTER THE SIDE
 	 * PRIORITIZE_ALL_SWITCH
 	 	 * true - go for the switch NO MATTER THE SIDE
 	 * PRIORITIZE_OPPOSITE_SWITCH
 	 * PRIORITIZE_SAME_SWITCH
	 * PRIORITIZE_FAST_CENTER_SWITCH
	 	 * true - have a nice fast center switch auto (it's also untested so lol)
	 	 * false - do not have a nice fast center switch auto ("slow is smooth and smooth is fast")
 	 * 
	 */
	//Position
	public static final int HARDCODED_POSITION = 1;
	
	/*
	 * if the below two are the same value, then PRIORITIZE_SWITCH will be set to true
	 * (PRIORITIZE_SWITCH comes before PRIORITIZE_SCALE if both are the same value)
	 */
	public static final boolean PRIORITIZE_SCALE = true;
	public static final boolean PRIORITIZE_SWITCH = true;
	
	/*
	 * if the below two are the same value, then 
	 */
	public static final boolean PRIORITIZE_SAME_SIDE = true;
	public static final boolean PRIORITIZE_OPPOSITE_SIDE = true;
	
	/**
	 * A brief dissertation on why there is a prioritize_all_scale AND a prioritize_all_switch,
	 * by kyle (3/27/18)
	 * 
	 * If x is false, the opposite of x isn't necessarily true. In the case of booleans, it is. In literally
	 * everywhere else in life, it isn't. If I hate hamburgers, it doesn't necessarily mean that I like
	 * things that aren't hamburgers-- it just means that I have no particular disposition/inclination towards
	 * them. If I am not gay, it doesn't necessarily mean that I'm not straight.
	 * 
	 * Think of this in terms of "gray-area" arguments - absent proactive arguments for either side, the issue
	 * rests in a gray area and could go either way.
	 * 
	 * What does this mean? If both prioritize_all_scale and prioritize_all_switch are false (and yes, that
	 * is logically possible), then the robot will take the path of least resistance. That said, it's not
	 * logically possible for prioritize_all_scale and prioritize_all_switch to be both true.
	 * 
	 * Boolean Precedence Order
	 *  TOP
	 *   |	PRIORITIZE_ALL_SCALE 					| PRIORITIZE_ALL_SWITCH
	 *   |	 
	 *   |
	 *   |
	 *   |
	 *   |
	 *   |
	 *   |
	 *   |
	 * BOTTOM
	 */
	
}
