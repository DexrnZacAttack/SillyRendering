package danilwhale.sillyrendering.config;

public enum ConfigCategory {
    MAIN("main");

    public final String name;

    ConfigCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
