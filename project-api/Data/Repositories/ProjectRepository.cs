using Microsoft.EntityFrameworkCore;
using project_api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace project_api.Data.Repositories
{
    public class ProjectRepository : IProjectRepository
    {
        private readonly ProjectContext _context;
        private readonly DbSet<Project> _projects;

        public ProjectRepository(ProjectContext dbContext)
        {
            _context = dbContext;
            _projects = dbContext.Projects;
        }

        public void Add(Project project)
        {
            _projects.Add(project);
        }

        public void Delete(Project project)
        {
            _projects.Remove(project);
        }

        public IEnumerable<Project> GetAll()
        {
            return _projects/*.Include(t => t.Taken)*/.ToList();
        }

        public Project GetBy(int id)
        {
            return _projects/*.Include(t => t.Taken)*/.SingleOrDefault(p => p.Id == id);
        }

        public void SaveChanges()
        {
            _context.SaveChanges();
        }

        public bool TryGetProject(int id, out Project project)
        {
            project = _context.Projects/*.Include(t => t.Taken)*/.FirstOrDefault(p => p.Id == id);
            return project != null;
        }

        public void Update(Project project)
        {
            _context.Update(project);
        }
    }
}
