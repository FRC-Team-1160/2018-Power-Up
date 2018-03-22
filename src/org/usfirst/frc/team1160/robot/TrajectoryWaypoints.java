package org.usfirst.frc.team1160.robot;

import jaci.pathfinder.Waypoint;

public interface TrajectoryWaypoints {
	
	/* NAMING CONVENTIONS
	 * 
	 * public static final Waypoint[] STARTPOSITION_ENDPOSITION = ...;
	 */
	
	public static final Waypoint[] POINTS_1 =
			new Waypoint[] {new Waypoint(0, 0, 0), new Waypoint(7, 0, 0)};
	public static final Waypoint[] POINTS_2 = 
			new Waypoint[] {
					new Waypoint(0, 0, 0), new Waypoint(7, 7, 90)
					
						/*new Waypoint(2,4,0),
							new Waypoint(12.5,4,45),
							new Waypoint(14,7,90)
							*/
							};
	
	//From the center to the left switch
	public static final Waypoint[] CENTER_LEFT_SWITCH_1 = 
			new Waypoint[] {new Waypoint(0,0,0),new Waypoint(2,0,0)};
	public static final Waypoint[] CENTER_LEFT_SWITCH_2 = 
			new Waypoint[] {new Waypoint(0,0,0),new Waypoint(5.0*Math.pow(2, 0.5),0,0)};
	public static final Waypoint[] CENTER_LEFT_SWITCH_3 = 
			new Waypoint[] {new Waypoint(0,0,0),new Waypoint(2.0,0,0)};

	//From the center to the left switch BACKWARDS
	public static final Waypoint[] CENTER_LEFT_SWITCH_1_BACKWARDS = 
			new Waypoint[] {new Waypoint(0,0,0),new Waypoint(-3,0,0)};
	public static final Waypoint[] CENTER_LEFT_SWITCH_2_BACKWARDS = 
			new Waypoint[] {new Waypoint(0,0,0),new Waypoint(-40*Math.pow(2, 0.5)/12,0,0)};
	public static final Waypoint[] CENTER_LEFT_SWITCH_3_BACKWARDS = 
			new Waypoint[] {new Waypoint(0,0,0),new Waypoint(-64/12,0,0)};
	
	//From the center to the right switch
	
	/* THIS IS FROM PRE-THURSDAY LV CODE
	public static final Waypoint[] CENTER_RIGHT_SWITCH = 
			new Waypoint[] {new Waypoint(0,0,0),new Waypoint(140/12,0,0)};
	*/
	public static final Waypoint[] CENTER_RIGHT_SWITCH_1 = 
			new Waypoint[] {new Waypoint(0,0,0),new Waypoint(2,0,0)};
	public static final Waypoint[] CENTER_RIGHT_SWITCH_2 =
			new Waypoint[] {new Waypoint(0,0,0),new Waypoint(4.5*Math.pow(2, 0.5),0,0)};
	public static final Waypoint[] CENTER_RIGHT_SWITCH_3 =
			new Waypoint[] {new Waypoint(0,0,0),new Waypoint(2,0,0)};
	
	//From the X side to the X switch where X is either left or right
	public static final Waypoint[] X_X_SWITCH_1 = 
			new Waypoint[] {new Waypoint(0,0,0),new Waypoint(12.4,0,0)};
	public static final Waypoint[] X_X_SWITCH_2 = 
			new Waypoint[] {new Waypoint(0,0,0),new Waypoint(1.5,0,0)};
	
	//Straight line from start to switch
	public static final Waypoint[] START_SWITCH =
			new Waypoint[] {new Waypoint(0, 0, 0), new Waypoint(140/12, 0, 0)};

}
