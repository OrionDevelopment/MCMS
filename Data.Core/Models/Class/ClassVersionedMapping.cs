using System;
using System.Linq;
using Data.Core.Models.Core;

namespace Data.Core.Models.Class
{
    public class ClassVersionedMapping
        : AbstractVersionedMapping<ClassMapping, ClassVersionedMapping, ClassCommittedMappingEntry, ClassProposalMappingEntry>
    {
        public string Package { get; set; }

        public ClassVersionedMapping Outer { get; set; }

        public IQueryable<ClassVersionedMapping> InheritsFrom { get; set; }
    }
}
