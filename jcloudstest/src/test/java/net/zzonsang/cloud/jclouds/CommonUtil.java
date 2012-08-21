package net.zzonsang.cloud.jclouds;

public class CommonUtil {

    public static void completeMsg() {
        System.out.println("### Complete ###\n");
    }
    
    public static void beforeMsg() {
        StackTraceElement stackTraceElements[] =
                (new Throwable()).getStackTrace();
        System.out.println("### " + stackTraceElements[1].toString() + " ###");
    }
}
