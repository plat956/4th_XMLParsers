package by.latushko.xmlapp.builder.tag;

public enum DeviceXmlTag {
    DEVICES("devices"),
    PORTABLE_DEVICE("portable-device"),
    EMBEDDED_DEVICE("embedded-device"),
    NAME("name"),
    ORIGIN("origin"),
    PURCHASE_DATE("purchase-date"),
    GROUP("group"),
    PRICE("price"),
    CRITICAL("critical"),
    RECHARGEABLE("rechargeable"),
    PROTECTIVE_CASE("protective-case"),
    TYPE("type");

    private String value;

    DeviceXmlTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
