package spliterators.part1.exercise;


import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

@SuppressWarnings("Duplicates")
public class RectangleHonestSpliterator extends Spliterators.AbstractIntSpliterator {

    private final int innerLength;
    private final int[][] array;
    private int startOuterInclusive;
    private int endOuterExclusive;
    private int startInnerInclusive;
    private int endInnerExclusive;

    public RectangleHonestSpliterator(int[][] array) {
        this(array, 0, array.length, 0);
    }

    private RectangleHonestSpliterator(int[][] array, int startOuterInclusive, int endOuterExclusive, int startInnerInclusive) {
        super(Long.MAX_VALUE, Spliterator.IMMUTABLE | Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.NONNULL);

        innerLength = array.length == 0 ? 0 : array[0].length;
        this.array = array;
        this.startOuterInclusive = startOuterInclusive;
        this.endOuterExclusive = endOuterExclusive;
        this.startInnerInclusive = startInnerInclusive;
        this.endInnerExclusive = innerLength;
    }

    private RectangleHonestSpliterator(int[][] array, int startOuterInclusive, int endOuterExclusive, int startInnerInclusive, int endInnerExclusive) {
        this(array, startOuterInclusive, endOuterExclusive, startInnerInclusive);
        this.endInnerExclusive = endInnerExclusive;
    }


    @Override
    public OfInt trySplit() {
        int size = startOuterInclusive - endOuterExclusive;
        if (size < 2) return null;

        int mid = startOuterInclusive + size / 2;

        RectangleHonestSpliterator ret = new RectangleHonestSpliterator(array, startOuterInclusive, endOuterExclusive, startInnerInclusive);
        startOuterInclusive = mid;
        startInnerInclusive = 0;

        return ret;
    }

    @Override
    public long estimateSize() {
        return ((long) endOuterExclusive - startOuterInclusive) * innerLength - startInnerInclusive;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (startOuterInclusive >= endOuterExclusive)
            return false;

        if (startInnerInclusive >= innerLength) {
            startOuterInclusive += 1;
            startInnerInclusive = 0;
            return tryAdvance(action);
        }

        int element = array[startOuterInclusive][startInnerInclusive];
        startInnerInclusive += 1;
        action.accept(element);
        return true;
    }
}