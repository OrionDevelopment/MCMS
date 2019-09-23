using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Data.Core.Models.Core;

namespace Data.Core.Models.Mapping
{
    public class LockingEntry
    {
        [Required]
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid Id { get; set; }

        [Required]
        public VersionedComponent VersionedComponent { get; set; }

        [Required]
        public MappingType MappingType { get; set; }
    }
}