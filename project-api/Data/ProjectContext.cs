using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using project_api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace project_api.Data
{
    public class ProjectContext: IdentityDbContext
  {
        public ProjectContext(DbContextOptions<ProjectContext> options): base(options)
        {
        }

        protected override void OnModelCreating(ModelBuilder builder)
        {
            base.OnModelCreating(builder);
            builder.Entity<Project>();
                //.HasMany(p => p.Taken)
                //.WithOne()
                //.IsRequired()
                //.HasForeignKey("ProjectId"); // Shadow property

            builder.Entity<Project>().Property(p => p.Naam).IsRequired().HasMaxLength(25);
            builder.Entity<Project>().Property(p => p.StartDatum).IsRequired();
            builder.Entity<Project>().Property(p => p.EindDatum).IsRequired();
            builder.Entity<Project>().Property(p => p.Budget).IsRequired();
            builder.Entity<Project>().Property(p => p.Status).IsRequired();
            builder.Entity<Project>().Property(p => p.Type).IsRequired();

            /*builder.Entity<Taak>().Property(t => t.Naam).IsRequired().HasMaxLength(50);
            builder.Entity<Taak>().Property(t => t.TaakStartDatum).IsRequired();
            builder.Entity<Taak>().Property(t => t.TaakEindDatum).IsRequired();
            builder.Entity<Taak>().Property(t => t.Categorie).IsRequired().HasMaxLength(15);
            builder.Entity<Taak>().Property(t => t.Prioriteit).IsRequired();
            builder.Entity<Taak>().Property(t => t.Status).IsRequired();*/

            /*builder.Entity<Customer>().Property(c => c.Voornaam).IsRequired().HasMaxLength(50);
            builder.Entity<Customer>().Property(c => c.Achternaam).IsRequired().HasMaxLength(50);
            builder.Entity<Customer>().Property(c => c.Email).IsRequired().HasMaxLength(100);
            builder.Entity<Customer>().Property(c => c.Onderneming).IsRequired().HasMaxLength(25);
            builder.Entity<Customer>().Property(c => c.Functie).IsRequired().HasMaxLength(25);
            builder.Entity<Customer>().Property(c => c.ProjectRol).HasMaxLength(25);
            builder.Entity<Customer>().Property(c => c.MarketingOptin);
            builder.Entity<Customer>().Property(c => c.Expertise).HasMaxLength(25);
            builder.Entity<Customer>().Property(c => c.Status).IsRequired();
            builder.Entity<Customer>().Ignore(c => c.OwnProjects);

            builder.Entity<CustomerProject>().HasKey(op => new { op.CustomerId, op.ProjectId });
            builder.Entity<CustomerProject>().HasOne(op => op.Customer).WithMany(c => c.Projects).HasForeignKey(op => op.CustomerId);
            builder.Entity<CustomerProject>().HasOne(op => op.Project).WithMany().HasForeignKey(op => op.ProjectId);   */         
            
            builder.Entity<Project>().HasData(
                // Dummy projecten
                new Project
                {
                  Id = 1,
                  Naam = "Post app tracking",
                  StartDatum = new DateTime(2021, 01, 15),
                  EindDatum = new DateTime(2021, 04, 15),
                  Budget = 100000,
                  Status = "Voltooid",
                  Type = "Marketing, Product management",
                },
                new Project
                {
                  Id = 2,
                  Naam = "Magazine-app",
                  StartDatum = new DateTime(2021, 03, 05),
                  EindDatum = new DateTime(2021, 07, 20),
                  Budget = 250000,
                  Status = "Bezig",
                  Type = "Marketing, Product management, Technology",
                }
                ,
                new Project
                {
                  Id = 3,
                  Naam = "GDPR audit",
                  StartDatum = new DateTime(2021, 10, 10),
                  EindDatum = new DateTime(2021, 12, 15),
                  Budget = 20000,
                  Status = "Open",
                  Type = "Finance, General management, R&D",
                },
                new Project
                {
                  Id = 4,
                  Naam = "Product management app",
                  StartDatum = new DateTime(2021, 05, 01),
                  EindDatum = new DateTime(2021, 09, 30),
                  Budget = 1500000,
                  Status = "Bezig",
                  Type = "Product management, R&D, Technology",
                },
                new Project
                {
                  Id = 5,
                  Naam = "Tevredenheid oogproduct",
                  StartDatum = new DateTime(2021, 07, 17),
                  EindDatum = new DateTime(2021, 10, 31),
                  Budget = 80000,
                  Status = "Open",
                  Type = "R&D, Sales",
                },
                new Project
                {
                  Id = 6,
                  Naam = "Lunch teambuilding",
                  StartDatum = new DateTime(2021, 08, 01),
                  EindDatum = new DateTime(2021, 08, 15),
                  Budget = 17999,
                  Status = "Open",
                  Type = "General management",
                },
                new Project
                {
                  Id = 7,
                  Naam = "Facebook pagina opzetten",
                  StartDatum = new DateTime(2021, 02, 07),
                  EindDatum = new DateTime(2021, 06, 10),
                  Budget = 75000,
                  Status = "Hangende",
                  Type = "General management, Marketing, Technology",
                },
                new Project
                {
                  Id = 8,
                  Naam = "Event tracking website",
                  StartDatum = new DateTime(2021, 04, 10),
                  EindDatum = new DateTime(2021, 04, 21),
                  Budget = 8500,
                  Status = "Voltooid",
                  Type = "Marketing, Technology",
                },
                new Project
                {
                  Id = 9,
                  Naam = "Campagne dashboard",
                  StartDatum = new DateTime(2021, 02, 01),
                  EindDatum = new DateTime(2021, 03, 15),
                  Budget = 5000,
                  Status = "Voltooid",
                  Type = "Marketing, Technology",
                },
                new Project
                {
                  Id = 10,
                  Naam = "Werving nieuwe collega",
                  StartDatum = new DateTime(2021, 05, 01),
                  EindDatum = new DateTime(2021, 06, 15),
                  Budget = 5900,
                  Status = "Hangende",
                  Type = "General management, HR",
                }
            );

            /*builder.Entity<Taak>().HasData(
                // Dummy taken project 1
                new
                {
                  Id = 1,
                  Naam = "Firebase SDK installeren",
                  TaakStartDatum = new DateTime(2021, 01, 15),
                  TaakEindDatum = new DateTime(2021, 02, 05),
                  Categorie = "Configuratie",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 1
                },
                new
                {
                  Id = 2,
                  Naam = "Screen views configureren",
                  TaakStartDatum = new DateTime(2021, 02, 06),
                  TaakEindDatum = new DateTime(2021, 02, 15),
                  Categorie = "Medium",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 1
                },
                new
                {
                  Id = 3,
                  Naam = "Events met parameters configureren",
                  TaakStartDatum = new DateTime(2021, 02, 16),
                  TaakEindDatum = new DateTime(2021, 03, 01),
                  Categorie = "Configuratie",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 1
                },
                new
                {
                  Id = 4,
                  Naam = "Dynamic links configureren",
                  TaakStartDatum = new DateTime(2021, 03, 02),
                  TaakEindDatum = new DateTime(2021, 03, 31),
                  Categorie = "Configuratie",
                  Prioriteit = "Medium",
                  Status = "Voltooid",
                  ProjectId = 1
                },
                new
                {
                  Id = 5,
                  Naam = "Dynamic links testen",
                  TaakStartDatum = new DateTime(2021, 04, 01),
                  TaakEindDatum = new DateTime(2021, 04, 15),
                  Categorie = "Testing",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 1
                },
                // Dummy taken project 2
                new
                {
                  Id = 6,
                  Naam = "Use cases schrijven",
                  TaakStartDatum = new DateTime(2021, 03, 05),
                  TaakEindDatum = new DateTime(2021, 03, 20),
                  Categorie = "Strategie",
                  Prioriteit = "Laag",
                  Status = "Voltooid",
                  ProjectId = 2
                },
                new
                {
                  Id = 7,
                  Naam = "Meetplan opstellen",
                  TaakStartDatum = new DateTime(2021, 03, 21),
                  TaakEindDatum = new DateTime(2021, 04, 15),
                  Categorie = "Strategie",
                  Prioriteit = "Medium",
                  Status = "Voltooid",
                  ProjectId = 2
                },
                new
                {
                  Id = 8,
                  Naam = "Functionele analyse opmaken",
                  TaakStartDatum = new DateTime(2021, 04, 16),
                  TaakEindDatum = new DateTime(2021, 04, 25),
                  Categorie = "Strategie",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 2
                },
                new
                {
                  Id = 9,
                  Naam = "Code van de app schrijven",
                  TaakStartDatum = new DateTime(2021, 04, 26),
                  TaakEindDatum = new DateTime(2021, 05, 30),
                  Categorie = "Development",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 2
                },
                new
                {
                  Id = 10,
                  Naam = "Vereisten testen",
                  TaakStartDatum = new DateTime(2021, 04, 26),
                  TaakEindDatum = new DateTime(2021, 05, 30),
                  Categorie = "Testing",
                  Prioriteit = "Hoog",
                  Status = "Bezig",
                  ProjectId = 2
                },
                new
                {
                  Id = 11,
                  Naam = "App publiceren",
                  TaakStartDatum = new DateTime(2021, 05, 31),
                  TaakEindDatum = new DateTime(2021, 05, 31),
                  Categorie = "Go live",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 2
                },
                new
                {
                  Id = 12,
                  Naam = "Werkinstructies schrijven",
                  TaakStartDatum = new DateTime(2021, 06, 01),
                  TaakEindDatum = new DateTime(2021, 06, 05),
                  Categorie = "Procedures",
                  Prioriteit = "Medium",
                  Status = "Open",
                  ProjectId = 2
                },
                new
                {
                  Id = 13,
                  Naam = "Mediacampagnes opzetten",
                  TaakStartDatum = new DateTime(2021, 06, 06),
                  TaakEindDatum = new DateTime(2021, 07, 10),
                  Categorie = "Campagning",
                  Prioriteit = "Medium",
                  Status = "Open",
                  ProjectId = 2
                },
                new
                {
                  Id = 14,
                  Naam = "Campagne resultaten analyseren",
                  TaakStartDatum = new DateTime(2021, 07, 11),
                  TaakEindDatum = new DateTime(2021, 07, 20),
                  Categorie = "Analyse",
                  Prioriteit = "Laag",
                  Status = "Open",
                  ProjectId = 2
                },
                // Dummy taken project 3
                new
                {
                  Id = 15,
                  Naam = "Agenda opmaken",
                  TaakStartDatum = new DateTime(2021, 10, 10),
                  TaakEindDatum = new DateTime(2021, 10, 15),
                  Categorie = "Administratie",
                  Prioriteit = "Medium",
                  Status = "Open",
                  ProjectId = 3
                },
                new
                {
                  Id = 16,
                  Naam = "Interne procedures analyseren",
                  TaakStartDatum = new DateTime(2021, 10, 16),
                  TaakEindDatum = new DateTime(2021, 10, 31),
                  Categorie = "Procedures",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 3
                },
                new
                {
                  Id = 17,
                  Naam = "Materiaal verzamelen",
                  TaakStartDatum = new DateTime(2021, 11, 01),
                  TaakEindDatum = new DateTime(2021, 11, 25),
                  Categorie = "Filing",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 3
                },
                new
                {
                  Id = 18,
                  Naam = "Interne briefing opstellen",
                  TaakStartDatum = new DateTime(2021, 11, 26),
                  TaakEindDatum = new DateTime(2021, 11, 30),
                  Categorie = "Administratie",
                  Prioriteit = "Medium",
                  Status = "Open",
                  ProjectId = 3
                },
                new
                {
                  Id = 19,
                  Naam = "Mock-up gesprekken organiseren",
                  TaakStartDatum = new DateTime(2021, 12, 01),
                  TaakEindDatum = new DateTime(2021, 12, 05),
                  Categorie = "Begeleiding",
                  Prioriteit = "Medium",
                  Status = "Open",
                  ProjectId = 3
                },
                new
                {
                  Id = 20,
                  Naam = "Auditeur ontvangen",
                  TaakStartDatum = new DateTime(2021, 12, 06),
                  TaakEindDatum = new DateTime(2021, 12, 09),
                  Categorie = "Audit",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 3
                },
                new
                {
                  Id = 21,
                  Naam = "Uitslag communiceren",
                  TaakStartDatum = new DateTime(2021, 12, 10),
                  TaakEindDatum = new DateTime(2021, 12, 15),
                  Categorie = "Audit",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 3
                },
                // Dummy taken project 4
                new
                {
                  Id = 22,
                  Naam = "Use cases schrijven",
                  TaakStartDatum = new DateTime(2021, 05, 01),
                  TaakEindDatum = new DateTime(2021, 05, 20),
                  Categorie = "Strategie",
                  Prioriteit = "Medium",
                  Status = "Voltooid",
                  ProjectId = 4
                },
                new
                {
                  Id = 23,
                  Naam = "Functionele analyse opmaken",
                  TaakStartDatum = new DateTime(2021, 05, 21),
                  TaakEindDatum = new DateTime(2021, 05, 31),
                  Categorie = "Strategie",
                  Prioriteit = "Hoog",
                  Status = "Bezig",
                  ProjectId = 4
                },
                new
                {
                  Id = 24,
                  Naam = "Proof-of-concept opmaken",
                  TaakStartDatum = new DateTime(2021, 06, 01),
                  TaakEindDatum = new DateTime(2021, 06, 30),
                  Categorie = "Development",
                  Prioriteit = "Medium",
                  Status = "Open",
                  ProjectId = 4
                },
                new
                {
                  Id = 25,
                  Naam = "Proof-of-concept testen",
                  TaakStartDatum = new DateTime(2021, 07, 01),
                  TaakEindDatum = new DateTime(2021, 07, 15),
                  Categorie = "Testing",
                  Prioriteit = "Medium",
                  Status = "Open",
                  ProjectId = 4
                },
                new
                {
                  Id = 26,
                  Naam = "Finale code van de app schrijven",
                  TaakStartDatum = new DateTime(2021, 07, 16),
                  TaakEindDatum = new DateTime(2021, 07, 31),
                  Categorie = "Development",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 4
                },
                new
                {
                  Id = 27,
                  Naam = "Finaal app testen",
                  TaakStartDatum = new DateTime(2021, 08, 01),
                  TaakEindDatum = new DateTime(2021, 08, 15),
                  Categorie = "Testing",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 4
                },
                new
                {
                  Id = 28,
                  Naam = "Werkinstructies schrijven",
                  TaakStartDatum = new DateTime(2021, 08, 16),
                  TaakEindDatum = new DateTime(2021, 08, 31),
                  Categorie = "Procedures",
                  Prioriteit = "Medium",
                  Status = "Open",
                  ProjectId = 4
                },
                new
                {
                  Id = 29,
                  Naam = "Interne trainings voorzien",
                  TaakStartDatum = new DateTime(2021, 09, 01),
                  TaakEindDatum = new DateTime(2021, 09, 05),
                  Categorie = "Training",
                  Prioriteit = "Medium",
                  Status = "Open",
                  ProjectId = 4
                },
                new
                {
                  Id = 30,
                  Naam = "Werknemers begeleiden bij go live",
                  TaakStartDatum = new DateTime(2021, 09, 06),
                  TaakEindDatum = new DateTime(2021, 09, 15),
                  Categorie = "Begeleiding",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 4
                },
                new
                {
                  Id = 31,
                  Naam = "Enquêtes onder werknemers verdelen",
                  TaakStartDatum = new DateTime(2021, 09, 15),
                  TaakEindDatum = new DateTime(2021, 09, 18),
                  Categorie = "Feedback",
                  Prioriteit = "Medium",
                  Status = "Open",
                  ProjectId = 4
                },
                new
                {
                  Id = 32,
                  Naam = "Tevredenheidsscores analyseren",
                  TaakStartDatum = new DateTime(2021, 09, 19),
                  TaakEindDatum = new DateTime(2021, 09, 30),
                  Categorie = "Feedback",
                  Prioriteit = "Medium",
                  Status = "Open",
                  ProjectId = 4
                },
                // Dummy taken project 5
                new
                {
                  Id = 33,
                  Naam = "Sleutelfactoren verkoop onderscheiden",
                  TaakStartDatum = new DateTime(2021, 07, 17),
                  TaakEindDatum = new DateTime(2021, 07, 31),
                  Categorie = "Strategie",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 5
                },
                new
                {
                  Id = 34,
                  Naam = "Enquêtevragen opstellen",
                  TaakStartDatum = new DateTime(2021, 08, 01),
                  TaakEindDatum = new DateTime(2021, 08, 15),
                  Categorie = "Feedback",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 5
                },
                new
                {
                  Id = 35,
                  Naam = "Ethische Commissie aanschrijven",
                  TaakStartDatum = new DateTime(2021, 08, 15),
                  TaakEindDatum = new DateTime(2021, 08, 31),
                  Categorie = "Wetgeving",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 5
                },
                new
                {
                  Id = 36,
                  Naam = "Enquêtes verdelen onder gebruikers",
                  TaakStartDatum = new DateTime(2021, 09, 01),
                  TaakEindDatum = new DateTime(2021, 09, 05),
                  Categorie = "Feedback",
                  Prioriteit = "Medium",
                  Status = "Open",
                  ProjectId = 5
                },
                new
                {
                  Id = 37,
                  Naam = "Resultaten kwantitatief analyseren",
                  TaakStartDatum = new DateTime(2021, 09, 06),
                  TaakEindDatum = new DateTime(2021, 09, 15),
                  Categorie = "Analyse",
                  Prioriteit = "Medium",
                  Status = "Open",
                  ProjectId = 5
                },
                new
                {
                  Id = 38,
                  Naam = "Focusgroepen organiseren",
                  TaakStartDatum = new DateTime(2021, 09, 16),
                  TaakEindDatum = new DateTime(2021, 09, 30),
                  Categorie = "Feedback",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 5
                },
                new
                {
                  Id = 39,
                  Naam = "Resultaten kwalitatief analyseren",
                  TaakStartDatum = new DateTime(2021, 09, 30),
                  TaakEindDatum = new DateTime(2021, 10, 31),
                  Categorie = "Analyse",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 5
                },
                // Dummy taken project 6
                new
                {
                  Id = 40,
                  Naam = "Menukaart aan collega’s bezorgen",
                  TaakStartDatum = new DateTime(2021, 08, 01),
                  TaakEindDatum = new DateTime(2021, 08, 05),
                  Categorie = "Administratie",
                  Prioriteit = "Laag",
                  Status = "Open",
                  ProjectId = 6
                },
                new
                {
                  Id = 41,
                  Naam = "Bestellingen noteren",
                  TaakStartDatum = new DateTime(2021, 08, 06),
                  TaakEindDatum = new DateTime(2021, 08, 10),
                  Categorie = "Administratie",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 6
                },
                new
                {
                  Id = 42,
                  Naam = "Bestellingen finaal doorgeven",
                  TaakStartDatum = new DateTime(2021, 08, 11),
                  TaakEindDatum = new DateTime(2021, 08, 14),
                  Categorie = "Administratie",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 6
                },
                new
                {
                  Id = 43,
                  Naam = "Lunch verdelen",
                  TaakStartDatum = new DateTime(2021, 08, 15),
                  TaakEindDatum = new DateTime(2021, 08, 15),
                  Categorie = "Administratie",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 6
                },
                // Dummy taken project 7
                new
                {
                  Id = 44,
                  Naam = "Ideeën verzamelen voor klinische testunit",
                  TaakStartDatum = new DateTime(2021, 02, 07),
                  TaakEindDatum = new DateTime(2021, 02, 28),
                  Categorie = "Onderzoek",
                  Prioriteit = "Medium",
                  Status = "Voltooid",
                  ProjectId = 7
                },
                new
                {
                  Id = 45,
                  Naam = "Ethische Commissie aanschrijven",
                  TaakStartDatum = new DateTime(2021, 03, 01),
                  TaakEindDatum = new DateTime(2021, 03, 05),
                  Categorie = "Wetgeving",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 7
                },
                new
                {
                  Id = 46,
                  Naam = "Adviezen verwerken",
                  TaakStartDatum = new DateTime(2021, 03, 06),
                  TaakEindDatum = new DateTime(2021, 03, 31),
                  Categorie = "Wetgeving",
                  Prioriteit = "Medium",
                  Status = "Voltooid",
                  ProjectId = 7
                },
                new
                {
                  Id = 47,
                  Naam = "Functionele analyse opmaken",
                  TaakStartDatum = new DateTime(2021, 04, 01),
                  TaakEindDatum = new DateTime(2021, 04, 30),
                  Categorie = "Strategie",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 7
                },
                new
                {
                  Id = 48,
                  Naam = "Procedures uitwerken",
                  TaakStartDatum = new DateTime(2021, 05, 01),
                  TaakEindDatum = new DateTime(2021, 05, 10),
                  Categorie = "Procedures",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 7
                },
                new
                {
                  Id = 49,
                  Naam = "Juridische departement overtuigen",
                  TaakStartDatum = new DateTime(2021, 05, 11),
                  TaakEindDatum = new DateTime(2021, 05, 20),
                  Categorie = "Wetgeving",
                  Prioriteit = "Hoog",
                  Status = "Hangende",
                  ProjectId = 7
                },
                new
                {
                  Id = 50,
                  Naam = "Ethische Commissie overtuigen",
                  TaakStartDatum = new DateTime(2021, 05, 21),
                  TaakEindDatum = new DateTime(2021, 05, 31),
                  Categorie = "Wetgeving",
                  Prioriteit = "Hoog",
                  Status = "Bezig",
                  ProjectId = 7
                },
                new
                {
                  Id = 51,
                  Naam = "Facebook pagina lanceren",
                  TaakStartDatum = new DateTime(2021, 06, 01),
                  TaakEindDatum = new DateTime(2021, 06, 07),
                  Categorie = "Go live",
                  Prioriteit = "Hoog",
                  Status = "Hangende",
                  ProjectId = 7
                },
                new
                {
                  Id = 52,
                  Naam = "Boodschappen monitoren",
                  TaakStartDatum = new DateTime(2021, 06, 08),
                  TaakEindDatum = new DateTime(2021, 06, 10),
                  Categorie = "Monitoring",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 7
                },
                // Dummy taken project 8
                new
                {
                  Id = 53,
                  Naam = "Meetplan opstellen",
                  TaakStartDatum = new DateTime(2021, 04, 10),
                  TaakEindDatum = new DateTime(2021, 04, 13),
                  Categorie = "Strategie",
                  Prioriteit = "Laag",
                  Status = "Voltooid",
                  ProjectId = 8
                },
                new
                {
                  Id = 54,
                  Naam = "Functionele analyse opmaken",
                  TaakStartDatum = new DateTime(2021, 04, 14),
                  TaakEindDatum = new DateTime(2021, 04, 17),
                  Categorie = "Strategie",
                  Prioriteit = "Laag",
                  Status = "Voltooid",
                  ProjectId = 8
                },
                new
                {
                  Id = 55,
                  Naam = "Development team briefen",
                  TaakStartDatum = new DateTime(2021, 04, 18),
                  TaakEindDatum = new DateTime(2021, 04, 18),
                  Categorie = "Briefing",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 8
                },
                new
                {
                  Id = 56,
                  Naam = "Events in Google Tag Manager instellen",
                  TaakStartDatum = new DateTime(2021, 04, 19),
                  TaakEindDatum = new DateTime(2021, 04, 20),
                  Categorie = "Configuratie",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 8
                },
                new
                {
                  Id = 57,
                  Naam = "Events testen",
                  TaakStartDatum = new DateTime(2021, 04, 21),
                  TaakEindDatum = new DateTime(2021, 04, 21),
                  Categorie = "Testing",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 8
                },
                // Dummy taken project 9
                new
                {
                  Id = 58,
                  Naam = "Meetplan opstellen",
                  TaakStartDatum = new DateTime(2021, 02, 01),
                  TaakEindDatum = new DateTime(2021, 02, 05),
                  Categorie = "Strategie",
                  Prioriteit = "Medium",
                  Status = "Voltooid",
                  ProjectId = 9
                },
                new
                {
                  Id = 59,
                  Naam = "Database mapping analyseren",
                  TaakStartDatum = new DateTime(2021, 02, 06),
                  TaakEindDatum = new DateTime(2021, 02, 22),
                  Categorie = "Analyse",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 9
                },
                new
                {
                  Id = 60,
                  Naam = "Mock-up dashboard uitwerken",
                  TaakStartDatum = new DateTime(2021, 02, 23),
                  TaakEindDatum = new DateTime(2021, 02, 28),
                  Categorie = "Dashboard",
                  Prioriteit = "Laag",
                  Status = "Voltooid",
                  ProjectId = 9
                },
                new
                {
                  Id = 61,
                  Naam = "Databronnen integreren",
                  TaakStartDatum = new DateTime(2021, 03, 01),
                  TaakEindDatum = new DateTime(2021, 03, 05),
                  Categorie = "Integratie",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 9
                },
                new
                {
                  Id = 62,
                  Naam = "Lambda scripts schrijven (AWS)",
                  TaakStartDatum = new DateTime(2021, 03, 06),
                  TaakEindDatum = new DateTime(2021, 03, 08),
                  Categorie = "Development",
                  Prioriteit = "Medium",
                  Status = "Voltooid",
                  ProjectId = 9
                },
                new
                {
                  Id = 63,
                  Naam = "Automatisatie voorzien",
                  TaakStartDatum = new DateTime(2021, 03, 09),
                  TaakEindDatum = new DateTime(2021, 03, 10),
                  Categorie = "Development",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 9
                },
                new
                {
                  Id = 64,
                  Naam = "Dashboard opmaken",
                  TaakStartDatum = new DateTime(2021, 03, 11),
                  TaakEindDatum = new DateTime(2021, 03, 14),
                  Categorie = "Dashboard",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 9
                },
                new
                {
                  Id = 65,
                  Naam = "Cijfers nakijken",
                  TaakStartDatum = new DateTime(2021, 03, 15),
                  TaakEindDatum = new DateTime(2021, 03, 15),
                  Categorie = "Testing",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 9
                },
                // Dummy taken project 10
                new
                {
                  Id = 66,
                  Naam = "Vacature uitschrijven",
                  TaakStartDatum = new DateTime(2021, 05, 01),
                  TaakEindDatum = new DateTime(2021, 05, 15),
                  Categorie = "Vacature",
                  Prioriteit = "Hoog",
                  Status = "Voltooid",
                  ProjectId = 10
                },
                new
                {
                  Id = 67,
                  Naam = "Respons analyseren",
                  TaakStartDatum = new DateTime(2021, 05, 15),
                  TaakEindDatum = new DateTime(2021, 05, 20),
                  Categorie = "Vacature",
                  Prioriteit = "Medium",
                  Status = "Voltooid",
                  ProjectId = 10
                },
                new
                {
                  Id = 68,
                  Naam = "Kandidaten selecteren",
                  TaakStartDatum = new DateTime(2021, 05, 21),
                  TaakEindDatum = new DateTime(2021, 05, 31),
                  Categorie = "Vacature",
                  Prioriteit = "Hoog",
                  Status = "Bezig",
                  ProjectId = 10
                },
                new
                {
                  Id = 69,
                  Naam = "Kandidaten uitnodigen",
                  TaakStartDatum = new DateTime(2021, 06, 01),
                  TaakEindDatum = new DateTime(2021, 06, 10),
                  Categorie = "Vacature",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 10
                },
                new
                {
                  Id = 70,
                  Naam = "Contract opmaken en onboarding",
                  TaakStartDatum = new DateTime(2021, 06, 11),
                  TaakEindDatum = new DateTime(2021, 06, 15),
                  Categorie = "Vacature",
                  Prioriteit = "Hoog",
                  Status = "Open",
                  ProjectId = 10
                }
            );*/
        }
    public DbSet<Project> Projects { get; set; }
    // public DbSet<Customer> Customers { get; set; }
  }
}
