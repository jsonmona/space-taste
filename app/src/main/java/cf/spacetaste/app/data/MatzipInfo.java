package cf.spacetaste.app.data;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class MatzipInfo {
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
