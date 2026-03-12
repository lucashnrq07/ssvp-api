using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel;

namespace Model;

[Table("conferencia")]
public class Conferencia
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long Id { get; set; }

    [Required]
    [MaxLength(100)]
    public string Nome { get; set; }

    [Required]
    public long ConselhoParticularId { get; set; }

    [ForeignKey(nameof(ConselhoParticularId))]
    public ConselhoParticular ConselhoParticular { get; set; }

    public ICollection<Assistido> Assistidos { get; set; } = new List<Assistido>();
}