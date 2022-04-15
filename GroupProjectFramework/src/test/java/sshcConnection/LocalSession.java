package sshcConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LocalSession implements AppSession {

    @Override
    public String sendCommand(String str) {
        return null;
    }

    @Override
    public boolean isAppRunning(String appName) {
        return false;
    }

    @Override
    public boolean startJavaApp(String appName) {
        return false;
    }

    @Override
    public boolean stopJavaApp(String appName) {
        return false;
    }
}
