using System;
using System.Collections.Generic;
using System.Linq;
using Flurl;

namespace Data.FabricImporter.Maven
{
    public class MavenArtifact
    {
        public static MavenArtifact Create(MavenProject project, string version, string classifier = null, string extension = "jar")
        {
            return new MavenArtifact(project, version, classifier, extension);
        }

        private MavenArtifact(MavenProject project, string version, string classifier = null, string extension = "jar")
        {
            Project = project ?? throw new ArgumentNullException(nameof(project));
            Version = version ?? throw new ArgumentNullException(nameof(version));
            Classifier = classifier;
            Extension = extension ?? throw new ArgumentException(nameof(extension));
        }

        public MavenProject Project { get; private set; }

        public string Version { get; private set; }

        public string Classifier { get; private set; }

        public string Extension { get; private set; }

        public string FileName =>
            $"{(new List<string> {Project.Name, Version, Classifier}).Where(s => !string.IsNullOrWhiteSpace(s)).AsEnumerable().Aggregate((s, s1) => $"{s}-{s1}")}.{Extension}";

        public Url Path => new Url(Project.Path).AppendPathSegment(Version).AppendPathSegment(FileName);
    }
}