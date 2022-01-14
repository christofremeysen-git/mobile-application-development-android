using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace project_api.Models
{
    public class Project
    {
        #region Properties
        public int Id { get; set; }

        [Required]
        [StringLength(25, MinimumLength=5)]
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

        //public ICollection<Taak> Taken { get; private set; }
        #endregion

        #region Constructors
        public Project()
        {
            //Taken = new List<Taak>();
        }

        public Project(string naam, DateTime startDatum, DateTime eindDatum, decimal budget, string status, string type): this()
        {
            Naam = naam;
            StartDatum = startDatum;
            EindDatum = eindDatum;
            Budget = budget;
            Status = status;
            Type = type;
        }
        #endregion

        #region Methods
        /*public void AddTaak(Taak taak) => Taken.Add(taak);

        public Taak GetTaak(int id) => Taken.SingleOrDefault(t => t.Id == id);

        public void DeleteTaak(Taak taak) => Taken.Remove(taak);*/
        #endregion


    }
}
