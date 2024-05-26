package net.serble.estools.Config.Schemas.Give;

@SuppressWarnings("unused")  // SnakeYAML needs the setters
public class GiveSettings {
    private boolean addWithoutUnderscores = true;
    private boolean removeWithUnderscores = false;

    public boolean isAddWithoutUnderscores() {
        return addWithoutUnderscores;
    }

    public boolean isRemoveWithUnderscores() {
        return removeWithUnderscores;
    }

    public void setAddWithoutUnderscores(boolean addWithoutUnderscores) {
        this.addWithoutUnderscores = addWithoutUnderscores;
    }

    public void setRemoveWithUnderscores(boolean removeWithUnderscores) {
        this.removeWithUnderscores = removeWithUnderscores;
    }
}
