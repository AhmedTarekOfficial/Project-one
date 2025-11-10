using Godot;

public partial class Settingbutoonstransatio : Area2D
{
	[Export] private TextureRect Image;

	private Color _normalColor = new Color(1, 1, 1);
	private Color _hoverColor = new Color(1.0f, 0.84f, 0);

	public override void _Ready()
	{
		MouseEntered += OnMouseEntered;
		MouseExited += OnMouseExited;
	}

	private void OnMouseEntered() => Image.Modulate = _hoverColor;
	private void OnMouseExited() => Image.Modulate = _normalColor;
}
