using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace project_api.Models
{
    public interface IProjectRepository
    {
        Project GetBy(int id);

        bool TryGetProject(int id, out Project project);

        IEnumerable<Project> GetAll();

        void Add(Project project);

        void Delete(Project project);

        void Update(Project project);

        void SaveChanges();
    }
}
