package apps.ucu.facadeservice;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
public class FacadeController {

    private final FacadeService fc;

    public FacadeController(@RequestBody @NotNull FacadeService facadeService) {
        this.fc = facadeService;
    }

    @GetMapping("/facade-service")
    public String getFacade(@NotNull RestTemplate restTemplate) {
        return fc.getFacade(restTemplate);
    }

    @PostMapping("/facade-service")
    public List<ResponseEntity> postFacade(@NotNull RestTemplate restTemplate, @RequestBody String txt) throws InterruptedException {
        return fc.postFacade(restTemplate, txt);
    }

}
