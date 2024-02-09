package fri.diplomska.diplomska.repositories;

import fri.diplomska.diplomska.models.Deployment;
import org.springframework.data.repository.CrudRepository;

public interface DeploymentRepository extends CrudRepository<Deployment, Integer> {
    Deployment findByName(String name);
    void delete(Deployment deployment);
}
