using Godot;
using System;

public partial class BackButton : Button
{
	public override void _Ready()
	{
		
		Pressed += OnButtonPressed;
	}

	private void OnButtonPressed()
	{
		GetTree().ChangeSceneToFile("res://Scenes/Main_menu.tscn");
	}
}
