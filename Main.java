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
		
		//Number of words "left" to display
		private int words;
		
		//Whether a delay is already counting down words
		private boolean counting;
		
		//Create the textbox
		public Textbox()
		{
			super(); //initialize
			text = new JLabel();
			text.setPreferredSize(new Dimension(300, 200));
			this.add(text); //add the textbox to the panel
			counting = false;
		}
		
		public void setText(String in)
		{
			//calculate the number of words
			words = in.split(" |(<br />)").length;
			new Thread(new Runnable()
			{
				//Whether this instance counts down
				private boolean count;
				
				@Override
				public void run()
				{
					//If the textbox isn't already counting, this thread does.
					if(!Textbox.this.counting) { count = true; Textbox.this.counting = true; }
					else { count = false; }
					
					//update the textbox's text
					text.setText(in);
					(Textbox.this).repaint();
					//Loop while there are words "left"
					while(words>0)
					{
						try
						{
							Thread.sleep(250);
						}
						catch (InterruptedException e){}
						
						//If the thread is counting, decrement the counter
						if(count) { words--; }
					}
					//Stops counting
					if(count) { Textbox.this.counting = false; }
					closeText();
				}
			}).start();
			
		}
		
		public void closeText()
		{
			//blank the text
			text.setText("");
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
	}

}
