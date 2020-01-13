package org.modmapping.mmms.repository.repositories.mapping.mappable;

import java.util.UUID;

import org.modmapping.mmms.repository.model.mapping.mappable.ProtectedMappableInformationDMO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

/**
 * Represents a repository that can provide and store {@link ProtectedMappableInformationDMO} objects.
 */
public interface IProtectedMappableInformationRepository extends R2dbcRepository<ProtectedMappableInformationDMO, UUID> {

    /**
     * Finds all the protected versioned mappable information which indicate that a given versioned mappable is locked
     * for mapping types.
     *
     * The returned order cannot be guaranteed.
     *
     * @param versionedMappableId The id of the versioned mappable for which protected mappable information is being looked up.
     * @param pageable The pagination information for the query.
     * @return Protected mappable information that indicates that the versioned mappable is locked for a given mapping type.
     */
    @Query("SELECT * FROM protected_mappable pm WHERE pm.versionedMappableId = $1")
    Flux<ProtectedMappableInformationDMO> findAllForVersionedMappable(UUID versionedMappableId, final Pageable pageable);

    /**
     * Finds all the protected versioned mappable information which indicate that a given mapping type is locked
     * for versioned mappables.
     *
     * The returned order cannot be guaranteed.
     *
     * @param mappingTypeId The id of the mapping type for which protected mappable information is being looked up.
     * @param pageable The pagination information for the query.
     * @return Protected mappable information that indicates that the mapping type is locked for a given versioned mappable.
     */
    @Query("SELECT * FROM protected_mappable pm WHERE pm.mappingTypeId = $1")
    Flux<ProtectedMappableInformationDMO> findAllForMappingType(UUID mappingTypeId, final Pageable pageable);
}
