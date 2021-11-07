package by.latushko.xmlapp.entity.type;

public enum DeviceGroup {
    INPUT("input"),
    OUTPUT("output"),
    MULTIMEDIA("multimedia"),
    OTHERS("others");

    private String value;

    DeviceGroup(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static DeviceGroup fromString(String text) {
        for (DeviceGroup b : DeviceGroup.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
