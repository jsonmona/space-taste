package cf.spacetaste.backend.controller;

import cf.spacetaste.backend.model.HashtagModel;
import cf.spacetaste.backend.service.HashtagService;
import cf.spacetaste.backend.service.MatzipService;
import cf.spacetaste.backend.service.SearchService;
import cf.spacetaste.backend.service.TokenService;
import cf.spacetaste.common.MatzipInfoDTO;
import cf.spacetaste.common.SearchRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchController {

    private final TokenService tokenService;
    private final SearchService searchService;
    private final MatzipService matzipService;
    private final HashtagService hashtagService;

    @PostMapping("/search/v1")
    public List<MatzipInfoDTO> search(@RequestHeader("authorization") String token, @RequestBody SearchRequestDTO req) {
        tokenService.verifyToken(token);

        return searchService.search(req)
                .stream()
                .map(matzipService::modelToInfo)
                .toList();
    }

    @GetMapping("/search/reviewuser")
    public List<MatzipInfoDTO> searchByReviewedUser(@RequestHeader("Authorization") String auth) {
        int userId = tokenService.verifyToken(auth);

        return matzipService.listByReviewedUser(userId);
    }

    @GetMapping("/search/randomtags")
    public List<String> getRandomTags(@RequestHeader("Authorization") String auth) {
        tokenService.verifyToken(auth);

        return hashtagService.listRandomValidHashtags()
                .stream()
                .map(HashtagModel::getContent)
                .toList();
    }
}
