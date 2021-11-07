package by.latushko.xmlapp.entity.type;

public enum EmbeddedDeviceType {
    RAM("RAM"),
    CPU("CPU"),
    MOTHERBOARD("Motherboard"),
    MEMORY_DRIVE("Memory drive");

    private String value;

    EmbeddedDeviceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static EmbeddedDeviceType fromString(String text) {
        for (EmbeddedDeviceType b : EmbeddedDeviceType.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
