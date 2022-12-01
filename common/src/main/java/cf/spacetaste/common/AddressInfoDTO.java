package cf.spacetaste.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressInfoDTO {

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
        AddressInfoDTO x = (AddressInfoDTO) obj;
        if (x == null)
            return false;
        if (addressCode == null)
            return x.addressCode == null;
        return addressCode.equals(x.addressCode);
    }
}
