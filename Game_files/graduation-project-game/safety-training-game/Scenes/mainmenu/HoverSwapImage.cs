using Godot;
using System;

public partial class HoverSwapImage : Area2D
{
	private TextureRect defaultShape;
	private TextureRect hoverShape;

	public override void _Ready()
{
	GD.Print("Ready: HoverSwapImage initialized");

	MouseEntered += () => GD.Print("Mouse entered area");
	MouseExited += () => GD.Print("Mouse exited area");
}

	private void OnMouseEntered()
	{
		defaultShape.Visible = false;
		hoverShape.Visible = true;
		GD.Print("Mouse entered");
	}

	private void OnMouseExited()
	{
		defaultShape.Visible = true;
		hoverShape.Visible = false;
		GD.Print("Mouse exited");
	}
}
