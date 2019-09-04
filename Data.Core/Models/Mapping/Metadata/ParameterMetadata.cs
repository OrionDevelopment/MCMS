using System.ComponentModel.DataAnnotations;

namespace Data.Core.Models.Mapping.MetaData
{
    public class ParameterMetadata
        : VersionedComponentMetadataBase
    {
        [Required]
        public virtual MethodMetadata ParameterOf { get; set; }

        [Required]
        public int Index { get; set; }
    }
}
