using Microsoft.AspNetCore.Identity;
using project_api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;

namespace project_api.Data
{
    public class ProjectDataInitializer
    {
        private readonly ProjectContext _dbContext;
        // private readonly UserManager<IdentityUser> _userManager;

        public ProjectDataInitializer(ProjectContext dbContext/*, UserManager<IdentityUser> userManager*/)
        {
            _dbContext = dbContext;
            // _userManager = userManager;
        }

        public async Task InitializeData()
        {
            _dbContext.Database.EnsureDeleted();
            if (_dbContext.Database.EnsureCreated())
            {
                // Seeding the database with projects (see DBContext)
                /*Customer admin = new Customer { Email="projectmaster@skedio.be", Voornaam="Christof", Achternaam="Remeysen", Onderneming = "Skedio", Functie ="Technology Director", Status="Active" };
                _dbContext.Customers.Add(admin);
                await CreateUser(admin.Email, "P@ssword1234", "admin");

                Customer customer = new Customer { Email="customer1@skedio.be", Voornaam="Jan", Achternaam="Adminaal", Onderneming="CompanyX", Functie="IT Manager", Status="Active" };
                _dbContext.Customers.Add(customer);
                customer.AddOwnProject(_dbContext.Projects.First());
                customer.AddOwnProject(_dbContext.Projects.First(p => p.Naam == "Product management app"));
                await CreateUser(customer.Email, "P@ssword1234", "admin customer");

                customer = new Customer { Email="customer2@skedio.be", Voornaam="Jan", Achternaam="Modaal", Onderneming="CompanyX", Functie="HR Manager", Status="Active" };
                _dbContext.Customers.Add(customer);
                customer.AddOwnProject(_dbContext.Projects.First());
                await CreateUser(customer.Email, "P@ssword1234", "customer");*/

                _dbContext.SaveChanges();
            }
        }

        /*private async Task CreateUser(string email, string password, string role)
        {
            var user = new IdentityUser { UserName = email, Email = email };
            await _userManager.CreateAsync(user, password);
            await _userManager.AddClaimAsync(user, new Claim(ClaimTypes.Role, role));
        }*/
    }
}
