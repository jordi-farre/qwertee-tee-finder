package tee.finder.qwertee;

import java.util.Arrays;
import java.util.List;

public class Tees {

    private List<Tee> value;

    public Tees() {
        // TODO: Remove this constructor
    }

    public Tees(Tee... value) {
        this.value = Arrays.asList(value);
    }

    public List<Tee> getValue() {
        return value;
    }

}
