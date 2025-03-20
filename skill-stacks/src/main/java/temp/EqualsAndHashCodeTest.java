package temp;

import java.util.HashSet;
import java.util.Set;

class DemoClass {
    int value;

    public DemoClass(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        return value == ((DemoClass) obj).value;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

public class EqualsAndHashCodeTest {
    public static void main(String[] args) {
        DemoClass obj = new DemoClass(5);
        DemoClass obj2 = new DemoClass(5);
        System.out.println(obj.equals(obj2));

        Set<DemoClass> set = new HashSet<DemoClass>();
        set.add(obj);
        System.out.println(set.contains(obj2));
    }
}