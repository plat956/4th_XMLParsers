package by.latushko.xmlapp.entity;

import by.latushko.xmlapp.entity.type.DeviceGroup;
import by.latushko.xmlapp.entity.type.DeviceOrigin;
import by.latushko.xmlapp.entity.type.EmbeddedDeviceType;

import java.math.BigDecimal;
import java.time.YearMonth;

public class EmbeddedDevice extends Device{
    private EmbeddedDeviceType type;

    public EmbeddedDevice() {
    }

    public EmbeddedDevice(String deviceId, String vendorSite, String name, DeviceOrigin origin, YearMonth purchaseDate,
                          DeviceGroup group, BigDecimal price, Boolean critical, EmbeddedDeviceType type) {
        super(deviceId, vendorSite, name, origin, purchaseDate, group, price, critical);
        this.type = type;
    }

    public EmbeddedDeviceType getType() {
        return type;
    }

    public void setType(EmbeddedDeviceType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EmbeddedDevice that = (EmbeddedDevice) o;

        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmbeddedDevice{");
        sb.append("type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
