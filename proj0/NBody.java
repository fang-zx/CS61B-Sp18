public class NBody {
	public static double readRadius(String filename) {
		In in = new In(filename);
		int n = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Planet[] readPlanets(String filename) {
		In in = new In(filename);
		int n = in.readInt();
		Planet[] planets = new Planet[n];

		double radius = in.readDouble();
		for (int i = 0; i < n; i++) {
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();
			planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
		}
		return planets;
	}

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Error: input format 'java NBody T dt filename'");
			return;
		}
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];

		double radius = readRadius(filename);
	        Planet[] planets = readPlanets(filename);
		
		StdDraw.setScale(-radius/2, radius/2);
		StdDraw.clear();

		StdDraw.picture(0, 0, "images/starfield.jpg");
	
		for (Planet p: planets) {
			p.draw();
		}
		
		StdDraw.enableDoubleBuffering();

		for (double i = 0; i < T; i += dt) {
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];

			for (int j = 0; j < planets.length; j++) {
				xForces[j] = planets[j].calcNetForceExertedByX(planets);
				yForces[j] = planets[j].calcNetForceExertedByY(planets);
			}
			for (int j = 0; j < planets.length; j++) {
				StdDraw.clear();
				StdDraw.picture(0, 0, "images/starfield.jpg");

				planets[j].update(dt, xForces[j], yForces[j]);
				planets[j].draw();
			}
		}

		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
					planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
					planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
		}


	}	
}
