using Godot;

public partial class ButtonArea : Area2D
{
	[Export] private TextureRect Image;
	[Export] private string NextScenePath = "res://introductiontosafetyequipment.tscn"; // Example: "res://Scenes/MainMenu.tscn"

	private Color _normalColor = new Color(1, 1, 1);
	private Color _hoverColor = new Color(1.0f, 0.84f, 0);

	public override void _Ready()
	{
		MouseEntered += OnMouseEntered;
		MouseExited += OnMouseExited;
		InputEvent += OnInputEvent; // Connect mouse click event
	}

	private void OnMouseEntered() => Image.Modulate = _hoverColor;
	private void OnMouseExited() => Image.Modulate = _normalColor;

	private void OnInputEvent(Node viewport, InputEvent @event, long shapeIdx)
	{
		if (@event is InputEventMouseButton mouseEvent && mouseEvent.Pressed && mouseEvent.ButtonIndex == MouseButton.Left)
		{
			if (!string.IsNullOrEmpty(NextScenePath))
				GetTree().ChangeSceneToFile(NextScenePath);
			else
				GD.PrintErr("NextScenePath is not assigned.");
		}
	}
}
