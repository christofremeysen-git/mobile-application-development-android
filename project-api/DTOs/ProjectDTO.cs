using project_api.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace project_api.DTOs
{
    public class ProjectDTO
    {
        [Required]
        [StringLength(25, MinimumLength = 5)]
        public string Naam { get; set; }

        [Required]
        [DataType(DataType.Date)]
        public DateTime StartDatum { get; set; }

        [Required]
        [DataType(DataType.Date)]
        public DateTime EindDatum { get; set; }

        [Required]
        [Range(0, int.MaxValue)]
        [DataType(DataType.Currency)]
        public decimal Budget { get; set; }

        [Required]
        public string Status { get; set; }

        [Required]
        public string Type { get; set; }

        //public IList<TaakDTO> Taken { get; set; }
    }
}
