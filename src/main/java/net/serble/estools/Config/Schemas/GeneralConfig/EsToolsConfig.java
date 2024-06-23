package net.serble.estools.Config.Schemas.GeneralConfig;

@SuppressWarnings("unused")  // SnakeYAML needs the setters
public class EsToolsConfig {
    private boolean safeTp = false;
    private boolean metrics = true;
    private UpdaterConfig updater = new UpdaterConfig();

    public boolean isSafeTp() {
        return safeTp;
    }

    public void setSafeTp(boolean safeTp) {
        this.safeTp = safeTp;
    }

    public boolean isMetrics() {
        return metrics;
    }

    public void setMetrics(boolean metrics) {
        this.metrics = metrics;
    }

    public UpdaterConfig getUpdater() {
        return updater;
    }

    public void setUpdater(UpdaterConfig updater) {
        this.updater = updater;
    }
}
