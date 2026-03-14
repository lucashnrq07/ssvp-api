using Microsoft.EntityFrameworkCore;
using Model;

namespace Data;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options)
        : base(options)
    {
    }

    public DbSet<Vicentino> Vicentinos { get; set; }
    public DbSet<Produto> Produtos { get; set; }
    public DbSet<Item> Itens { get; set; }
    public DbSet<ConselhoCentral> ConselhosCentrais { get; set; }
    public DbSet<ConselhoParticular> ConselhosParticulares { get; set; }
    public DbSet<Conferencia> Conferencias { get; set; }
    public DbSet<Bolsa> Bolsas { get; set; }
    public DbSet<Assistido> Assistidos { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        // Enum como string
        modelBuilder.Entity<Bolsa>()
            .Property(b => b.Status)
            .HasConversion<string>();


        // Unique composto (igual Java)
        modelBuilder.Entity<ConselhoParticular>()
            .HasIndex(c => new { c.Nome, c.ConselhoCentralId })
            .IsUnique();


        // Cascade Item → Bolsa
        modelBuilder.Entity<Item>()
            .HasOne(i => i.Bolsa)
            .WithMany(b => b.Itens)
            .HasForeignKey(i => i.BolsaId)
            .OnDelete(DeleteBehavior.Cascade);


        // OneToOne Vicentino → ConselhoParticular
        modelBuilder.Entity<Vicentino>()
            .HasOne(v => v.ConselhoParticular)
            .WithOne()
            .HasForeignKey<Vicentino>(v => v.ConselhoParticularId);
    }
}