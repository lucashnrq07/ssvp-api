using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Model.Enums;

namespace Model;

[Table("bolsas")]
public class Bolsa
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long Id { get; set; }

    [Required]
    public long AssistidoId { get; set; }

    [ForeignKey(nameof(AssistidoId))]
    public Assistido Assistido { get; set; }

    [Required]
    public DateTime Momento { get; set; }

    [Required]
    public Status Status { get; set; }

    [Required]
    [Column(TypeName = "decimal(10,2)")]
    public decimal Total { get; set; }

    public ICollection<Item> Itens { get; set; } = new List<Item>();
}