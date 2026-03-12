using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel;
using System.Globalization;

namespace Model;

[Table("produtos")]
public class Produto
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long Id { get; set; }

    [Required]
    public string Nome { get; set; }

    [Required]
    [Column(TypeName = "decimal(10,2)")]
    public decimal preco { get; set; }

    [Required]
    public bool Ativo { get; set; }
}