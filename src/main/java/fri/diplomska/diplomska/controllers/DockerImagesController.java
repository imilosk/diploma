package fri.diplomska.diplomska.controllers;

import fri.diplomska.diplomska.models.DockerImage;
import fri.diplomska.diplomska.models.data.DockerImageDataModel;
import fri.diplomska.diplomska.services.DockerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;

@RestController
public class DockerImagesController {

    private final DockerService dockerService;

    public DockerImagesController(DockerService dockerService) {
        this.dockerService = dockerService;
    }

    @RequestMapping(value = "/app/images", method = RequestMethod.GET)
    public @ResponseBody Iterable<DockerImage> index() {
        return this.dockerService.getAllImages();
    }

    @RequestMapping(value = "/app/images", method = RequestMethod.POST)
    public ResponseEntity<String> index(@Valid DockerImageDataModel request) {
        try {
            String imageName = request.getImageName();
            String imageTag = request.getImageTag();
            String additionalArgs = request.getAdditionalArgs();
            MultipartFile file = request.getFile();

            dockerService.buildImage(file, imageName, imageTag, additionalArgs);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
