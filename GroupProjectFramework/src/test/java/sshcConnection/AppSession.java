package sshcConnection;

public interface AppSession {

    //methods with no body
    String sendCommand(String str);

    boolean isAppRunning(String appName);

    boolean startJavaApp(String appName);

    boolean stopJavaApp(String appName);
}
