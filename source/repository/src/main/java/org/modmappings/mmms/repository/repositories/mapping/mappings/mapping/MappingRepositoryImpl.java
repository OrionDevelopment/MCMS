package org.modmappings.mmms.repository.repositories.mapping.mappings.mapping;

import org.modmappings.mmms.er2dbc.data.access.strategy.ExtendedDataAccessStrategy;
import org.modmappings.mmms.er2dbc.data.statements.criteria.ColumnBasedCriteria;
import org.modmappings.mmms.er2dbc.data.statements.mapper.ExtendedStatementMapper;
import org.modmappings.mmms.er2dbc.data.statements.select.SelectSpecWithJoin;
import org.modmappings.mmms.repository.model.mapping.mappable.MappableTypeDMO;
import org.modmappings.mmms.repository.model.mapping.mappings.MappingDMO;
import org.modmappings.mmms.repository.repositories.AbstractModMappingRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.PreparedOperation;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import javax.annotation.Priority;
import java.util.List;
import java.util.UUID;

import static org.modmappings.mmms.er2dbc.data.statements.criteria.ColumnBasedCriteria.on;
import static org.modmappings.mmms.er2dbc.data.statements.expression.Expressions.reference;
import static org.modmappings.mmms.er2dbc.data.statements.join.JoinSpec.*;
import static org.modmappings.mmms.er2dbc.data.statements.sort.SortSpec.Order.asc;
import static org.modmappings.mmms.er2dbc.data.statements.sort.SortSpec.Order.desc;
import static org.modmappings.mmms.er2dbc.data.statements.sort.SortSpec.sort;

/**
 * Represents a repository which can provide and store {@link MappingDMO} objects.
 */
@Primary
@Priority(Integer.MAX_VALUE)
class MappingRepositoryImpl extends AbstractModMappingRepository<MappingDMO> implements MappingRepository {

    public MappingRepositoryImpl(final DatabaseClient databaseClient, final ExtendedDataAccessStrategy accessStrategy) {
        super(databaseClient, accessStrategy, MappingDMO.class);
    }

    @Override
    public Mono<Page<MappingDMO>> findAllOrLatestFor(final Boolean latestOnly,
                                                     final UUID versionedMappableId,
                                                     final UUID releaseId,
                                                     final MappableTypeDMO mappableType,
                                                     final String inputRegex,
                                                     final String outputRegex,
                                                     final UUID mappingTypeId,
                                                     final UUID gameVersionId,
                                                     final UUID userId,
                                                     final UUID parentClassId,
                                                     final UUID parentMethodId,
                                                     final String parentClassPackagePath,
                                                     final boolean externallyVisibleOnly,
                                                     final Pageable pageable) {
        return createPagedStarRequest(
                selectSpecWithJoin -> {
                    if (latestOnly) {
                        selectSpecWithJoin = selectSpecWithJoin.withDistinctOn(reference("output"));
                    }

                    selectSpecWithJoin = selectSpecWithJoin
                            .join(() -> optionalJoin(parentClassId != null || parentMethodId != null, "versioned_mappable", "vm")
                                    .on(() -> on(reference("versioned_mappable_id")).is(reference("vm", "id")))
                            )
                            .join(() -> nonNullLeftOuterJoin(releaseId, "release_component", "rc")
                                    .on(() -> on(reference("id")).is(reference("rc", "mapping_id"))))
                            .join(() -> join("mapping_type", "mt")
                                    .on(() -> on(reference("mapping_type_id")).is(reference("mt", "id"))))
                            .where(
                                    () -> {
                                        ColumnBasedCriteria criteria = nonNullAndEqualsCheckForWhere(null, versionedMappableId, "", "versioned_mappable_id");
                                        criteria = nonNullAndEqualsCheckForWhere(criteria, releaseId, "rc", "release_id");
                                        criteria = nonNullAndEqualsCheckForWhere(criteria, mappableType, "", "mappable_type");
                                        criteria = nonNullAndMatchesCheckForWhere(criteria, inputRegex, "", "input");
                                        criteria = nonNullAndMatchesCheckForWhere(criteria, outputRegex, "", "output");
                                        criteria = nonNullAndEqualsCheckForWhere(criteria, mappingTypeId, "", "mapping_type_id");
                                        criteria = nonNullAndEqualsCheckForWhere(criteria, gameVersionId, "", "game_version_id");
                                        criteria = nonNullAndEqualsCheckForWhere(criteria, parentClassId, "vm", "parent_class_id");
                                        criteria = nonNullAndEqualsCheckForWhere(criteria, parentMethodId, "vm", "parent_method_id");
                                        criteria = nonNullAndEqualsCheckForWhere(criteria, userId, "", "created_by");
                                        criteria = nonNullAndEqualsCheckForWhere(criteria, parentClassPackagePath, "", "package_path");

                                        if (externallyVisibleOnly) {
                                            criteria = nonNullAndEqualsCheckForWhere(criteria, true, "mt", "visible");
                                        }

                                        return criteria;
                                    }
                            );

                    if (latestOnly)
                    {
                        selectSpecWithJoin = selectSpecWithJoin.withSort(sort(asc(reference("output"))).and(desc(reference("created_on"))));
                    }

                    return selectSpecWithJoin;
                }
                ,
                pageable
        );
    }

    @Override
    public Mono<MappingDMO> findById(final UUID id,
                                     final boolean externallyVisibleOnly) {
        Assert.notNull(id, "Id must not be null!");

        final List<String> columns = getAccessStrategy().getAllColumns(this.getEntity().getJavaType());
        final String idColumnName = getIdColumnName();

        final ExtendedStatementMapper mapper = getAccessStrategy().getStatementMapper().forType(this.getEntity().getJavaType());
        final SelectSpecWithJoin specWithJoin = mapper.createSelectWithJoin(this.getEntity().getTableName())
                .withProjectionFromColumnName(columns)
                .join(() -> join("mapping_type", "mt").on(() -> on(reference("mapping_type_id")).is(reference("mt", "id"))))
                .where(() -> {
                    ColumnBasedCriteria criteria = nonNullAndEqualsCheckForWhere(
                            null,
                            id,
                            "",
                            idColumnName
                    );

                    if (externallyVisibleOnly) {
                        criteria = nonNullAndEqualsCheckForWhere(
                                criteria,
                                true,
                                "mt",
                                "visible"
                        );
                    }

                    return criteria;
                });


        final PreparedOperation<?> operation = mapper.getMappedObject(specWithJoin);

        return this.getDatabaseClient().execute(operation) //
                .as(this.getEntity().getJavaType()) //
                .fetch() //
                .one();
    }
}
