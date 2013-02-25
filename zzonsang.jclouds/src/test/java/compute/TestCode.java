package compute;

import org.junit.Test;

public class TestCode  {

    @Test
    public void test() {
        Boolean[] rackNumber = new Boolean[42];
        for ( int i = 0; i < 42; i++ ) {
            rackNumber[i] = false;
        }
        
        for ( Boolean flag : rackNumber ) {
            System.out.println(flag);
        }
        
        if ( rackNumber[0] ) {
            System.out.println("test");
        }
    }
}
