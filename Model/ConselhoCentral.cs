using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel;

namespace Model;

[Table("conselhoCentral")]
public class ConselhoCentral
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long Id { get; set; }

    [Required]
    [MaxLength(100)]
    public string Nome { get; set; }

    public ICollection<ConselhoParticular> ConselhosParticulares { get; set; } = new List<ConselhoParticular>();
}
