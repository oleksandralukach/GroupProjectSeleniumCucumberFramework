package sshcConnection;

public class Test {
    public static void main(String[] args) {
        LocalSession localSession = new LocalSession();
        System.out.println(localSession.sendCommand("java -version"));

    }
}
