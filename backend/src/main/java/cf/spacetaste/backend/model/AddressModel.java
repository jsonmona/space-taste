package cf.spacetaste.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressModel {

    private String addressCode;
    private String siDo;
    private String siGunGu;
    private String eupMyeonDong;
    private String dongRi;

    @Override
    public String toString() {
        return "[" + addressCode + "] " + siDo + " " + siGunGu + " " + eupMyeonDong + " " + dongRi;
    }

    @Override
    public int hashCode() {
        return addressCode.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        AddressModel x = (AddressModel) obj;
        if (x == null)
            return false;
        if (addressCode == null)
            return x.addressCode == null;
        return addressCode.equals(x.addressCode);
    }
}
