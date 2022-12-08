package cf.spacetaste.backend.service;

import cf.spacetaste.backend.mapper.MatzipMapper;
import cf.spacetaste.backend.mapper.ReviewMapper;
import cf.spacetaste.backend.model.AverageStarModel;
import cf.spacetaste.backend.model.MatzipModel;
import cf.spacetaste.backend.model.ReviewModel;
import cf.spacetaste.backend.model.ReviewWithUserInfoModel;
import cf.spacetaste.common.CreateReviewRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final MatzipMapper matzipMapper;

    public int createReview(int userId, int matzipId, CreateReviewRequestDTO info) {
        MatzipModel matzip = matzipMapper.getFromId(matzipId);
        if (matzip == null)
            return 0;

        if (info.getScoreTaste() < 1 || 5 < info.getScoreTaste() ||
                info.getScorePrice() < 1 || 5 < info.getScorePrice() ||
                info.getScoreKindness() < 1 || 5 < info.getScoreKindness() ||
                info.getScoreClean() < 1 || 5 < info.getScoreClean())
            return 0;

        ReviewModel review = new ReviewModel(
                0,
                matzipId,
                userId,
                (byte) info.getScoreTaste(),
                (byte) info.getScorePrice(),
                (byte) info.getScoreKindness(),
                (byte) info.getScoreClean(),
                info.getDetail()
        );

        int rows = reviewMapper.create(review);
        if (rows < 1)
            return -1;

        AverageStarModel stars = reviewMapper.getAverageStar(matzip);
        matzipMapper.updateStar(matzip, stars);

        return review.getReviewId();
    }

    public List<ReviewWithUserInfoModel> listFromMatzip(int matzipId) {
        return reviewMapper.listFromMatzipWithNickname(matzipId);
    }
}
