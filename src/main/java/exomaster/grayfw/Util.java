package exomaster.grayfw;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static void consoleLog(Object... params) {
        StringBuilder sb = new StringBuilder();
        for (Object o : params)
            sb.append(o).append(' ');
        System.out.println('(' + new SimpleDateFormat("MM/dd/yy HH:mm:ss z").format(new Date()) + ") GRAY: " + sb.toString().trim());
    }

}
