package FullDroneGUI;

public class Obstacle extends ArenaItem {
	Obstacle(){
		
	}
	Obstacle (double ix, double iy, double ir){
		super(ix, iy, ir);
		col = 'b';
	}
	
	@Override
	protected void checkItem (DroneArena a) {
		
	}
	
	@Override
	protected void adjustItem() {
		
	}
	
	protected String getStrType() {
		return "Obstacle";
	}
}
