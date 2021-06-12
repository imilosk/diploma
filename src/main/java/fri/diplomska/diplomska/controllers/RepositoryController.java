package fri.diplomska.diplomska.controllers;

import fri.diplomska.diplomska.repository.ImageRepository;
import fri.diplomska.diplomska.requestModels.DockerImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RepositoryController {

    @Autowired
    private ImageRepository imageRepository;

    @ExceptionHandler(Exception.class)
    @RequestMapping(value = "/app/listImages", method = RequestMethod.GET)
    public @ResponseBody Iterable<DockerImage> index() {
        return imageRepository.findAll();
    }
}
