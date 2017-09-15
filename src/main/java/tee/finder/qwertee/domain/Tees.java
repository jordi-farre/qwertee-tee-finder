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

}
