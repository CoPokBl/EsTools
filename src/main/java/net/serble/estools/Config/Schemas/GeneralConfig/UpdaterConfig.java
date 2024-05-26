package net.serble.estools.Config.Schemas.GeneralConfig;

@SuppressWarnings("unused")  // SnakeYAML needs the setters
public class UpdaterConfig {
    private String githubReleasesUrl = "https://api.github.com/repos/CoPokBl/EsTools/releases/latest";
    private boolean autoUpdate = false;
    private boolean warnOnOutdated = false;
    private boolean logOnOutdated = true;

    public boolean isAutoUpdate() {
        return autoUpdate;
    }

    public boolean isLogOnOutdated() {
        return logOnOutdated;
    }

    public boolean isWarnOnOutdated() {
        return warnOnOutdated;
    }

    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }

    public String getGithubReleasesUrl() {
        return githubReleasesUrl;
    }

    public void setGithubReleasesUrl(String githubReleasesUrl) {
        this.githubReleasesUrl = githubReleasesUrl;
    }

    public void setLogOnOutdated(boolean logOnOutdated) {
        this.logOnOutdated = logOnOutdated;
    }

    public void setWarnOnOutdated(boolean warnOnOutdated) {
        this.warnOnOutdated = warnOnOutdated;
    }
}
