package exomaster.grayfw.logging;

import java.io.File;
import java.io.PrintStream;

import static exomaster.grayfw.Util.consoleLog;

public class Logging {
    private PrintStream logStream, logErrStream, consoleStream, errStream; //These may not need to be stored

    //TODO: Add startup and shutdown events (do this using a base class and extend to other parts of the framework as well)

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void run() {
        File file = new File("logs/"); //Should this go in SplitOutputStream in case dir was deleted
        if (!file.exists())
            file.mkdir();
        System.setOut(logStream = new PrintStream(new SplitOutputStream(consoleStream = System.out)));
        System.setErr(logErrStream = new PrintStream(new SplitOutputStream(errStream = System.err)));

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                consoleLog("Closing log.\n");
            }
        });
    }
}