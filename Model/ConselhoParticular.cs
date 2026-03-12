using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel;

namespace Model;

[Table("conselhoParticular")]
public class ConselhoParticular
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long Id { get; set; }

    [Required]
    [MaxLength(100)]
    public string Nome { get; set; }

    [Required]
    public long ConselhoCentralId { get; set; }

    [ForeignKey(nameof(ConselhoCentralId))]
    public ConselhoCentral ConselhoCentral { get; set; }
    public ICollection<Conferencia> Conferencias { get; set; } = new List<Conferencia>();
}