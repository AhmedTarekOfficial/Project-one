using Godot;
using System.Collections.Generic;
using System.Threading.Tasks;

public partial class Paneltextholder: Panel
{
	private RichTextLabel _label;
	private List<string> _lines = new List<string>
	{
		"جميل! لابس الخوذة، بس لسه ناقصك شوية حاجات قبل ما تدخل",
		"اختار النظارات، وبعدين خُد حذاء الأمان. دول أهم حاجتين في المرحلة دي",
		"القفازات خليهالك للمراحل اللي فيها شغل مباشر بالمعدات",
		"تمام كده يا بطل، جاهز تدخل المصنع بأمان."
	};

	private float _typingSpeed = 0.05f; // الوقت بين كل حرف
	private bool _showCursor = true;
	private bool _isTyping = false;
	private string _currentFullText = "";
	private bool _stopBlink = false;

	public override void _Ready()
	{
		_label = GetNode<RichTextLabel>("instructiongame");
		GD.Print("Label found: ", _label != null);

		_label.Clear();
		_label.CustomMinimumSize = new Vector2(600, 200);
		_label.Position = new Vector2(5, 5);

		// دعم اللغة العربية
		_label.TextDirection = Control.TextDirection.Rtl;
		_label.BbcodeEnabled = false;
		_label.ScrollActive = false;

		// ابدأ الكتابة
		_ = StartTyping();
	}

	private async Task StartTyping()
	{
		_isTyping = true;
		_stopBlink = false;
		_ = BlinkCursor();

		foreach (string line in _lines)
		{
			await TypeLine(line);
			_currentFullText += "\n";
			await ToSignal(GetTree().CreateTimer(0.7f), "timeout"); // فاصل بين السطور
		}

		_isTyping = false;
		_stopBlink = true;
		_label.Text = _currentFullText.TrimEnd('\n');

		ShowButtonGlow();
	}

	private async Task TypeLine(string line)
	{
		foreach (char c in line)
		{
			_currentFullText += c;
			_label.Text = _currentFullText + (_showCursor ? "|" : "");
			await ToSignal(GetTree().CreateTimer(_typingSpeed), "timeout");
		}
	}

	private async Task BlinkCursor()
	{
		while (!_stopBlink)
		{
			_showCursor = !_showCursor;
			_label.Text = _currentFullText + (_showCursor ? "|" : "");
			await ToSignal(GetTree().CreateTimer(0.5f), "timeout");
		}

		// بعد ما تخلص الكتابة، اختفي المؤشر
		_showCursor = false;
		_label.Text = _currentFullText;
	}

	private void ShowButtonGlow()
	{
		// Placeholder for your button glow logic
		GD.Print("Button glow triggered.");
	}
}
