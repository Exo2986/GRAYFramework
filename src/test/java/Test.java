import exomaster.grayfw.config.Config;
import exomaster.grayfw.config.ConfigEntry;
import exomaster.grayfw.logging.Logging;

import static exomaster.grayfw.Util.consoleLog;

public class Test {
    private Logging logging;
    private Config config;

    public static void main(String[] args) throws Exception {
        new Test();
    }

    public Test() {
        logging = new Logging();
        logging.run();
        config = new Config("Config/Test.cfg");

        //Add config entries
        config.addConfigEntry(new ConfigEntry<>("TEST_1", "string"));
        config.addConfigEntry(new ConfigEntry<>("TEST_2", "10"));

        config.init();

        if (this.config.getHasUpdatedConfigFile()) {
            consoleLog("Config.txt has been created or updated with missing values. Please input the proper values of each new entry before running this bot again.");
            System.exit(1);
        }

        consoleLog(config.getEntryAsString("TEST_1"));
        consoleLog(((int)config.getEntry("TEST_2", Integer.class)) + 2);

        //Example code for loading modules using org.reflections (https://github.com/ronmamo/reflections)
        /*modules = new ArrayList<>();
        Reflections ref = new Reflections();
        Set<Class<? extends Module>> classSet = ref.getSubTypesOf(Module.class);
        for (Class<? extends Module> c : classSet) {
            try {
                Module md = (Module) Module.class.getClassLoader().loadClass(c.getCanonicalName()).newInstance();
                this.modules.add(md);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }*/
    }
}
