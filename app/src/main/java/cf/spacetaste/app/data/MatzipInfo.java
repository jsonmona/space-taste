package cf.spacetaste.app.data;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class MatzipInfo implements Serializable {
    private final int matzipId;

    @NonNull
    private final String name;

    @NonNull
    private final String baseAddress;

    @NonNull
    private final String detailAddress;

    @NonNull
    private final List<String> hashtags;

    private final String photoUrl;
}
