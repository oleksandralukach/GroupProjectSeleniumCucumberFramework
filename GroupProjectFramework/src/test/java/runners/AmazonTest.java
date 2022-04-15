package runners;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


//for practice purposes
public class AmazonTest {

    @Test
    public void test1(){
        System.out.println("1");
    } @Test
    public void test2(){
        System.out.println("2");
    }@Test
    public void test3(){
        Assert.fail();
    }
}
