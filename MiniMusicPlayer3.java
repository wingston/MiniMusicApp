import javax.sound.midi.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class MiniMusicPlayer3 {
	
	JFrame f = new JFrame("My First Music Video");
	MyDrawPanel ml;
	
	public static void main(String[] args){
		MiniMusicPlayer3 mini = new MiniMusicPlayer3();
		mini.go();
	}
	
	public void setUpGui(){
		ml = new MyDrawPanel();
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
			
			int r = 0;
			for(int i = 0; i < 60; i++){
				r=(int)((Math.random()*50)+50);
				track.add(makeEvent(144,1,r,100,i));
				track.add(makeEvent(176,1,127,0,i));
				track.add(makeEvent(128,1,r,100,i+1));
			}
			
			sequencer.setSequence(seq);
			sequencer.start();
			sequencer.setTempoInBPM(120);
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
	
	boolean msg=false;
	
	public void controlChange(ShortMessage event){
		msg=true;
		repaint();
		
	}
	public void paintComponent(Graphics g){
		if(msg){
			
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

