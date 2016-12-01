import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main
{
	@SuppressWarnings("serial")
	public static class Textbox extends JPanel
	{
		//The actual textbox
		private JLabel text;
		
		//The time when the textbox will close
		private long closeTime;
		
		//Create the textbox
		public Textbox()
		{
			super(); //initialize
			text = new JLabel();
			text.setPreferredSize(new Dimension(300, 200));
			this.add(text); //add the textbox to the panel
			//set closeTime to the current time
			closeTime = System.currentTimeMillis();
		}
		
		public void setText(String text)
		{
			//calculate the number of words
			int words = text.split(" |(<br />)").length;
			//set close time to (words * 250) ms from now
			closeTime = System.currentTimeMillis() + (words * 250);
			//update the textbox's text
			this.text.setText(text);
		}
		
		public void closeText()
		{
			//blank the text
			text.setText("");
		}
		
		public void update()
		{
			//If its after the time to close the box, close it
			if(System.currentTimeMillis() > closeTime)
			{
				//System.out.println("close");
				closeText();
			}
		}
	}
	
	@SuppressWarnings("serial")
	public static class TextButton extends JButton implements ActionListener
	{
		//The text to update the textbox to
		private String text;
		//The textbox to update
		private Textbox source;
		
		public TextButton(String label, String text, Textbox source)
		{
			super(label);
			this.text = text;
			this.source = source;
			this.addActionListener(this);
			this.setPreferredSize(new Dimension(80, 30));
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			//When clicked on, update the text
			source.setText(text);
		}
	}
	
	public static void main(String[] args)
	{
		//setup for the JFrame
		JFrame frame = new JFrame("Textbox");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		//make the textbox
		Textbox tb = new Textbox();
		frame.getContentPane().add(tb);
		//add buttons
		frame.getContentPane().add(new TextButton("first", "this is the first text", tb));
		frame.getContentPane().add(new TextButton("second", "this is the second text", tb));
		frame.getContentPane().add(new TextButton("third", "<html>this is the third text<br />more text on another line to<br />make it last longer</html>", tb));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		while(true)
		{
			//update the textbox
			tb.update();
			Thread.yield();
		}
	}

}
