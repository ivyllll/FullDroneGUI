package FullDroneGUI;

public class Enemy extends ArenaItem {
	private int score;

	Enemy(double ix, double iy, double ir) {
		super(ix, iy, ir);
		score = 0;
		col = 'y';
	}	
	
	
	@Override
	protected void checkItem(DroneArena b) {
		// TODO Auto-generated method stub
		if (b.checkHit(this)) {
			score++;
			if (score == 1) col = 'o';
			if (score == 2) col = 'r';
			if (score > 2)  {rad = 0; x=-1;}
		}
	}
	
	@Override
	protected void adjustItem() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected String getStrType() {
		return "Enemy";
	}

}
