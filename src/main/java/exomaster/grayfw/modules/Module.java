package exomaster.grayfw.modules;

public abstract class Module {
    protected String id;
    protected boolean running = false;

    public Module() {
        this.run();
    }

    public void stop() {
        running = false;
    }

    public void run() {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
