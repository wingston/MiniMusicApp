import javax.sound.midi.*;
import java.awt.*;
import javax.swing.*;

public class Music {
	
	JFrame f = new JFrame("love u");
	MyDrawPanel ml;
	
	public static void main(String[] args){
		Music mini = new Music();
		mini.go();
		
	}
	public void setUpGui(){
		ml=new MyDrawPanel();
		f.setContentPane(ml);
		f.setBounds(100,100,300,300);
		f.setVisible(true);
		
	}
	public void go(){
		setUpGui();
		try{
		Sequencer sequencer = MidiSystem.getSequencer();
		sequencer.open();
		sequencer.addControllerEventListener(ml, new int[] {127});
		Sequence seq = new Sequence(Sequence.PPQ,4);
		Track track = seq.createTrack();
		
		for(int i=0;i<60;i+=4){
			int r=(int)(Math.random()*50)+50;
			track.add(makeEvent(144,1,r,100,i));
			track.add(makeEvent(176,1,127,0,i));
			track.add(makeEvent(128,1,r,100,i+2));
			
		}
		
		sequencer.setSequence(seq);
		sequencer.setTempoInBPM(120);
		sequencer.start();
		
	}catch(Exception ex){ex.printStackTrace();}
		
		
	}
	public MidiEvent makeEvent(int comd,int chan, int one, int two, int tick){
		MidiEvent event = null;
		try{
			ShortMessage a = new ShortMessage();
			a.setMessage(comd,chan,one,two);
			event=new MidiEvent(a,tick);
			
		}catch(Exception ex){}
		return event;
	}
	class MyDrawPanel extends JPanel implements ControllerEventListener{
		boolean msg = false;
		
		public void controlChange(ShortMessage event){
			msg=true;
			
			repaint();
		}
		public void paintComponent(Graphics g){
			if(msg){
				Graphics2D g2 = (Graphics2D)g;
				
				int r = (int)(Math.random()*255);
				int gr = (int)(Math.random()*255);
				int b = (int)(Math.random()*255);
				
				g.setColor(new Color(r,gr,b));
				
				int height=(int)((Math.random()*120)+10);
				int width= (int)((Math.random()*120)+10);
				
				int x=(int)((Math.random()*40));
				int y= (int)((Math.random()*40));
				
				g.fillRect(x, y, width, height);
				
				
				msg=false;
				
			}
		}
	}

}