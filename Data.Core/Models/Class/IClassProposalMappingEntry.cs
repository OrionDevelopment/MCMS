using Data.Core.Models.Core;

namespace Data.Core.Models.Class
{
    /// <summary>
    /// A proposal for changing a class mapping.
    /// The actually mapped data might not changes
    /// </summary>
    public interface IClassProposalMappingEntry : IProposalMappingEntry
    {

        /// <summary>
        /// Indicates the proposed package the class is in.
        /// </summary>
        string Package { get; set; }

        /// <summary>
        /// The parent class.
        /// Is null if this is not an internal class.
        /// </summary>
        IClassMapping ParentClass { get; set; }
    }
}
