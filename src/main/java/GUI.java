import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel input;
	private JPanel output;

	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow][grow]"));

		output = new IOWindow(false);
		contentPane.add(output, "cell 0 0,grow");

		input = new IOWindow(true);
		contentPane.add(input, "cell 0 1,grow");
		this.setVisible(true);
	}

	String getInput() {
		String inp = ((IOWindow) input).lastInput;
		((IOWindow) input).lastInput = "";
		try {
			if(inp.substring(0, 1).equals("\n"))
				inp = inp.substring(1);
		} catch (Exception e) {}
		return inp;
	}

	void print(String s) {
		((IOWindow) output).appendOutput(s);
	}

}
