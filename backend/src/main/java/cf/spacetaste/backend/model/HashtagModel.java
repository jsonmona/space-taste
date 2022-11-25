package cf.spacetaste.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class HashtagModel {
    final private int hashtagId;
    private String type;
    private String content;
}
