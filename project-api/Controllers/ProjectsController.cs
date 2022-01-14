using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using project_api.DTOs;
using project_api.Models;
using System.Collections.Generic;
using System.Linq;

namespace project_api.Controllers
{
  [ApiConventionType(typeof(DefaultApiConventions))]
    [Produces("application/json")]
    [Route("api/[controller]")]
    [ApiController]
    // [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    public class ProjectsController : ControllerBase
    {
        private readonly IProjectRepository _projectRepository;
        // private readonly ICustomerRepository _customerRepository;

        public ProjectsController(IProjectRepository context/*, ICustomerRepository customerRepository*/)
        {
            _projectRepository = context;
            // _customerRepository = customerRepository;
        }

        // GET: api/Projects
        /// <summary>
        /// Get all projects, ordered subsequently by naam, status and budget
        /// </summary>
        /// <returns>An array of projects.</returns>
        [HttpGet]
        public IEnumerable<Project> GetProjects()
        {
            return _projectRepository.GetAll().OrderBy(p => p.Naam).ThenBy(p => p.Status).ThenByDescending(p => p.Budget);
        }

        // GET: api/Projects/{id}
        /// <summary>
        /// Get the project with a given id.
        /// </summary>
        /// <param name="id">The id of the project.</param>
        /// <returns>The applicable project.</returns>
        [HttpGet("{id}")]
        public ActionResult<Project> GetProject(int id)
        {
            Project project = _projectRepository.GetBy(id);
            if(project == null)
            {
                return NotFound();
            }
            return Ok(project);
        }

        /// <summary>
        /// Get projects of current user
        /// </summary>
        /*[HttpGet("Projects")]
        public IEnumerable<Project> GetOwnProjects()
        {
            Customer customer = _customerRepository.GetBy(User.Identity.Name);
            return customer.OwnProjects;
        }*/

        // POST: api/Projects
        /// <summary>
        /// Add a new project.
        /// </summary>
        /// <param name="project">The new project.</param>
        /// <returns></returns>
        [HttpPost]
        public ActionResult<Project> Create(ProjectDTO project)
        {
            Project projectToCreate = new Project()
            {
                Naam = project.Naam,
                StartDatum = project.StartDatum,
                EindDatum = project.EindDatum,
                Budget = project.Budget,
                Status = project.Status,
                Type = project.Type
            };

            /*foreach(var t in project.Taken)
            {
                projectToCreate.AddTaak(new Taak(t.Naam, t.TaakStartDatum, t.TaakEindDatum, t.Categorie, t.Prioriteit, t.Status));
            }*/

            _projectRepository.Add(projectToCreate);
            _projectRepository.SaveChanges();

            return CreatedAtAction(nameof(GetProject), new { id = projectToCreate.Id}, projectToCreate);
        }

        // PUT: api/Projects/{id}
        /// <summary>
        /// Modify a project.
        /// </summary>
        /// <param name="id">The id of the project to be modified.</param>
        /// <param name="project">The modified project.</param>
        /// <returns></returns>
        [HttpPut("{id}")]
        public ActionResult Update(int id, Project project)
        {
            Project projectToUpdate = _projectRepository.GetBy(id);
            if (projectToUpdate == null)
            {
                return NotFound();
            }
            if (id != project.Id)
            {
                return BadRequest();
            }

            projectToUpdate.Naam = project.Naam;
            projectToUpdate.StartDatum = project.StartDatum;
            projectToUpdate.EindDatum = project.EindDatum;
            projectToUpdate.Budget = project.Budget;
            projectToUpdate.Status = project.Status;
            projectToUpdate.Type = project.Type;

            _projectRepository.Update(projectToUpdate);
            _projectRepository.SaveChanges();

            return NoContent();
        }

        // DELETE: api/Projects/{id}
        /// <summary>
        /// Delete a project.
        /// </summary>
        /// <param name="id">The id of the project marked for deletion.</param>
        /// <returns></returns>
        // [Authorize(Roles = "admin, admin customer")]
        [HttpDelete("{id}")]
        public IActionResult DeleteProject(int id)
        {
            Project project = _projectRepository.GetBy(id);
            if(project == null)
            {
                return NotFound();
            }

            _projectRepository.Delete(project);
            _projectRepository.SaveChanges();
            return NoContent();
        }

        // GET: api/Projects/{id}/Taken/{taakId}
        /// <summary>
        /// Get a taak for a project.
        /// </summary>
        /// <param name="id">The id of the project.</param>
        /// <param name="taakId">The id of the taak.</param>
        /// <returns>The applicable taak.</returns>
        /*[HttpGet("{id}/Taken/{taakId}")]
        public ActionResult<Taak> GetTaak(int id, int taakId)
        {
            if(!_projectRepository.TryGetProject(id, out var project))
            {
                return NotFound();
            }

            Taak taak = project.GetTaak(taakId);

            if(taak == null)
            {
                return NotFound();
            }

            return taak;
        }*/

        // POST: api/Projects/{id}/Taken
        /// <summary>
        /// Add a taak to a project.
        /// </summary>
        /// <param name="id">The id of the project.</param>
        /// <param name="taak">The taak to be added.</param>
        /// <returns></returns>
        /*[HttpPost("{id}/taken")]
        public ActionResult<Taak> PostTaak(int id, TaakDTO taak) 
        { 
            if(!_projectRepository.TryGetProject(id, out var project))
            {
                return NotFound();
            }
            
            var taakToCreate = new Taak(taak.Naam, taak.TaakStartDatum, taak.TaakEindDatum, taak.Categorie, taak.Prioriteit, taak.Status);
            project.AddTaak(taakToCreate);
            _projectRepository.SaveChanges();

            return CreatedAtAction("GetTaak", new { id = project.Id, taakId = taakToCreate.Id }, taakToCreate);
        }*/

        // DELETE: api/Projects/{id}/Taken/{taakId}
        /// <summary>
        /// Delete a taak from a project.
        /// </summary>
        /// <param name="id">The id of the project.</param>
        /// <param name="taakId">The id of the taak marked for deletion.</param>
        /// <returns></returns>
        // [Authorize(Roles = "admin, admin customer")]
        /*[HttpDelete("{id}/Taken/{taakId}")]
        public IActionResult DeleteTaak(int id, int taakId)
        {
            if (!_projectRepository.TryGetProject(id, out var project))
            {
                return NotFound();
            }

            var taakToDelete = project.GetTaak(taakId);

            if(taakToDelete == null)
            {
                return NotFound();
            }

            project.DeleteTaak(taakToDelete);
            _projectRepository.SaveChanges();

            return NoContent();
        }*/

        // PUT: api/Projects/{id}/Taken/{taakId}
        /// <summary>
        /// Modify a taak pertaining to a project.
        /// </summary>
        /// <param name="id">The id of the project.</param>
        /// <param name="taakId">The id of the taak to be modified.</param>
        /// <param name="taak">The modified taak.</param>
        /// <returns></returns>
        /*[HttpPut("{id}/Taken/{taakId}")]
        public ActionResult UpdateTaak(int id, int taakId, Taak taak)
        {
            if (!_projectRepository.TryGetProject(id, out var project))
            {
                return NotFound();
            }

            var taakToUpdate = project.GetTaak(taakId);

            if (taakToUpdate == null)
            {
                return NotFound();
            }

            taakToUpdate.Naam = taak.Naam;
            taakToUpdate.TaakStartDatum = taak.TaakStartDatum;
            taakToUpdate.TaakEindDatum = taak.TaakEindDatum;
            taakToUpdate.Categorie = taak.Categorie;
            taakToUpdate.Prioriteit = taak.Prioriteit;
            taakToUpdate.Status = taak.Status;

            _projectRepository.SaveChanges();

            return NoContent();
        }*/
    }
}
