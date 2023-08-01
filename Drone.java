package FullDroneGUI;

public class Drone extends ArenaItem {
	protected double angle, speed;
	public Drone() {
		this(0,0,0,0,0);
	}
	public Drone(double rx, double ry, double rr, double initAngle, double rsp){
		super(rx, ry, rr);
		angle = initAngle;
		speed = rsp;
	}
	@Override
	protected void checkItem(DroneArena d) {
		// TODO Auto-generated method stub
		angle = d.CheckDroneAngle(x, y, rad, angle, itemID);
	}
	@Override
	protected void adjustItem() {
		// TODO Auto-generated method stub
		double radAngle = angle*Math.PI/180;		// put angle in radians
		x += speed * Math.cos(radAngle);		// new X position
		y += speed * Math.sin(radAngle);		// new Y position
	}
	/**
	 * return string defining item type
	 */
	protected String getStrType() {
		return "Drone";
	}
}
