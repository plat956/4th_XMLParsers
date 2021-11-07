package by.latushko.xmlapp.entity;

import by.latushko.xmlapp.entity.type.DeviceGroup;
import by.latushko.xmlapp.entity.type.DeviceOrigin;

import java.math.BigDecimal;
import java.time.YearMonth;

public abstract class Device {
    public static final String DEFAULT_VENDOR_SITE = "https://device-support.com";

    private String deviceId;
    private String vendorSite;
    private String name;
    private DeviceOrigin origin;
    private YearMonth purchaseDate;
    private DeviceGroup group;
    private BigDecimal price;
    private Boolean critical;

    public Device() {
    }

    public Device(String deviceId, String vendorSite, String name, DeviceOrigin origin, YearMonth purchaseDate, DeviceGroup group, BigDecimal price, Boolean critical) {
        this.deviceId = deviceId;
        this.vendorSite = vendorSite;
        this.name = name;
        this.origin = origin;
        this.purchaseDate = purchaseDate;
        this.group = group;
        this.price = price;
        this.critical = critical;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getVendorSite() {
        return vendorSite;
    }

    public void setVendorSite(String vendorSite) {
        this.vendorSite = vendorSite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(DeviceOrigin origin) {
        this.origin = origin;
    }

    public YearMonth getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(YearMonth purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public DeviceGroup getGroup() {
        return group;
    }

    public void setGroup(DeviceGroup group) {
        this.group = group;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getCritical() {
        return critical;
    }

    public void setCritical(Boolean critical) {
        this.critical = critical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        if (deviceId != null ? !deviceId.equals(device.deviceId) : device.deviceId != null) return false;
        if (vendorSite != null ? !vendorSite.equals(device.vendorSite) : device.vendorSite != null) return false;
        if (name != null ? !name.equals(device.name) : device.name != null) return false;
        if (origin != device.origin) return false;
        if (purchaseDate != null ? !purchaseDate.equals(device.purchaseDate) : device.purchaseDate != null)
            return false;
        if (group != device.group) return false;
        if (price != null ? !price.equals(device.price) : device.price != null) return false;
        return critical != null ? critical.equals(device.critical) : device.critical == null;
    }

    @Override
    public int hashCode() {
        int result = deviceId != null ? deviceId.hashCode() : 0;
        result = 31 * result + (vendorSite != null ? vendorSite.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        result = 31 * result + (purchaseDate != null ? purchaseDate.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (critical != null ? critical.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Device{");
        sb.append("deviceId='").append(deviceId).append('\'');
        sb.append(", vendorSite='").append(vendorSite).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", origin=").append(origin);
        sb.append(", purchaseDate=").append(purchaseDate);
        sb.append(", group=").append(group);
        sb.append(", price=").append(price);
        sb.append(", critical=").append(critical);
        sb.append('}');
        return sb.toString();
    }
}
