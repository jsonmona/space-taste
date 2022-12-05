package cf.spacetaste.backend.service;

import cf.spacetaste.backend.mapper.HashtagMapper;
import cf.spacetaste.backend.model.HashtagModel;
import cf.spacetaste.backend.model.PhotoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HashtagService {

    private final HashtagMapper hashtagMapper;
    private final PhotoService photoService;

    public List<HashtagModel> listRandomValidHashtags() {
        List<HashtagModel> tags = listAllValidHashtags();
        Collections.shuffle(tags);
        return tags;
    }

    public List<HashtagModel> listAllValidHashtags() {
        return hashtagMapper.listAllValidHashtags();
    }

    public List<HashtagModel> getFromContent(String content) {
        return hashtagMapper.getFromContent(content);
    }

    public PhotoModel randomPhoto(HashtagModel hashtag) {
        List<Integer> photos = hashtagMapper.getMainPhotoOfTag(hashtag);
        if (photos.isEmpty())
            return null;

        Collections.shuffle(photos);
        for (int photoId : photos) {
            PhotoModel ret = photoService.getFromId(photoId);
            if (ret != null)
                return ret;
        }
        return null;
    }
}
