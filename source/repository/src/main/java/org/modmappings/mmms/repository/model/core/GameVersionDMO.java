package org.modmappings.mmms.repository.model.core;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Represents a single version of the game.
 */
@Table("game_version")
public class GameVersionDMO {

    @Id
    private final UUID id;
    private final UUID createdBy;
    private final Timestamp createdOn;
    private String name;
    private boolean isPreRelease;
    private boolean isSnapshot;

    @PersistenceConstructor
    GameVersionDMO(final UUID id, final UUID createdBy, final Timestamp createdOn, final String name, final boolean isPreRelease, final boolean isSnapshot) {
        this.id = id;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.name = name;
        this.isPreRelease = isPreRelease;
        this.isSnapshot = isSnapshot;
    }

    public GameVersionDMO(final UUID createdBy, final String name, final boolean isPreRelease, final boolean isSnapshot) {
        this.id = UUID.randomUUID();
        this.createdBy = createdBy;
        this.createdOn = Timestamp.from(Instant.now());
        this.name = name;
        this.isPreRelease = isPreRelease;
        this.isSnapshot = isSnapshot;
    }

    public UUID getId() {
        return id;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPreRelease() {
        return isPreRelease;
    }

    public void setPreRelease(boolean preRelease) {
        isPreRelease = preRelease;
    }

    public boolean isSnapshot() {
        return isSnapshot;
    }

    public void setSnapshot(boolean snapshot) {
        isSnapshot = snapshot;
    }

    @Override
    public String toString() {
        return "GameVersionDMO{" +
                               "id=" + id +
                               ", createdBy=" + createdBy +
                               ", createdOn=" + createdOn +
                               ", name='" + name + '\'' +
                               ", isPreRelease=" + isPreRelease +
                               ", isSnapshot=" + isSnapshot +
                               '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final GameVersionDMO that = (GameVersionDMO) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
