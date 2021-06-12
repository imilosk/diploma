package fri.diplomska.diplomska.repository;

import fri.diplomska.diplomska.requestModels.DockerImage;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<DockerImage, Integer> {

}

