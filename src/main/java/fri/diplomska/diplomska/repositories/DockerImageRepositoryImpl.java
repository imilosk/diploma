package fri.diplomska.diplomska.repositories;

import fri.diplomska.diplomska.models.DockerImage;
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
     * @param imageName The image name
     * @param imageTag The image tag
     * @param imageId The image id
     */
    public void upsert(String imageName, String imageTag, long imageSize, String imageId) {
        DockerImage existingDockerImage = this.dockerImageRepository.findByImageId(imageId);

        if (existingDockerImage == null) {
            DockerImage dockerImage = new DockerImage();
            dockerImage.setName(imageName);
            dockerImage.setSize(imageSize);
            dockerImage.setImageId(imageId);
            dockerImage.setTag(imageTag);
            this.dockerImageRepository.save(dockerImage);
        } else {
            existingDockerImage.setTag(imageTag);
            existingDockerImage.setName(imageName);
            this.dockerImageRepository.save(existingDockerImage);
        }
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
