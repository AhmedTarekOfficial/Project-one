using Godot;
using System;

public partial class DistructionOfCoWorker : Node2D
{
	// References to your nodes
	private RichTextLabel textLabel;
	private TextureRect glowImage;
	
	// Your dialog text (multiple lines)
	private string[] dialogLines = new string[]
{
	"شايفك قاعد ووشك فيه قلق… ده طبيعي.",
	"كل عامل في أول مرة بيحس بالخوف قبل ما يلمس الماكينة.",
	"الصندوق اللي في إيدك فيه أدوات الأمان… الخوذة على راسك، تمام.",
	"بس لسه محتار تختار إيه؟ الجزمة؟ ولا النضارة؟ ولا القفاز؟",
	"متخفش، مفيش إجابة غلط هنا… في تدريبنا هتعرف إمتى تلبس كل حاجة وليه.",
	"خد نفس، وافتكر دايماً… الأمان مش ضعف، الأمان وعي.",
    "دلوقتي ركّز معايا، خطوة بخطوة، وهتعرف كل حاجة في وقتها."
};

	// Text animation speed (seconds per character)
	private float textSpeed = 0.05f;
	// Delay between lines (seconds)
	private float lineDelay = 0.5f;
	
	public override void _Ready()
	{
		// Get node references
		textLabel = GetNode<RichTextLabel>("instructionmessages");
		glowImage = GetNode<TextureRect>("ButtonArea/TextureRect");
		
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
		int linesPerPage = 2;
		
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
