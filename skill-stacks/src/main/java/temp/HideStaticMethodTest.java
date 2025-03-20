package temp;

class Parent {
    static void staticMethod() {
        System.out.println("temp.Parent's static method");
    }

    void instanceMethod() {
        System.out.println("temp.Parent's instance method");
    }
}

class Child extends Parent {
    // 隐藏父类的静态方法
    static void staticMethod() {
        System.out.println("temp.Child's static method");
    }

    // 覆盖父类的实例方法
    @Override
    void instanceMethod() {
        System.out.println("temp.Child's instance method");
    }
}

public class HideStaticMethodTest {
    public static void main(String[] args) {
        Parent parent = new Parent();
        Parent childAsParent = new Child(); // 父类引用指向子类对象
        Child child = new Child();

        // 静态方法调用（看引用类型）
        Parent.staticMethod();       // 输出: temp.Parent's static method
        Child.staticMethod();        // 输出: temp.Child's static method
        childAsParent.staticMethod(); // 输出: temp.Parent's static method（关键！）

        // 实例方法调用（看实际对象类型）
        parent.instanceMethod();      // 输出: temp.Parent's instance method
        childAsParent.instanceMethod(); // 输出: temp.Child's instance method（动态绑定）
        child.instanceMethod();       // 输出: temp.Child's instance method
    }
}
