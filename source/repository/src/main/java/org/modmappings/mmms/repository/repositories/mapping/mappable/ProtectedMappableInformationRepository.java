package org.modmappings.mmms.repository.repositories.mapping.mappable;

import org.modmappings.mmms.er2dbc.data.access.strategy.ExtendedDataAccessStrategy;
import org.modmappings.mmms.repository.model.mapping.mappable.ProtectedMappableInformationDMO;
import org.modmappings.mmms.repository.repositories.ModMappingR2DBCRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.relational.repository.query.RelationalEntityInformation;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Represents a repository that can provide and store {@link ProtectedMappableInformationDMO} objects.
 */
@Repository
public class ProtectedMappableInformationRepository extends ModMappingR2DBCRepository<ProtectedMappableInformationDMO> {

    public ProtectedMappableInformationRepository(RelationalEntityInformation<ProtectedMappableInformationDMO, UUID> entity, DatabaseClient databaseClient, R2dbcConverter converter, ExtendedDataAccessStrategy accessStrategy) {
        super(entity, databaseClient, converter, accessStrategy);
    }

    /**
     * Finds all the protected versioned mappable information which indicate that a given versioned mappable is locked
     * for mapping types.
     *
     * @param versionedMappableId The id of the versioned mappable for which protected mappable information is being looked up.
     * @param pageable The pageable information for request.
     * @return Protected mappable information that indicates that the versioned mappable is locked for a given mapping type.
     */
    public Mono<Page<ProtectedMappableInformationDMO>> findAllByVersionedMappable(
            final UUID versionedMappableId,
            final Pageable pageable
    ) {
        return createPagedStarSingleWhereRequest("versioned_mappable_id", versionedMappableId, pageable);
    }

    /**
     * Finds all the protected versioned mappable information which indicate that a given mapping type is locked
     * for versioned mappables.
     *
     * @param mappingTypeId The id of the mapping type for which protected mappable information is being looked up.
     * @param pageable The paging and sorting information.
     * @return Protected mappable information that indicates that the mapping type is locked for a given versioned mappable.
     */
    public Mono<Page<ProtectedMappableInformationDMO>> findAllByMappingType(
            final UUID mappingTypeId,
            final Pageable pageable
    ) {
      return createPagedStarSingleWhereRequest("mapping_type_id", mappingTypeId, pageable);
    }
}