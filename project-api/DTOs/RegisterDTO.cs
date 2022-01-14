using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
/*
namespace project_api.DTOs
{
  public class RegisterDTO: LoginDTO
  {
    [Required]
    [StringLength(50)]
    public String Voornaam { get; set; }

    [Required]
    [StringLength(50)]
    public String Achternaam { get; set; }

    [Required]
    public String Onderneming { get; set; }

    [Required]
    [StringLength(25)]
    public String Functie { get; set; }

    [Required]
    public String Status { get; set; }

    [Required]
    [Compare("Password")]
    [RegularExpression("^((?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])|(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[^a-zA-Z0-9])|(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[^a-zA-Z0-9])|(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^a-zA-Z0-9])).{8,}$", ErrorMessage = "Passwords must be at least 8 characters and contain at 3 of 4 of the following: upper case (A-Z), lower case (a-z), number (0-9) and special character (e.g. !@#$%^&*)")]
    public String PasswordConfirmation { get; set; }
  }
}
*/