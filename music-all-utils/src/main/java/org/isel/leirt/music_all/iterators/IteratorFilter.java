/*
 * GNU General Public License v3.0
 *
 * Copyright (c) 2019, Miguel Gamboa (gamboa.pt)
 *
 *   All rights granted under this License are granted for the term of
 * copyright on the Program, and are irrevocable provided the stated
 * conditions are met.  This License explicitly affirms your unlimited
 * permission to run the unmodified Program.  The output from running a
 * covered work is covered by this License only if the output, given its
 * content, constitutes a covered work.  This License acknowledges your
 * rights of fair use or other equivalent, as provided by copyright law.
 *
 *   You may make, run and propagate covered works that you do not
 * convey, without conditions so long as your license otherwise remains
 * in force.  You may convey covered works to others for the sole purpose
 * of having them make modifications exclusively for you, or provide you
 * with facilities for running those works, provided that you comply with
 * the terms of this License in conveying all material for which you do
 * not control copyright.  Those thus making or running the covered works
 * for you must do so exclusively on your behalf, under your direction
 * and control, on terms that prohibit them from making any copies of
 * your copyrighted material outside their relationship with you.
 *
 *   Conveying under any other circumstances is permitted solely under
 * the conditions stated below.  Sublicensing is not allowed; section 10
 * makes it unnecessary.
 *
 */

package org.isel.leirt.music_all.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

public class IteratorFilter<T>  implements Iterator<T> {
    private Iterator<T> src;
    private Predicate<T> p;

    private Optional<T> box;


    public IteratorFilter(Iterable<T> src, Predicate<T> p) {
        this.src = src.iterator();
        this.p=p;
        box = Optional.empty();
    }

    @Override
    public boolean hasNext() {
        if (box.isPresent()) return true;
        while(src.hasNext()) {
            T item = src.next();
            if (p.test(item)) {
                box = Optional.of(item);
                return true;
            }
        }
        return false;
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new NoSuchElementException();
        T item = box.get();
        box = Optional.empty();
        return item;
    }
}