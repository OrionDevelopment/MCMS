package com.mcms.data.core.repositories.mapping.mappable;

import java.util.UUID;

import com.mcms.api.datamodel.mapping.mappable.MappableInGameVersionInheritanceDataDMO;
import com.mcms.data.core.repositories.IRepository;
import reactor.core.publisher.Flux;

/**
 * Represents a repository which can provide and store {@link MappableInGameVersionInheritanceDataDMO} objects.
 */
public interface IMappableInGameVersionInheritanceDataRepository extends IRepository<MappableInGameVersionInheritanceDataDMO> {

    /**
     * Finds all the inheritance data in which the given mappable in game version class is
     * the super type tole.
     *
     * @param superTypeMappableInGameVersionId The id of the mappable in game version class for which the inheritance data in super type role will be looked up.
     * @return All inheritance data which indicates that the given mappable in a game version is a super type.
     */
    Flux<MappableInGameVersionInheritanceDataDMO> findAllForSuperType(UUID superTypeMappableInGameVersionId);

    /**
     * Finds all the inheritance data in which the given mappable in game version class is
     * the sub type tole.
     *
     * @param subTypeMappableInGameVersionId The id of the mappable in game version class for which the inheritance data in sub type role will be looked up.
     * @return All inheritance data which indicates that the given mappable in a game version is a sub type.
     */
    Flux<MappableInGameVersionInheritanceDataDMO> findAllForSubType(UUID subTypeMappableInGameVersionId);
}
