using Godot;
using System;

public partial class StartGameButton : Button
{
	public override void _Ready()
	{
		Pressed += OnButtonPressed;
	}

	private void OnButtonPressed()
	{
		GD.Print("Button pressed!");
		GetTree().ChangeSceneToFile("res://Scenes/Levels/Distruction-of-co-worker.tscn");
	}
}
