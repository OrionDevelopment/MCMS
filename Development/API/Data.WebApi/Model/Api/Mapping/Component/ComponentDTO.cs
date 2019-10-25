using System;
using System.Collections.Generic;
using Mcms.Api.Data.Poco.Models.Mapping.Component;

namespace Data.WebApi.Model.Api.Mapping.Component
{
    /// <summary>
    /// A domain transitioning object that represents a single component that is mapped.
    /// A component tracks a mapped piece of source code across the version history of the game.
    /// </summary>
    /// <remarks>
    /// Certain properties of this dto might not be changeable.
    /// These are marked.
    /// It might be possible to change them using a workflow, or some are unchangeable by design.
    /// </remarks>
    public class ComponentDto
    {
        /// <summary>
        /// The id of the component.
        /// </summary>
        /// <remarks>Is readonly.</remarks>
        public Guid Id { get; }

        /// <summary>
        /// The type of the component.
        /// </summary>
        /// <see cref="ComponentType"/>
        public ComponentType Type { get; set; }

        /// <summary>
        /// The versioned variants of a given component.
        /// The keys of this dictionary are the game version names, values are the ids for the versioned components.
        /// </summary>
        /// <remarks>Is readonly. Changes can be made via workflows related to versioned components.</remarks>
        public IReadOnlyDictionary<string, Guid> VersionedComponents { get; }
    }
}
