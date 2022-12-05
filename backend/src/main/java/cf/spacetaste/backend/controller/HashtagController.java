package cf.spacetaste.backend.controller;

import cf.spacetaste.backend.model.HashtagModel;
import cf.spacetaste.backend.service.HashtagService;
import cf.spacetaste.backend.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class HashtagController {

    private final HashtagService hashtagService;
    private final PhotoService photoService;

    @GetMapping("/hashtag/photo")
    public String getHashtagPhotoUrl(@RequestParam String tag) {
        List<HashtagModel> hashtags = hashtagService.getFromContent(tag);
        if (hashtags.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return photoService.makeUrl(hashtagService.randomPhoto(hashtags.get(0)));
    }
}
