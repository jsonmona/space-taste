package cf.spacetaste.backend.service;

import cf.spacetaste.backend.mapper.HashtagMapper;
import cf.spacetaste.backend.mapper.MatzipMapper;
import cf.spacetaste.backend.model.HashtagModel;
import cf.spacetaste.backend.model.MatzipModel;
import cf.spacetaste.common.SearchRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final HashtagMapper hashtagMapper;
    private final MatzipMapper matzipMapper;

    private List<Integer> findExactHashtags(List<String> hashtags) {
        return hashtags
                .stream()
                .flatMap((tag) -> hashtagMapper.getFromContent(tag).stream())
                .map(HashtagModel::getHashtagId)
                .toList();
    }

    private List<Integer> findAnyHashtags(String term) {
        return Arrays.stream(term.split("[ %_]"))
                .flatMap((token) -> hashtagMapper.searchContent(token).stream())
                .map(HashtagModel::getHashtagId)
                .toList();
    }

    public List<MatzipModel> search(SearchRequestDTO req) {
        List<Integer> requiredTags = List.of();
        List<Integer> optionalTags = List.of();
        String term = "";

        if (req.getTags() != null && req.getTags().size() > 0)
            requiredTags = findExactHashtags(req.getTags());

        if (req.getTerm() != null && !req.getTerm().isBlank()) {
            optionalTags = findAnyHashtags(req.getTerm());
            term = "%" + req.getTerm().replace('%', '_').trim() + "%";
        }

        if (requiredTags.isEmpty() && optionalTags.isEmpty() && term.isEmpty())
            return List.of();

        return matzipMapper.search(requiredTags, optionalTags, term);
    }
}
