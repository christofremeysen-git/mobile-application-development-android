using Microsoft.EntityFrameworkCore;
using project_api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
/*
namespace project_api.Data.Repositories
{
  public class CustomerRepository : ICustomerRepository
  {
    #region Properties
    private readonly ProjectContext _context;
    private readonly DbSet<Customer> _customers;
    #endregion

    public CustomerRepository(ProjectContext dbContext)
    {
      _context = dbContext;
      _customers = dbContext.Customers;
    }


    public void Add(Customer customer)
    {
      _customers.Add(customer);
    }

    public Customer GetBy(string email)
    {
      return _customers.Include(c => c.Projects).ThenInclude(op => op.Project).ThenInclude(p => p.Taken).SingleOrDefault(c => c.Email == email);
    }

    public void SaveChanges()
    {
      _context.SaveChanges();
    }
  }
}
*/