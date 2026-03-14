using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Model;

[Table("vicentinos")]
public class Vicentino
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long Id { get; set; }

    [Required]
    public long ConselhoParticularId { get; set; }

    [ForeignKey(nameof(ConselhoParticularId))]
    public ConselhoParticular ConselhoParticular { get; set; }

    [Required]
    public string Senha { get; set; }
}