package spliterators.part1.exercise;

import org.junit.Test;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RectangleSpliteratorTest {

    private int[][] array = {{1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}};

    private int[][] array2 = {{1, 2, 3, 4},
            {4, 5, 6, 6},
            {7, 8, 9, 9}};

    private RectangleSpliterator getSpliterator(){
        return new RectangleSpliterator(array);
    }

    private RectangleSpliterator getSpliterator2(){
        return new RectangleSpliterator(array2);
    }

    @Test
    public void testTryAdvance() {
        RectangleSpliterator spliterator = getSpliterator();

        IntConsumer c = System.out::println;
        spliterator.tryAdvance(c);
        spliterator.tryAdvance(c);
    }

    @Test
    public void anyMatchTest(){
        IntStream intStream1 = StreamSupport.intStream(getSpliterator(), true);
        IntStream intStream2 = StreamSupport.intStream(getSpliterator(), true);
        IntStream intStream3 = StreamSupport.intStream(getSpliterator(), true);
        assertThat(intStream1.anyMatch(e -> e == 6), is(true));
        assertThat(intStream2.skip(5).anyMatch(e -> e == 6), is(true));
        assertThat(intStream3.skip(7).anyMatch(e -> e == 6), is(false));
    }

    @Test
    public void sumTest(){
        IntStream intStream1 = StreamSupport.intStream(getSpliterator(), true);
        IntStream intStream2 = StreamSupport.intStream(getSpliterator(), true);
        IntStream intStream3 = StreamSupport.intStream(getSpliterator(), true);
        assertThat(intStream1.sum(), is(45));
        assertThat(intStream2.skip(1).sum(), is(44));
        assertThat(intStream3.skip(2).limit(6).sum(), is(33));
    }

    @Test
    public void distinctTest(){
        IntStream intStream1 = StreamSupport.intStream(getSpliterator2(), true).distinct();
        IntStream intStream2 = StreamSupport.intStream(getSpliterator2(), true).distinct();
        assertThat(intStream1.anyMatch(e -> e == 6), is(true));
        assertThat(intStream2.sum(), is(45));
    }

}