package cf.spacetaste.backend.service;

import cf.spacetaste.backend.mapper.HashtagMapper;
import cf.spacetaste.backend.model.HashtagModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HashtagService {

    private final HashtagMapper hashtagMapper;

    public List<HashtagModel> listRandomValidHashtags() {
        List<HashtagModel> tags = listAllValidHashtags();
        Collections.shuffle(tags);
        return tags;
    }

    public List<HashtagModel> listAllValidHashtags() {
        return hashtagMapper.listAllValidHashtags();
    }
}
