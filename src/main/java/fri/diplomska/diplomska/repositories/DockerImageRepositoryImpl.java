package fri.diplomska.diplomska.repositories;

import fri.diplomska.diplomska.models.DockerImage;
import fri.diplomska.diplomska.models.data.DockerImageDataModel;
import org.springframework.stereotype.Component;

@Component
public class DockerImageRepositoryImpl {

    private final DockerImageRepository dockerImageRepository;

    public DockerImageRepositoryImpl(DockerImageRepository dockerImageRepository) {
        this.dockerImageRepository = dockerImageRepository;
    }

    /**
     * Upsert docker image in DB
     *
     * @param dockerImageDataModel The Docker image data model
     */
    public void upsert(DockerImageDataModel dockerImageDataModel) {
        DockerImage dockerImage = this.dockerImageRepository.findByImageId(dockerImageDataModel.getImageId());

        if (dockerImage == null) {
            dockerImage = new DockerImage();
        }

        dockerImage.setName(dockerImageDataModel.getImageName());
        dockerImage.setSize(dockerImageDataModel.getImageSize());
        dockerImage.setImageId(dockerImageDataModel.getImageId());
        dockerImage.setTag(dockerImageDataModel.getImageTag());
        dockerImage.setOs(dockerImageDataModel.getOs());
        dockerImage.setArchitecture(dockerImageDataModel.getArchitecture());
        dockerImage.setExposedPort(dockerImageDataModel.getExposedPort());
        this.dockerImageRepository.save(dockerImage);
    }

    /**
     * Returns a list of all Docker images in the DB
     *
     * @return List of docker images
     */
    public Iterable<DockerImage> getAll() {
        return this.dockerImageRepository.findAll();
    }

    /**
     * Get a Docker image by image id
     *
     * @param imageId The Docker image id
     * @return Returns a DockerImage object
     */
    public DockerImage get(String imageId) {
        return this.dockerImageRepository.findByImageId(imageId);
    }

    /**
     * Deletes an image with the given image id
     *
     * @param imageId The Docker image to delete
     */
    public void deleteByImageId(String imageId) {
        DockerImage dockerImage = this.get(imageId);
        this.dockerImageRepository.delete(dockerImage);
    }

}
