package cf.spacetaste.backend.model;

import lombok.Data;

@Data
public class HashtagModel {
    final private int id;
    private String type;
    private String content;
}
