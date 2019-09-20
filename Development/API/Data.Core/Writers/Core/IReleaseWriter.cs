using Data.Core.Models.Core;
using Data.Core.Readers.Core;

namespace Data.Core.Writers.Core
{
    public interface IReleaseWriter
        : IReleaseReader, IWriter<Release>
    {
    }
}