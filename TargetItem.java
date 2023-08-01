package FullDroneGUI;


public class TargetItem extends ArenaItem {
	private int score;      // if a ball hit it, it will count 1
	
	public TargetItem(double ix, double iy, double ir) {
		super(ix, iy, ir);	// set up drone
		score=0;			// initialise score
		col = 'g';			// override colour to green
	}
	public void drawItem(MyCanvas mc) {
		super.drawItem(mc);		// draw item
		mc.showInt(x, y, score);	// put score on top
	}
	
	@Override 
	// this method exists in parent class, but we override it in the child class
	// check whether it has been hit
	protected void checkItem(DroneArena d) {
		if (d.checkHit(this)) score++;// if been hit, then increase score
		if (d.checkHitAvoider(this)) score-=2;
	}
	@Override
	protected void adjustItem() {
		// nothing to do
	}
	
	@Override
	protected String getStrType() {
		return "Target";
	}
	
	
}
