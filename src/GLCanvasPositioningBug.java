import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

public class GLCanvasPositioningBug {
	public static JFrame createFrame(String title) {
		JFrame frame = new JFrame();
		frame.setTitle(title);
		frame.setSize(new Dimension(800, 600));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel canvasHolder = new JPanel(new BorderLayout());
		canvasHolder.setBackground(Color.RED);
		canvasHolder.setBorder(new EmptyBorder(50, 50, 50, 50));
		
		GLProfile profile = GLProfile.getGL4ES3();
		GLCanvas canvas = new GLCanvas(new GLCapabilities(profile));
		
		canvas.addGLEventListener(new GLEventListener() {
			@Override
			public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
				GL gl = drawable.getGL();
				gl.glViewport(0, 0, width, height);
			}
			
			@Override
			public void init(GLAutoDrawable drawable) {
			}
			
			@Override
			public void dispose(GLAutoDrawable drawable) {
			}
			
			@Override
			public void display(GLAutoDrawable drawable) {
				GL gl = drawable.getGL();
				gl.glClearColor(0, 0, 1, 1);
				gl.glClear(GL.GL_COLOR_BUFFER_BIT);
			}
		});
		
		JPanel canvasBorder = new JPanel(new BorderLayout());
		canvasBorder.setBorder(new LineBorder(Color.BLACK));
		canvasBorder.add(canvas, BorderLayout.CENTER);
		canvasHolder.add(canvasBorder, BorderLayout.CENTER);
		
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(canvasHolder, BorderLayout.CENTER);
		
		new javax.swing.Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (canvasHolder.getInsets().left == 50) {
					canvasHolder.setBorder(new EmptyBorder(100, 100, 100, 100));
				}
				else {
					canvasHolder.setBorder(new EmptyBorder(50, 50, 50, 50));
				}
			}
		}).start();	
		
		return frame;
	}

	public static void main(String[] args) {
		JFrame frame1 = createFrame("setVisible(true) on main thread");
		JFrame frame2 = createFrame("setVisible(true) on AWT Event Dispatch Thread");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int padding = 50;
		
		frame1.setSize((screenSize.width - padding * 3) / 2, screenSize.height / 2);
		frame2.setSize(frame1.getSize());
		
		frame1.setLocation(screenSize.width / 2 - frame1.getWidth() - padding / 2, screenSize.height / 2 - frame1.getHeight() / 2);
		frame2.setLocation(screenSize.width / 2 + padding / 2, screenSize.height / 2 - frame2.getHeight() / 2);

		frame1.setVisible(true);
		
		Timer timer = new Timer(7000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame2.setVisible(true);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
}
