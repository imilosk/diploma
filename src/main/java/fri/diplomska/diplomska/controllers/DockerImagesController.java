package fri.diplomska.diplomska.controllers;

import fri.diplomska.diplomska.models.DockerImage;
import fri.diplomska.diplomska.models.data.DockerImageDataModel;
import fri.diplomska.diplomska.models.request.DockerImageRequestDataModel;
import fri.diplomska.diplomska.services.DockerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class DockerImagesController {

    private final DockerService dockerService;

    public DockerImagesController(DockerService dockerService) {
        this.dockerService = dockerService;
    }

    @RequestMapping(value = "/app/images", method = RequestMethod.GET)
    public @ResponseBody Iterable<DockerImage> getAll() {
        return this.dockerService.getAllImages();
    }

    @RequestMapping(value = "/app/images", method = RequestMethod.POST)
    public ResponseEntity<String> add(@Valid DockerImageRequestDataModel request) {
        try {
            DockerImageDataModel dockerImageDataModel = new DockerImageDataModel(request);
            dockerService.buildImage(dockerImageDataModel);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @RequestMapping(value = "/app/images/{imageId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable @NotNull String imageId) {
        try {
            dockerService.deleteImage(imageId);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
