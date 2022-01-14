using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using project_api.DTOs;
using project_api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
/*
namespace project_api.Controllers
{
  [ApiConventionType(typeof(DefaultApiConventions))]
  [Produces("application/json")]
  [Route("api/[controller]")]
  [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
  [Authorize(Policy = "AdminOnly")]
  [ApiController]
  public class CustomerController : ControllerBase
  {
    private readonly ICustomerRepository _customerRepository;

    public CustomerController(ICustomerRepository customerRepository)
    {
      _customerRepository = customerRepository;
    }

    /// <summary>
    /// Get the details of the authenticated customer
    /// </summary>
    /// <returns>the customer</returns>
    [AllowAnonymous]
    [HttpGet()]
    public ActionResult<CustomerDTO> GetCustomer()
    {
      Customer customer = _customerRepository.GetBy(User.Identity.Name);
      return new CustomerDTO(customer);
    }
  }
}
*/