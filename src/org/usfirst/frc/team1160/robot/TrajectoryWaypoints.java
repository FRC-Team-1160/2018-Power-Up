package org.usfirst.frc.team1160.robot;

import jaci.pathfinder.Waypoint;

public interface TrajectoryWaypoints {
	
	//TODO: Slap a bunch of waypoint arrays here
	
	public static final Waypoint[] POINTS_1 =
			new Waypoint[] {new Waypoint(0, 0, 0), new Waypoint(10, 0, 0)};
	public static final Waypoint[] POINTS_2 = 
			new Waypoint[] {new Waypoint(0,0,0),new Waypoint(7,7,90)};

}
