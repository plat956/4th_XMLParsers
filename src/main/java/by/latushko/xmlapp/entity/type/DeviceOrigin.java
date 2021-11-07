package by.latushko.xmlapp.entity.type;

public enum DeviceOrigin {
    BELARUS("Belarus"),
    UKRAINE("Ukraine"),
    JAPAN("Japan"),
    CHINA("China"),
    USA("USA");

    private String value;

    DeviceOrigin(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static DeviceOrigin fromString(String text) {
        for (DeviceOrigin b : DeviceOrigin.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
