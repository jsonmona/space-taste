package cf.spacetaste.backend.controller;

import cf.spacetaste.backend.service.MatzipService;
import cf.spacetaste.backend.service.SearchService;
import cf.spacetaste.common.MatzipInfoDTO;
import cf.spacetaste.common.SearchRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchController {

    private final SearchService searchService;

    private final MatzipService matzipService;

    @PostMapping("/search/v1")
    public List<MatzipInfoDTO> search(@RequestBody SearchRequestDTO req) {
        return searchService.search(req)
                .stream()
                .map(matzipService::modelToInfo)
                .toList();
    }
}
