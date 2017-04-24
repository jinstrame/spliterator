package spliterators.part2.exercise;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

public class ZipWithIndexDoubleSpliterator extends Spliterators.AbstractSpliterator<IndexedDoublePair> {


    private final OfDouble inner;
    private int currentIndex;

    public ZipWithIndexDoubleSpliterator(OfDouble inner) {
        this(0, inner);
    }

    private ZipWithIndexDoubleSpliterator(int firstIndex, OfDouble inner) {
        super(inner.estimateSize(), inner.characteristics());
        currentIndex = firstIndex;
        this.inner = inner;
    }

    @Override
    public int characteristics() {
        return inner.characteristics();
    }

    @Override
    public boolean tryAdvance(Consumer<? super IndexedDoublePair> action) {
        DoubleConsumer a = d -> action.accept(new IndexedDoublePair(currentIndex, d));
        return inner.tryAdvance(a);
    }

    @Override
    public void forEachRemaining(Consumer<? super IndexedDoublePair> action) {
        DoubleConsumer a = d -> action.accept(new IndexedDoublePair(currentIndex, d));
        inner.forEachRemaining(a);
    }

    @Override
    public Spliterator<IndexedDoublePair> trySplit() {
        ZipWithIndexDoubleSpliterator ret = null;
        if (inner.hasCharacteristics(Spliterator.SIZED)) {
            long size = inner.estimateSize();
            ret = new ZipWithIndexDoubleSpliterator(currentIndex, inner.trySplit());
            currentIndex += size;
        }
        return ret;
    }

    @Override
    public long estimateSize() {
        return inner.estimateSize();
    }
}
