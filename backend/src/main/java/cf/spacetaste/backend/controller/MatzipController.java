package cf.spacetaste.backend.controller;

import cf.spacetaste.backend.model.MatzipModel;
import cf.spacetaste.backend.service.MatzipService;
import cf.spacetaste.backend.service.PhotoService;
import cf.spacetaste.backend.service.ReviewService;
import cf.spacetaste.backend.service.TokenService;
import cf.spacetaste.common.MatzipBasicInfoDTO;
import cf.spacetaste.common.MatzipInfoDTO;
import cf.spacetaste.common.ReviewInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MatzipController {

    private final MatzipService matzipService;
    private final TokenService tokenService;
    private final ReviewService reviewService;

    @PostMapping("/matzip")
    public MatzipInfoDTO createMatzip(@RequestHeader("authorization") String auth, @RequestBody MatzipBasicInfoDTO info) {
        int userId = tokenService.verifyToken(auth);
        var matzip = matzipService.createMatzip(info);
        if (matzip == null || matzip.getMatzipId() <= 0)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        return matzipService.modelToInfo(matzip);
    }

    @GetMapping("/matzip/{id}")
    public MatzipInfoDTO queryMatzip(@PathVariable int id) {
        var matzip = matzipService.queryFromId(id);
        if (matzip == null || matzip.getMatzipId() <= 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return matzipService.modelToInfo(matzip);
    }

    @GetMapping("/matzip/{id}/photo")
    public List<String> queryMatzipPhotos(@PathVariable int id) {
        var matzip = matzipService.queryFromId(id);
        if (matzip == null || matzip.getMatzipId() <= 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return matzipService.listPhotoUrl(matzip);
    }

    @PostMapping("/matzip/{id}/review")
    public ResponseEntity<String> createReview(@RequestHeader("Authorization") String auth, @PathVariable int id, @RequestBody ReviewInfoDTO body) {
        int userId = tokenService.verifyToken(auth);

        if (body.getMatzipId() != id || body.getReviewId() != 0)
            return ResponseEntity.badRequest().build();

        int reviewId = reviewService.createReview(userId, id, body);
        if (reviewId == 0)
            return ResponseEntity.badRequest().build();
        if (reviewId < 0)
            return ResponseEntity.internalServerError().build();

        return ResponseEntity.created(URI.create("/matzip/"+id+"/review/"+reviewId)).build();
    }
}
