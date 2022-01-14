using System;
using System.Collections.Generic;
using System.Linq;
/*
namespace project_api.Models
{
  public class Customer
  {
    #region Properties
    public int CustomerId { get; set; }

    public string Voornaam{ get; set; }

    public string Achternaam { get; set; }

    public string Email { get; set; }

    public string Onderneming { get; set; }

    public string Functie { get; set; }

    public string ProjectRol { get; set; }

    public Boolean MarketingOptin { get; set; }

    public string Expertise { get; set; }

    public string Status { get; set; }

    public ICollection<CustomerProject> Projects { get; private set; }

    public IEnumerable<Project> OwnProjects => Projects.Select(c => c.Project);
    #endregion

    #region Constructors
    public Customer()
    {
      Projects = new List<CustomerProject>();
    }
    #endregion

    #region
    public void AddOwnProject(Project project)
    {
      Projects.Add(new CustomerProject() { ProjectId = project.Id, CustomerId = CustomerId, Project = project, Customer = this });
    }
    #endregion

  }
}
*/