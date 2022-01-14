using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;
/*
namespace project_api.Models
{
    public class Taak
    {
        #region Properties
        public int Id { get; set; }

        [Required]
        [StringLength(50, MinimumLength = 5)]
        public string Naam { get; set; }

        [Required]
        [DataType(DataType.Date)]
        public DateTime TaakStartDatum { get; set; }

        [Required]
        [DataType(DataType.Date)]
        public DateTime TaakEindDatum { get; set; }

        [Required]
        [StringLength(15, MinimumLength = 5)]
        public string Categorie { get; set; }

        [Required]
        public string Prioriteit { get; set; }

        [Required]
        public string Status { get; set; }
        #endregion

        #region Constructors
        public Taak() {
        }
        public Taak(string naam, DateTime taakStartDatum, DateTime taakEindDatum, string categorie, string prioriteit, string status)
        {
            Naam = naam;
            TaakStartDatum = taakStartDatum;
            TaakEindDatum = taakEindDatum;
            Categorie = categorie;
            Prioriteit = prioriteit;
            Status = status;
        }
        #endregion
    }
}
*/