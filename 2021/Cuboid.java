import java.math.*;
import java.util.*;

public class Cuboid {
	public int xMin, xMax, yMin, yMax, zMin, zMax;
	public boolean on;
	
	public Cuboid(boolean on, int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
		this.on = on;
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.zMin = zMin;
		this.zMax = zMax;
	}
	
	public BigInteger area() {
		return BigInteger.valueOf((xMax-xMin)+1).multiply(BigInteger.valueOf(((yMax-yMin)+1)).multiply(BigInteger.valueOf(((zMax-zMin)+1))));
	}
	
	public Cuboid overlap(Cuboid other) {
		if(other.xMin > xMax ||
				other.xMax < xMin ||
				other.yMin > yMax ||
				other.yMax < yMin ||
				other.zMin > zMax ||
				other.zMax < zMin) {
			return null;
		}
		return new Cuboid(on && other.on,
				Math.max(xMin, other.xMin),
				Math.min(xMax, other.xMax),
				Math.max(yMin, other.yMin),
				Math.min(yMax, other.yMax),
				Math.max(zMin, other.zMin),
				Math.min(zMax, other.zMax));
	}
	
	public ArrayList<Cuboid> cutout(Cuboid other) {
		ArrayList<Cuboid> out = new ArrayList<Cuboid>();
		
		boolean leftUsed = false;
		boolean rightUsed = false;
		boolean bottomUsed = false;
		boolean topUsed = false;
		
		if(other.xMin > xMin) {
			out.add(new Cuboid(on, xMin, other.xMin-1, yMin, yMax, zMin, zMax));
			leftUsed = true;
		}
		
		if(other.xMax < xMax) {
			out.add(new Cuboid(on, other.xMax+1, xMax, yMin, yMax, zMin, zMax));
			rightUsed = true;
		}
		
		if(other.yMin > yMin) {
			if(!leftUsed && !rightUsed) {
				out.add(new Cuboid(on, xMin, xMax, yMin, other.yMin-1, zMin, zMax));
			} else if(!leftUsed) {
				out.add(new Cuboid(on, xMin, other.xMax, yMin, other.yMin-1, zMin, zMax));
			} else if(!rightUsed) {
				out.add(new Cuboid(on, other.xMin, xMax, yMin, other.yMin-1, zMin, zMax));
			} else {
				out.add(new Cuboid(on, other.xMin, other.xMax, yMin, other.yMin-1, zMin, zMax));
			}
			bottomUsed = true;
		}
		
		if(other.yMax < yMax) {
			int xs, xb;
			if(leftUsed) {
				xs = other.xMin;
			} else {
				xs = xMin;
			}
			if(rightUsed) {
				xb = other.xMax;
			} else {
				xb = xMax;
			}
			out.add(new Cuboid(on, xs, xb, other.yMax+1, yMax, zMin, zMax));
			topUsed = true;
		}
		
		if(other.zMin > zMin) {
			int xs, xb;
			if(leftUsed) {
				xs = other.xMin;
			} else {
				xs = xMin;
			}
			if(rightUsed) {
				xb = other.xMax;
			} else {
				xb = xMax;
			}
			int ys, yb;
			if(bottomUsed) {
				ys = other.yMin;
			} else {
				ys = yMin;
			}
			if(topUsed) {
				yb = other.yMax;
			} else {
				yb = yMax;
			}
			out.add(new Cuboid(on, xs, xb, ys, yb, zMin, other.zMin-1));
		}
		
		if(other.zMax < zMax) {
			int xs, xb;
			if(leftUsed) {
				xs = other.xMin;
			} else {
				xs = xMin;
			}
			if(rightUsed) {
				xb = other.xMax;
			} else {
				xb = xMax;
			}
			int ys, yb;
			if(bottomUsed) {
				ys = other.yMin;
			} else {
				ys = yMin;
			}
			if(topUsed) {
				yb = other.yMax;
			} else {
				yb = yMax;
			}
			out.add(new Cuboid(on, xs, xb, ys, yb, other.zMax+1, zMax));
		}
		
		
		return out;
	}
}
