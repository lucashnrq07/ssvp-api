using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel;

namespace Model;

[Table("assistidos")]
public class Assistido
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long Id { get; set; }

    [Required]
    [MaxLength(100)]
    public string Nome { get; set;}

    [Required]
    [MaxLength(15)]
    public String Cpf { get; set; }

    [Required]
    [Column(TypeName = "decimal(10,2)")]
    public decimal Saldo { get; set; } = 0;

    [Required]
    public long ConferenciaId { get; set; }

    [ForeignKey(nameof(ConferenciaId))]
    public Conferencia Conferencia { get; set; }
}
