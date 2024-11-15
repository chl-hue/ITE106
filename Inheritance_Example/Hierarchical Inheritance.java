class A { 
  public void print A() { System.out.println("Class A"); }
}

class B extends A { 
  public void print () (System.out.println("Class B"); }
}

class C extends A {
  public void print C() (System.out.println("Class C"); }
}
  
class D extends A { 
  public void print D() { System.out.println("Class D"); }
}

public class Test {
  public static void main(String[] args)
  {
    B obj_B = new B();
    obj_B.print_A();
    obj_B.print_B();
    
    C obj_C = new C();
    obj_C.print_A();
    obj_C.print_C();
    
    D obj_D = new D();
    obj_D.print_A();
    obj_D.print_D();
  }
}
