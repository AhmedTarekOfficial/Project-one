using Godot;
using System;

public partial class CoWorkerInFrontOfFactory : Node2D
{
	// References to your nodes
	private RichTextLabel textLabel;
	private TextureRect glowImage;
	
	// Your dialog text (multiple lines)
	private string[] dialogLines = new string[]
	{
		"السلامة مش في اللبس ولا في اللافتات، السلامة تبدأ من وعيك.",
		"خليك دايمًا صاحي، لأن غلطة صغيرة ممكن تِكلف كتير.",
		"راقب اللي حواليك، واحترم صوت الماكينات كأنه تحذير.",
		"ما تستعجلش، السرعة عمرها ما كانت دليل شطارة." ,
		"والأهم… ساعد زميلك قبل ما يحتاج المساعدة." ,
		"دلوقتي، خد نفس، وافتح الباب. الرحلة لسه في أولها."
	};
	
	// Text animation speed (seconds per character)
	private float textSpeed = 0.05f;
	// Delay between lines (seconds)
	private float lineDelay = 0.5f;
	
	public override void _Ready()
	{
		// Get node references
		textLabel = GetNode<RichTextLabel>("TextLabel");
		glowImage = GetNode<TextureRect>("Contbutton/GlowImage");
		
		// Hide the glow effect initially
		glowImage.Modulate = new Color(1, 1, 1, 0);
		
		// Clear the text initially
		textLabel.Text = "";
		
		// Start the text animation
		ShowText();
	}
	
	private async void ShowText()
	{
		textLabel.Text = "";
		int linesPerPage = 3;
		
		// Loop through each line
		for (int lineIndex = 0; lineIndex < dialogLines.Length; lineIndex++)
		{
			string line = dialogLines[lineIndex];
			
			// Check if we need to clear the text (every 3 lines)
			if (lineIndex > 0 && lineIndex % linesPerPage == 0)
			{
				textLabel.Text = "";
				// Optional: small delay after clearing
				await ToSignal(GetTree().CreateTimer(lineDelay), SceneTreeTimer.SignalName.Timeout);
			}
			
			// Animate current line appearing letter by letter
			for (int i = 0; i <= line.Length; i++)
			{
				// Build the complete text with lines in current page
				string currentText = "";
				int pageStartIndex = (lineIndex / linesPerPage) * linesPerPage;
				
				// Add all previous lines in current page
				for (int j = pageStartIndex; j < lineIndex; j++)
				{
					currentText += dialogLines[j] + "\n";
				}
				
				// Add current line being typed
				currentText += line.Substring(0, i);
				
				textLabel.Text = currentText;
				await ToSignal(GetTree().CreateTimer(textSpeed), SceneTreeTimer.SignalName.Timeout);
			}
			
			// Add newline after completing the line (except for the last line)
			if (lineIndex < dialogLines.Length - 1)
			{
				textLabel.Text += "\n";
				// Optional delay between lines
				await ToSignal(GetTree().CreateTimer(lineDelay), SceneTreeTimer.SignalName.Timeout);
			}
		}
		
		// Text finished, now glow the image
		GlowYellow();
	}
	
	private void GlowYellow()
	{
		// Create a tween for smooth glow animation
		Tween tween = CreateTween();
		
		// Fade in yellow glow
		tween.TweenProperty(glowImage, "modulate", new Color(1, 1, 0, 1), 0.5);
		
		// Optional: Make it pulse
		tween.SetLoops();
		tween.TweenProperty(glowImage, "modulate", new Color(1, 1, 0, 0.5f), 0.8);
		tween.TweenProperty(glowImage, "modulate", new Color(1, 1, 0, 1), 0.8);
	}
}
