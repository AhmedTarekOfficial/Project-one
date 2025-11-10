using Godot;
using System;

public partial class BackButtonArea : Area2D
{
	private TextureRect text;

	public override void _Ready()
	{
		text = GetNode<TextureRect>("TextureRect2");

		MouseEntered += OnMouseEntered;
		MouseExited += OnMouseExited;
	}

	private void OnMouseEntered()
	{
		text.Modulate = new Color("B32000"); 	}

	private void OnMouseExited()
	{
		text.Modulate = new Color(1, 1, 1, 1); // Back to normal
		
	}
}
