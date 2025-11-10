using Godot;
using System;

public partial class MachinesDistruction : Node2D
{
	// References to your nodes
	private RichTextLabel textLabel;
	private TextureRect glowImage;
	
	// Your dialog text (multiple lines)
	private string[] dialogLines = new string[]
	{
		"أنت دلوقتي في نص المصنع، وقدامك تلات ماكينات.",
		"كل ماكينة ليها دور، لكن النهارده تدريبك هيكون على ماكينة قطع الكابلات الكهربائية.",
		"قبل ما تبدأ، خليك جاهز.",
		"راجع خطواتك، ركّز في التفاصيل، وتخيل إنك في موقف حقيقي.",
		"هنا مش المطلوب تشتغل بسرعة… المطلوب تشتغل بأمان.",
		"كل قرار هتاخده جوا التدريب دا ممكن يفرق بين عامل واعي، وعامل متهوّر.",
		"فكر، استعد، ولما تبقى متأكد إنك جاهز… اضغط على الماكينة وابدأ تدريبك الأول. ⚙️"
	};

	// Text animation speed (seconds per character)
	private float textSpeed = 0.05f;
	// Delay between lines (seconds)
	private float lineDelay = 0.3f;
	
	public override void _Ready()
	{
		// Get node references
		textLabel = GetNode<RichTextLabel>("instructionslabel");
		glowImage = GetNode<TextureRect>("Firstmachine/firstmachine");
		
		// Keep the image visible but remove glow
		glowImage.Modulate = new Color(1, 1, 1, 1); // fully visible
		glowImage.SelfModulate = new Color(1, 1, 1, 1); // ensure it's visible
		glowImage.Material = null; // clear any special material if glowing before
		
		// Clear text
		textLabel.Text = "";
		
		// Start typing
		ShowText();
	}
	
	private async void ShowText()
	{
		textLabel.Text = "";
		int linesPerPage = 4;
		
		for (int lineIndex = 0; lineIndex < dialogLines.Length; lineIndex++)
		{
			string line = dialogLines[lineIndex];
			
			if (lineIndex > 0 && lineIndex % linesPerPage == 0)
			{
				textLabel.Text = "";
				await ToSignal(GetTree().CreateTimer(lineDelay), SceneTreeTimer.SignalName.Timeout);
			}
			
			for (int i = 0; i <= line.Length; i++)
			{
				string currentText = "";
				int pageStartIndex = (lineIndex / linesPerPage) * linesPerPage;
				
				for (int j = pageStartIndex; j < lineIndex; j++)
				{
					currentText += dialogLines[j] + "\n";
				}
				
				currentText += line.Substring(0, i);
				textLabel.Text = currentText;

				await ToSignal(GetTree().CreateTimer(textSpeed), SceneTreeTimer.SignalName.Timeout);
			}
			
			if (lineIndex < dialogLines.Length - 1)
			{
				textLabel.Text += "\n";
				await ToSignal(GetTree().CreateTimer(lineDelay), SceneTreeTimer.SignalName.Timeout);
			}
		}

		// Start glow effect only after writing finishes
		GlowYellow();
	}
	
	private void GlowYellow()
	{
		Tween tween = CreateTween();
		
		// Yellow glow pulse
		tween.TweenProperty(glowImage, "modulate", new Color(1, 1, 0, 1), 0.5);
		tween.SetLoops();
		tween.TweenProperty(glowImage, "modulate", new Color(1, 1, 0, 0.5f), 0.8);
		tween.TweenProperty(glowImage, "modulate", new Color(1, 1, 0, 1), 0.8);
	}
}
