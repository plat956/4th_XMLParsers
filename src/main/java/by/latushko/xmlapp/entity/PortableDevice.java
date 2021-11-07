package by.latushko.xmlapp.entity;

import by.latushko.xmlapp.entity.type.DeviceGroup;
import by.latushko.xmlapp.entity.type.DeviceOrigin;

import java.math.BigDecimal;
import java.time.YearMonth;

public class PortableDevice extends Device{
    private Boolean rechargeable;
    private Boolean protectiveCase;

    public PortableDevice() {
    }

    public PortableDevice(String deviceId, String vendorSite, String name, DeviceOrigin origin, YearMonth purchaseDate,
                          DeviceGroup group, BigDecimal price, Boolean critical, Boolean rechargeable, Boolean protectiveCase) {
        super(deviceId, vendorSite, name, origin, purchaseDate, group, price, critical);
        this.rechargeable = rechargeable;
        this.protectiveCase = protectiveCase;
    }

    public Boolean getRechargeable() {
        return rechargeable;
    }

    public void setRechargeable(Boolean rechargeable) {
        this.rechargeable = rechargeable;
    }

    public Boolean getProtectiveCase() {
        return protectiveCase;
    }

    public void setProtectiveCase(Boolean protectiveCase) {
        this.protectiveCase = protectiveCase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PortableDevice that = (PortableDevice) o;

        if (rechargeable != null ? !rechargeable.equals(that.rechargeable) : that.rechargeable != null) return false;
        return protectiveCase != null ? protectiveCase.equals(that.protectiveCase) : that.protectiveCase == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (rechargeable != null ? rechargeable.hashCode() : 0);
        result = 31 * result + (protectiveCase != null ? protectiveCase.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PortableDevice{");
        sb.append("rechargeable=").append(rechargeable);
        sb.append(", protectiveCase=").append(protectiveCase);
        sb.append('}');
        return sb.toString();
    }
}
