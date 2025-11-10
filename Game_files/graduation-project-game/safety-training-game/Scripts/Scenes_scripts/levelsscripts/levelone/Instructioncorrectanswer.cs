using Godot;
using System;

public partial class Instructioncorrectanswer : Button
{
	public override void _Ready()
	{
		
		Pressed += OnButtonPressed;
	}

	private void OnButtonPressed()
	{
		GetTree().ChangeSceneToFile("res://Scenes/Levels/MachinesDistruction.tscn");
	}
}
