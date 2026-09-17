package az.shopery.filenet_ms.repository;

import az.shopery.filenet_ms.model.entity.File;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {
}
