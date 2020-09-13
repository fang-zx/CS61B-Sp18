package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int N = oomages.size();
        int[] nums = new int[M];
        for (Oomage o: oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            nums[bucketNum]++;
        }
        for (int num: nums) {
            if (num < N / 50 || num > N / 2.5) {
                return false;
            }
        }
        return true;
    }
}
