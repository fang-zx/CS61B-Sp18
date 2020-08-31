public class TestPlanet {
	public static void main(String[] args) {
		Planet p1 = new Planet(0, 0, 3, 5, 100, null);
		Planet p2 = new Planet(-2, 3, 0, 0, 150, null);
		System.out.println("Distance:" + p1.calcDistance(p2));
		System.out.println("p1's force exerted by p2:" + p1.calcForceExertedBy(p2));
		System.out.println("p1's x-force exerted by p2:" + p1.calcForceExertedByX(p2));
		System.out.println("p1's y-force exerted by p2:" + p1.calcForceExertedByY(p2));
		p1.update(1, -5, -2);
		System.out.println("After updating p1, posX:" + p1.xxPos + ", posY:" + p1.yyPos + 
				", xxVel:" + p1.xxVel + ", yyVel:" + p1.yyVel);
	}
}
