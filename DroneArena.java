package FullDroneGUI;

import java.util.ArrayList;
import java.util.Random;

import week_9.TargetBall;


public class DroneArena {
	double xSize, ySize;					//size of arena
	private ArrayList<ArenaItem> allItems;		//array list of all balls in arena
	Random generator;
	DroneArena(){
		this(500, 400);			//default size
	}
	
	DroneArena(double xS, double yS){
		xSize = xS;
		ySize = yS;
		generator = new Random();
		allItems = new ArrayList<ArenaItem>();			//list of all balls, initially empty
		allItems.add(new Drone(xS/2, yS/2, 10, 45, 5)); //add game ball
		allItems.add(new TargetItem(xS/2, yS - 20, 20));
		allItems.add(new AvoidDrone(generator.nextDouble(xSize), generator.nextDouble(xSize), 8, 20, 5));
		allItems.add(new Obstacle(40, 40, 12));
		allItems.add(new Enemy(generator.nextDouble(xSize), generator.nextDouble(xSize), 12));
		allItems.add(new Enemy(generator.nextDouble(xSize), generator.nextDouble(xSize), 15));
		allItems.add(new Enemy(generator.nextDouble(xSize), generator.nextDouble(xSize), 18));
		allItems.add(new Enemy(generator.nextDouble(xSize), generator.nextDouble(xSize), 10));
		allItems.add(new Enemy(generator.nextDouble(xSize), generator.nextDouble(xSize), 10));

		}
	
	/**
	 * return arena size in x direction
	 * @return
	 */
	public double getXSize() {
		return xSize;
	}
	/**
	 * return arena size in y direction
	 * @return
	 */
	public double getYSize() {
		return ySize;
	}
	/** 
	 * draw all balls in the arena into interface bi
	 * @param bi
	 */
	public void drawArena(MyCanvas mc) {
		for (ArenaItem d: allItems) d.drawItem(mc);    //draw all drones
	}
	
	/**
	 * check all balls -- see if need to change angle of moving drones, etc
	 */
	public void checkDrones() {
		for (ArenaItem d: allItems) d.checkItem(this);//check all drones
	}
	/**
	 * adjust all balls -- move any moving ones
	 */
	public void adjustDrones() {
//		for (ArenaItem d: allItems) {
//			d.adjustItem();//adjust all drones
//		}
		for (int ct = 0; ct < allItems.size(); ct++) {
			ArenaItem d;
			d = allItems.get(ct);
			d.adjustItem();    //adjust all drones
		}
	}
	
	/**
	 * set the avoider at x,y
	 * @param x
	 * @param y
	 */
	public void setObstacle(double x, double y) {
		for (ArenaItem d: allItems) 
			if(d instanceof Obstacle) d.setXY(x, y);//check all balls
	}
	
	/**
	 * return list of strings defining each drone
	 * @return
	 */
	public ArrayList<String> describeAll(){
		ArrayList<String> ans = new ArrayList<String>();
		for (ArenaItem d: allItems) ans.add(d.toString());
		return ans;
	}
	/**
	 * check angle of drone -- if hitting wall, reboundl if hitting drone, change angle
	 * @param x
	 * @param y
	 * @param rad
	 * @param ang
	 * @param notID
	 * @return
	 */
	public double CheckDroneAngle(double x, double y, double rad, double ang, int notID) {
		double ans = ang;
		if (x < rad || x > xSize - rad) ans = 180 - ans;
		// if ball hit (tried to go through) left or right walls, set mirror angle, being 180-angle
		if (y < rad || y > ySize - rad) ans = - ans;
		// if ball hit (tried to go through) top or bottom walls, set mirror angle, being -angle
		
		for (ArenaItem d: allItems)
			if(d.getID() != notID && d.hitting(x, y, rad)) ans = 180 * Math.atan2(y-d.getY(), x-d.getX())/Math.PI;
				//check all balls except one with given id
				//if hitting, return angle between the other ball and this one
		return ans;		//return the angle
	}
	
	/**
	 * check if the target ball has been hit by another ball
	 * @param target the target ball
	 * @return true if hit
	 */
	public boolean checkHit(ArenaItem target) {
		boolean ans = false;
		for (ArenaItem d: allItems)
			if(d instanceof Drone && d.hitting(target)) ans = true;
		//try all drones, if drone, check if hitting the target
		return ans;
	}
	
	public boolean checkHitAvoider(ArenaItem target) {
		boolean ans = false;
		for (ArenaItem d: allItems)
			if(d instanceof AvoidDrone && d.hitting(target)) ans = true;
		//try all drones, if drone, check if hitting the target
		return ans;
	}
	
	public void addDrone() {
		allItems.add(new Drone(xSize/2, ySize/2, 10, 10, 10));
	}
	
	public void addObstacle() {
		allItems.add(new Obstacle(generator.nextDouble(xSize), generator.nextDouble(xSize), 12));
	}
	
	public void addAvoider() {
		allItems.add(new AvoidDrone(generator.nextDouble(xSize), generator.nextDouble(xSize), 8, 20, 10));
	}
	
	public void main(String[] args) {
		DroneArena arena;
		arena = new DroneArena(400, 500);
		arena.toString();
	}
}