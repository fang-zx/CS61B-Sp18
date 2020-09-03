import static org.junit.Assert.*;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class TestArrayDequeGold {

    @Test
    public void testTwoDeque() {
        String methodCalls = "";
        StudentArrayDeque<Integer> sad = new StudentArrayDeque();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution();

        for (int i = 0; true; i++) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.4) {
                sad.addFirst(i);
                ads.addFirst(i);
                methodCalls += "addFirst(" + i + ")\n";

                if (numberBetweenZeroAndOne < 0.3) {
                    Integer actual = sad.removeLast();
                    Integer expected = ads.removeLast();
                    methodCalls += "removeLast(" + i + ")\n";
                    
                    assertEquals(methodCalls, expected, actual);
                }
            } else {
                sad.addLast(i);
                ads.addLast(i);
                methodCalls += "addLast(" + i + ")\n";
                if (numberBetweenZeroAndOne > 0.6) {
                    Integer actual = sad.removeFirst();
                    Integer expected = ads.removeFirst();
                    methodCalls += "removeFirst(" + i + ")\n";
                    assertEquals(methodCalls, expected, actual);
                }
            }
        }
    }
}
