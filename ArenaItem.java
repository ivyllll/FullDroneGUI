package FullDroneGUI;



public abstract class ArenaItem {
	protected double x, y, rad;	// position, size   
	protected char col;       
	static int ballCounter = 0;		// used to give each ball a unique identifier
	protected int itemID;
	
	
	ArenaItem() {
		this(100, 100, 10);
	}
	/**
	 * construct arena item (for static drone)
	 * @param ix	initial x position
	 * @param iy	y position
	 * @param ir	radius
	 */
	public ArenaItem(double ix, double iy, double ir) {
		x = ix;
		y = iy;
		rad = ir;
		itemID = ballCounter++;
		col = 'r';
	}
	
	/**
	 * return x position
	 * @return
	 */
	public double getX() { return x; }
	/**
	 * return y position
	 * @return
	 */
	public double getY() { return y; }
	/**
	 * return radius of item
	 * @return
	 */
	public double getRad() { return rad; }
	/**
	 * function to put item at nx,ny
	 * @param nx
	 * @param ny
	 */
	public void setXY(double nx, double ny) {
		x = nx;
		y = ny;
	}
	
	/**
	 * return the identity of item
	 * @return
	 */
	public int getID() {return itemID; }
	/**
	 * draw a item into the interface bi
	 * @param bi
	 */
	public void drawItem(MyCanvas mc) {
		mc.showCircle(x, y, rad, col);
	}

	protected String getStrType() {
		return "Item";
	}
	/** 
	 * return string describing ball
	 */
	public String toString() {
		return getStrType()+" at "+Math.round(x)+", "+Math.round(y);
	}
	
	/**
	 * abstract method for checking a item in arena b
	 * @param b
	 */
	protected abstract void checkItem(DroneArena b);
	/**
	 * abstract method for adjusting a item (?moving it)
	 */
	protected abstract void adjustItem();
	/**
	 * is item at ox,oy size or hitting this item
	 * @param ox
	 * @param oy
	 * @param or
	 * @return true if hitting
	 */
	public boolean hitting(double ox, double oy, double or) {
		return (ox-x)*(ox-x) + (oy-y)*(oy-y) < (or+rad)*(or+rad);
	}		// hitting if dist between items and ox,oy < ist rad + or
	
	/** is drone hitting the other item
	 * 
	 * @param oItem - the other item
	 * @return true if hitting
	 */
	public boolean hitting (ArenaItem oItem) {
		if(oItem == null) {return false;}
		else {return hitting(oItem.getX(), oItem.getY(), oItem.getRad());}
	}
}
