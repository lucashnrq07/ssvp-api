using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Model;

[Table("itens")]
public class Item
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long Id { get; set; }

    [Required]
    [Column("order_id")]
    public long BolsaId { get; set; }

    [ForeignKey(nameof(BolsaId))]
    public Bolsa Bolsa { get; set; }

    [Required]
    [Column("product_id")]
    public long ProdutoId { get; set; }

    [ForeignKey(nameof(ProdutoId))]
    public Produto Produto { get; set; }

    [Required]
    public int Quantidade { get; set; }

    [Required]
    [Column(TypeName = "decimal(10,2)")]
    public decimal Preco { get; set; }

    [Required]
    [Column(TypeName = "decimal(10,2)")]
    public decimal Total { get; set; }
}