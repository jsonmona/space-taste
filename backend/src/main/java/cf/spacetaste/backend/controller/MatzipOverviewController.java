package cf.spacetaste.backend.controller;

import cf.spacetaste.backend.service.MatzipService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatzipOverviewController {

    private MatzipService matzipService;

    public MatzipOverviewController(MatzipService matzipService) {
        this.matzipService = matzipService;
    }
}
