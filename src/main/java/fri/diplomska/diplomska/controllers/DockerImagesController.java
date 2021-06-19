package fri.diplomska.diplomska.controllers;

import fri.diplomska.diplomska.helpers.FileHelpers;
import fri.diplomska.diplomska.models.DockerImage;
import fri.diplomska.diplomska.models.data.DockerImageDataModel;
import fri.diplomska.diplomska.services.DockerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;

@RestController
public class DockerImagesController {

    private final DockerService dockerService;

    public DockerImagesController(DockerService dockerService) {
        this.dockerService = dockerService;
    }

    @ExceptionHandler(Exception.class)
    @RequestMapping(value = "/app/images", method = RequestMethod.GET)
    public @ResponseBody Iterable<DockerImage> index() {
        return dockerService.getAllImages();
    }

    @ExceptionHandler(Exception.class)
    @RequestMapping(value = "/app/images", method = RequestMethod.POST)
    public ResponseEntity<String> index(@Valid DockerImageDataModel request) {
        try {
            String imageName = request.getImageName();
            String imageTag = request.getImageTag();
            String additionalArgs = request.getAdditionalArgs();
            MultipartFile file = request.getFile();

            String dockerfileDirectory = FileHelpers.createTempDirectory();
            FileHelpers.unzipFile(file, dockerfileDirectory);

            dockerService.buildImage(dockerfileDirectory, imageName, imageTag, additionalArgs);

            FileHelpers.deleteDirectory(dockerfileDirectory);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
