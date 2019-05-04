
//IMPORTS
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class ImageEditorGUI {

	private static JFrame frame = new JFrame("Image Editor GUI");
	private static JMenuItem open = new JMenuItem("Open...");
	private static JMenuItem save = new JMenuItem("Save");
	private static JMenuItem exit = new JMenuItem("Exit");
	private static JMenuItem restore = new JMenuItem("Restore to Original");
	private static JMenuItem hFlip = new JMenuItem("Horizontal Flip");
	private static JMenuItem vFlip = new JMenuItem("Vertical Flip");
	private static JMenuItem gray = new JMenuItem("Gray Scale");
	private static JMenuItem sepia = new JMenuItem("Sepia Tone");
	private static JMenuItem invert = new JMenuItem("Invert Color");
	private static JMenuItem blur = new JMenuItem("Gaussian Blur");
	private static JMenuItem bulge = new JMenuItem("Bulge Effect");
	private static JMenuItem rotate = new JMenuItem("Rotate 180 degrees");
	private static JPanel panel = new JPanel(new BorderLayout());
	private static JLabel original = new JLabel();
	private static BufferedImage image, copy;
	private static JFileChooser selectFile = new JFileChooser();
	private static File file;

	public static class Exit implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			frame.dispose(); // Closes the window
			System.exit(0); // Closes resources
		}
	}

	public static class OpenFile implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			// Finds the directory with the source code
			File directory = new File(System.getProperty("user.dir"));
			// Sets the file explorer to open the directory with the source code
			selectFile.setCurrentDirectory(directory);
			selectFile.setDialogTitle("Open");

			if (selectFile.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
				file = selectFile.getSelectedFile();
				try {
					image = ImageIO.read(file); // Image that will be edited in the program being given the image
					copy = ImageIO.read(file);// Image that will be used as a original restore point being given the
												// image
					original.setIcon(new ImageIcon(image));
				} catch (IOException f) {
				}
			}
		}
	}

	public static class Save implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (selectFile.showSaveDialog(save) == JFileChooser.APPROVE_OPTION) {
				file = selectFile.getSelectedFile();
				try {
					ImageIO.write(image, "png", file); // Writing the image to an existing or new file
				} catch (IOException f) {
					f.printStackTrace();
				}
			}
		}
	}

	public static class Restore implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					image.setRGB(x, y, copy.getRGB(x, y)); // Takes the color values from the second image from OpenFile
															// and sets it into the main image
				}
			}
			original.setIcon(new ImageIcon(image));
		}
	}

	public static class Horizontal implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			for (int x = 0; x < image.getWidth() / 2; x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					int tmp = image.getRGB(x, y); // Using a variable to store colour values before the flip is
													// performed
					image.setRGB(x, y, image.getRGB(image.getWidth() - x - 1, y)); // Takes colour values from right
																					// side of the image and sets it on
																					// left side
					image.setRGB(image.getWidth() - x - 1, y, tmp); // Takes old left side colour values and sets it on
																	// right side
				}
			}
			original.setIcon(new ImageIcon(image));
		}
	}

	public static class Vertical implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight() / 2; y++) {
					int tmp = image.getRGB(x, y); // Using a variable to store colour values before the flip is
													// performed
					image.setRGB(x, y, image.getRGB(x, image.getHeight() - y - 1)); // Takes colour values from bottom
																					// of the image and sets it on top
																					// side
					image.setRGB(x, image.getHeight() - y - 1, tmp); // Takes old top colour values and sets it on
																		// bottom
				}
			}
			original.setIcon(new ImageIcon(image));
		}
	}

	public static class Grey implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					Color c = new Color(image.getRGB(x, y)); // Storing colour in a Color object
					int r = (int) (c.getRed() * 0.299); // Taking out specific colour channels and multiplying by
														// specified values
					int g = (int) (c.getGreen() * 0.587);
					int b = (int) (c.getBlue() * 0.114);
					c = new Color(r + g + b, r + g + b, r + g + b); // Averaging out colour channels

					image.setRGB(x, y, c.getRGB());
				}
			}
			original.setIcon(new ImageIcon(image));
		}
	}

	public static class Sepia implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					Color c = new Color(image.getRGB(x, y));// Storing colour in a Color object
					int r = (int) (c.getRed()); // Storing r g b colour channels into variables
					int g = (int) (c.getGreen());
					int b = (int) (c.getBlue());
					int sr = (int) (0.393 * r + 0.769 * g + 0.189 * b); // Multiplying specified colour values by the
																		// colour channels
					int sg = (int) (0.349 * r + 0.686 * g + 0.168 * b);
					int sb = (int) (0.272 * r + 0.534 * g + 0.131 * b);

					if (sr > 255) { // Making sure that if the colors are out of boundaries, it will be set to its
									// 's' value
						r = 255;
					} else {
						r = sr;
					}

					if (sg > 255) {
						g = 255;
					} else {
						g = sg;
					}

					if (sb > 255) {
						b = 255;
					} else {
						b = sb;
					}

					c = new Color(r, g, b, c.getAlpha());
					image.setRGB(x, y, c.getRGB());
				}
			}
			original.setIcon(new ImageIcon(image));
		}
	}

	public static class Invert implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					Color c = new Color(image.getRGB(x, y)); // Storing colour in a Color object
					int r = (int) (255 - c.getRed()); // Subtracting colour channels from 255 in order to result in an
														// inverted colour value
					int g = (int) (255 - c.getGreen());
					int b = (int) (255 - c.getBlue());
					c = new Color(r, g, b, c.getAlpha());

					image.setRGB(x, y, c.getRGB());
				}
			}
			original.setIcon(new ImageIcon(image));
		}
	}

	public static class Gaussian implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			double kernel[][] = new double[5][5];

			double sum = 0;

			for (int a = 0; a < 5; a++) {
				for (int b = 0; b < 5; b++) {
					// Making a kernel that stores a constant value of the gaussian kernel using the given formula in a 5x5 radius
					kernel[a][b] = (1 / (2 * Math.PI * Math.pow(1.5, 2)))
							* Math.pow(Math.E, -(Math.pow(a - 2, 2) + Math.pow(b - 2, 2)) / (2 * Math.pow(1.5, 2)));
					sum += kernel[a][b];
				}
			}

			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					//Divide the sum by the sum to start blurring in the middle pixel
					kernel[i][j] /= sum;
				}
			}

			for (int x = 0; x < image.getWidth() - 5; x++) {
				for (int y = 0; y < image.getHeight() - 5; y++) {
					double r = 0, g = 0, b = 0;
					for (int i = 0; i < 5; i++) {
						for (int j = 0; j < 5; j++) {
							try {
								Color c = new Color(image.getRGB(5 / 2 + x - 1 + i, 5 / 2 + y - 1 + j));
								// Summing up r g b values that are multiplied by the kernel in order to achieve blur effect
								r += c.getRed() * kernel[i][j];
								b += c.getBlue() * kernel[i][j];
								g += c.getGreen() * kernel[i][j];
							} catch (Exception f) {
							}

						}
					}
					Color color = new Color((int) r, (int) g, (int) b);
					image.setRGB(x, y, color.getRGB());
				}
			}

			original.setIcon(new ImageIcon(image));
		}

	}

	public static class Bulge implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			int bulgeRadius;
			BufferedImage bulged = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB); // Make a new image for the bulged image output
			if (image.getWidth() > image.getHeight()) { // If the width is greater than height, it will use the width as the radius and vice versa
				bulgeRadius = image.getWidth();
			} else {
				bulgeRadius = image.getHeight();
			}

			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					try {
						int centerX = image.getWidth() / 2;//Finds center pixel to start from center
						int centerY = image.getHeight() / 2;
						double xVal = x - centerX;
						double yVal = y - centerY;
						double radius = Math.sqrt(Math.pow(xVal, 2) + Math.pow(yVal, 2)) / (bulgeRadius / 2); // Converting coordinate axis to polar axis
						double angle = Math.atan2(yVal, xVal);
						double newRadius = Math.pow(radius, 1.5) / 0.79 * (bulgeRadius / 2);
						xVal = newRadius * Math.cos(angle) + centerX; // Calculating bulged pixels
						yVal = newRadius * Math.sin(angle) + centerY;
						bulged.setRGB(x, y, image.getRGB((int) xVal, (int) yVal));
					} catch (Exception f) {
					}
				}
			}
			image = bulged; //Setting the main image as the bulged image
			original.setIcon(new ImageIcon(image));
		}
	}

	public static class Rotate180 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					newImage.setRGB(image.getWidth() - 1 - x, image.getHeight() - 1 - y, image.getRGB(x, y)); // Takes the top left pixel and sets in bottom right and continues
				}
			}
			image = newImage;
			original.setIcon(new ImageIcon(newImage));
		}
	}

	public static void main(String[] args) {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Options");

		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
		restore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
		hFlip.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK));
		vFlip.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
		gray.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
		sepia.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
		invert.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK));
		blur.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK));
		bulge.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));

		file.add(open);
		file.add(save);
		file.add(exit);
		edit.add(restore);
		edit.addSeparator();
		edit.add(hFlip);
		edit.add(vFlip);
		edit.add(gray);
		edit.add(sepia);
		edit.add(invert);
		edit.add(blur);
		edit.add(bulge);
		edit.add(rotate);
		menuBar.add(file);
		menuBar.add(edit);
		frame.setJMenuBar(menuBar);
		original.setHorizontalAlignment(JLabel.CENTER);
		panel.add(original, BorderLayout.CENTER);

		open.addActionListener(new OpenFile());
		save.addActionListener(new Save());
		exit.addActionListener(new Exit());

		if (original != null) {
			restore.addActionListener(new Restore());
			hFlip.addActionListener(new Horizontal());
			vFlip.addActionListener(new Vertical());
			gray.addActionListener(new Grey());
			sepia.addActionListener(new Sepia());
			invert.addActionListener(new Invert());
			blur.addActionListener(new Gaussian());
			bulge.addActionListener(new Bulge());
			rotate.addActionListener(new Rotate180());
		}

		panel.setPreferredSize(new Dimension(1000, 750));
		frame.setResizable(true);
		frame.setContentPane(panel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	}
}
