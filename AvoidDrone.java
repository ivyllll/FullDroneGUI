package FullDroneGUI;

public class AvoidDrone extends Drone {
	double sensorRad;
	
	public AvoidDrone() {
		super();
		sensorRad = rad * 2;
	}
	
	/**
	 * constructor for a new avoider
	 * @param rx         its x position
	 * @param ry         its y position
	 * @param itsArena   the arena it is in
	 */
	public AvoidDrone(double rx, double ry, double rr, double initAngle, double rsp) {
		super(rx, ry, rr, initAngle, rsp);
		col = 'g';
		sensorRad = rad * 2;
	}
	
	@Override
	public void drawItem(MyCanvas mc) {
		char sensorColor = 'b';
		
		mc.showCircle(x, y, sensorRad, sensorColor);
		mc.showCircle(x, y, rad*1.5, 'w');
		mc.showCircle(x, y, rad, col);
	}


	protected String getStrType() {
		return "AvoidDrone";
	}

}
