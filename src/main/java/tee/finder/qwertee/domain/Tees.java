package tee.finder.qwertee.domain;

import java.util.Arrays;
import java.util.List;

public class Tees {

    private List<Tee> value;

    public Tees(Tee... value) {
        this.value = Arrays.asList(value);
    }

    public List<Tee> getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tees tees = (Tees) o;

        return value != null ? value.equals(tees.value) : tees.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
