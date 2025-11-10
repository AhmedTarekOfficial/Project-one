
using Godot;
using System;

public partial class Exitgamebutton : Button
{
	public override void _Ready()
	{
		this.Pressed += OnButtonPressed;
	}

	private void OnButtonPressed()
	{
		GetTree().Quit(); // This closes the game
	}
}
