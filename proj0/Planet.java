public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static final double G = 6.67e-11;

	public Planet(double xP, double yP, double xV, 
			double yV, double m, String img) {
		this.xxPos = xP;
		this.yyPos = yP;
		this.xxVel = xV;
		this.yyVel = yV;
		this.mass = m;
		this.imgFileName = img;
	}

	public Planet(Planet p) {
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = new String(p.imgFileName);
	}
	
	/** Return the distance between this planet and the given one */
	public double calcDistance(Planet other) {
		double xDis = this.xxPos - other.xxPos;
		double yDis = this.yyPos - other.yyPos;
		return Math.sqrt(xDis * xDis + yDis * yDis);
	}
	
	/** Return the force exerted on this planet by the given planet */
	public double calcForceExertedBy(Planet other) {
		double dist = calcDistance(other);
		double f = G * this.mass * other.mass / dist / dist;
		return f;
	}
	
	/** Return the force exerted in the X direction */
	public double calcForceExertedByX(Planet other) {
		double dx = other.xxPos - this.xxPos;
		double dist = calcDistance(other);
		double force = calcForceExertedBy(other);
		return force * dx  / dist;
	}

	/** Return the force exerted in the Y direction */
	public double calcForceExertedByY(Planet other) {
		double dy = other.yyPos - this.yyPos;
		double dist = calcDistance(other);
		double force = calcForceExertedBy(other);
		return force * dy / dist;
	}

	/** Return the net force exerted in the X direction */
	public double calcNetForceExertedByX(Planet[] planets) {
		double netForceX = 0;
		for (Planet p: planets) {
			if (this.equals(p)) continue;
			netForceX += calcForceExertedByX(p);
		}
		return netForceX;
	}

	/** Return the net force exerted in the Y direction */
	public double calcNetForceExertedByY(Planet[] planets) {
		double netForceY = 0;
		for (Planet p: planets) {
			if (this.equals(p)) continue;
			netForceY += calcForceExertedByY(p);
		}
		return netForceY;
	}

	public void update(double dt, double fX, double fY) {
		double ax = fX / mass;
		double ay = fY / mass;
		xxVel += dt * ax;
		yyVel += dt * ay;
		xxPos += dt * xxVel;
		yyPos += dt * yyVel;
	}

	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}
}
