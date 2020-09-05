import edu.princeton.cs.algs4.StdAudio;

public class GuitarHero {
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        synthesizer.GuitarString[] strings = new synthesizer.GuitarString[37];

        for (int i = 0; i < 37; i++) {
            strings[i] = new synthesizer.GuitarString(440 * Math.pow(2.0, (i-24)/12));
        }
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index < 0) {
                    continue;
                }
                else {
                    strings[index].pluck();
                }
            }

            double sample = 0;
            for (int j = 0; j < strings.length; j++) {
                sample += strings[j].sample();
            }

            StdAudio.play(sample);

            for (int j = 0; j < strings.length; j++) {
                strings[j].tic();
            }
        }
    }
}
