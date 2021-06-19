package fri.diplomska.diplomska.repositories;

import fri.diplomska.diplomska.models.DockerImage;
import org.springframework.data.repository.CrudRepository;

public interface DockerImageRepository extends CrudRepository<DockerImage, Integer> {
    DockerImage findByImageId(String imageId);
}

