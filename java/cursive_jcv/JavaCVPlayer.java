package cursive_jcv;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;

import com.jme3.texture.Image;
import com.jme3.texture.Image.Format;
//import com.jme3.texture.plugins.AWTLoader;

public class JavaCVPlayer {

	FFmpegFrameGrabber grabber;
	Java2DFrameConverter converter = new Java2DFrameConverter();
	Frame frame;

	Image targetImage;
	AWTLoader awt = new AWTLoader();

	String filename;

	float fps;
	float currentTime = 0;

	float currentFrameTime = 0;
	float maxFrameTime;

	public boolean isPlaying = false;
	public boolean isPaused = false;
	public boolean isFlip = false;

	boolean readyForNextFrame = true;
	boolean isInPlay = false; // if true, then we're in the middle of the play()
								// method. to control threading.

	boolean isLoadInProcess = false; // is true when load requested, play stops
										// as soon as possible and does not play
										// until load completes

	public void setImage(Image image) { // just so if you will decide to change
		this.targetImage = image;
	}

	public void log(String msg){ 
		System.out.println(" =============== PLAYER: " + msg);
	}
	
	public Image loadFile(String f) throws Exception, InterruptedException {


		System.out.println("============= [a]");

		// abort if already reloading
		if (isLoadInProcess)
			return targetImage;

		System.out.println("============= [b]");

		isLoadInProcess = true; // now claim the play cycle to stop it
		
		// wait while play finishes
		while (isInPlay) {
			System.out.println("============= [c]");
			Thread.sleep(1);
		}

		System.out.println("============= [d]");

		stop();

		System.out.println("============= [e]");

		this.filename = f;
		if (!new java.io.File(f).exists()) {
			System.out.println("NEST.JAVA.JCVP: load file, not found " + f);
			targetImage = null;
			return null;
		}

		System.out.println("============= [f]");
		
		currentFrameTime = 0;
		currentTime = 0;
		grabber = new FFmpegFrameGrabber(f);
		System.out.println("============= [g]");
		grabber.start();
		System.out.println("============= [h]");
		System.out.println("NEST.JAVA.JCVP: load file, new fps =" + grabber.getFrameRate());
		fps = (float) grabber.getFrameRate();
		maxFrameTime = 1f / fps;
		frame = grabber.grab();
		System.out.println("============= [i]");
		System.out.println("NEST.JAVA.JCVP: widht and height =" + grabber.getImageWidth() + "  " + grabber.getImageHeight());
		this.targetImage = new Image(Format.BGR8, grabber.getImageWidth(), grabber.getImageHeight(), null);

		log("load [L] " + f);
		
		isLoadInProcess = false;
		System.out.println("============= [j] loading complete!");
		return targetImage;
	}

	public int getWidth() {
		if (grabber == null)
			return -1;
		return grabber.getImageWidth();
	}

	public int getHeight() {
		if (grabber == null)
			return -1;
		return grabber.getImageHeight();
	}

	public void start() {

	}

	public void pause() {
		isPaused = true;
	}

	public void play() {
		isPaused = false;
		isPlaying = true;
	}

	public void play(float tpf) throws Exception {
		
		if (isLoadInProcess)
			return;
		
		isInPlay = true;
		
		try {
			//System.out.println ("PLAYER: playing");
			if (isPlaying & !isPaused) {

				if (targetImage == null)
					return;

				currentFrameTime += tpf;
				currentTime += tpf;

				if (currentFrameTime > maxFrameTime) {

					while (currentFrameTime >= maxFrameTime) {
						currentFrameTime -= maxFrameTime;
					}

					while (frame.image == null || readyForNextFrame) {
						readyForNextFrame = false;
						frame = grabber.grab();

						if (frame == null) {
							stop();
							return;
						}
					}

					if (frame.image != null) {
						//System.out.println ("PLAYER: got frame");
						// System.out.println("NEST.JAVA.JCVP: -------------------- grabbed a frame!");
						awt.load(converter.convert(frame), targetImage, isFlip);
						// targetImage.setData(awt.load(converter.convert(frame),
						// isFlip).getData(0));
					}

					readyForNextFrame = true;
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			isInPlay = false;
		}
	}

	public void stop() throws Exception {
		if (grabber != null) {
			isPlaying = false;
			grabber.stop();
			grabber.release();
			grabber = null;
		}
	}
}
