package org.isel.leirt.music_all.queries;

import org.isel.leirt.music_all.iterators.*;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.isel.leirt.music_all.Errors.TODO;


public interface PipeIterable<T> extends Iterable<T> {
    
    // factory operations
    
    static <T> PipeIterable<T> generate(Supplier<T> next){
        return () -> new IteratorGenerate<>(next);
    }
    
    
    static <T> PipeIterable<T> iterate(T seed, Function<T, T> next){
        TODO("generate");
        return null;
    }
    
    static <T> PipeIterable<T> of(T... elems) {
        return () -> new IteratorArray<>(elems);
    }
    
    static <T> PipeIterable<T> of(Iterable<T> items) {
        return () -> items.iterator();
    }
    
    static PipeIterable<Integer> range(int min, int max) {
        return () ->
                   new Iterator<>() {
                       private int curr = min;
                       
                       @Override
                       public boolean hasNext() {
                           return curr <= max;
                       }
                       
                       @Override
                       public Integer next() {
                           if (!hasNext())
                               throw new NoSuchElementException();
                           return curr++;
                       }
                   };
    }
    
    // intermediate operations
    
    default PipeIterable<T> filter(Predicate<T> pred){
        return () -> new IteratorFilter<>(this, pred);
    }
    
    default <R> PipeIterable<R> map(Function<T, R> mapper){
        return () -> new IteratorMap<>(this, mapper);
    }
    
    default <R> PipeIterable<R> flatMap(Function<T, Iterable<R>> mapper){
        return () -> new IteratorFlatMap<>(this,mapper);
    }
    
    default PipeIterable<T> takeWhile(Predicate<T> pred){
        return () -> new IteratorTakeWhile<>(this,pred);
    }
    
    default PipeIterable<T> skipWhile(Predicate<T> pred) {
        TODO("skipWhile");
        return null;
    }
    
    default  PipeIterable<T> skip( int nr)
    {
        return () -> {
            int curr = 0;
            Iterator<T> it = iterator();
            while(it.hasNext() && curr < nr) {
                it.next();
            }
            return it;
        };
    }
    
    default PipeIterable<T> limit(int nr){
        TODO("limit");
        return null;
    }
    
    default PipeIterable<T> cache() {
        TODO("cache");
        return null;
    }
    
    // terminal operations
    
    default int count() {
        int count=0;
        for(T item:this)
            count++;
        return count;
    }
    
    default T[] toArray(T[] proto) {
        List<T> res = new ArrayList<>();
        for(T val:this) {
            res.add(val);
        }
        return toList().toArray(proto);
    }
    
    default List<T> toList() {
        List<T> res = new ArrayList<>();
        for(T val:this) {
            res.add(val);
        }
        return res;
    }
    
    default Optional<T> first() {
        Iterator<T> it = iterator();
        if (!it.hasNext()) return Optional.empty();
        return Optional.of(it.next());
    }
    
    default Optional<T> max(Comparator<T> cmp) {
        Iterator<T> it = iterator();
        if (!it.hasNext()) return Optional.empty();
        T m = it.next();
        while(it.hasNext()) {
            T n = it.next();
            if (cmp.compare(n, m) > 0) m =n;
        }
        return Optional.of(m);
    }
    
    default Optional<T> reduce(BinaryOperator<T> accumulator) {
        TODO("reduce");
        return null;
    }
    
    default Optional<T> last() {
        TODO("last");
        return null;
    }
}