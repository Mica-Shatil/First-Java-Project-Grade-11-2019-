// The code for the angle/velocity of the bullets was gotten from the link below
// http://www.java-gaming.org/topics/how-to-make-a-bullet-shoot-towards-the-mouse/33010/view.html
//package Block_Breaker;

import hsa_ufa.Console;
import java.lang.Math;
import java.awt.*;
// java applet not used as sound does not work
// import java.applet.*;
import java.util.concurrent.ThreadLocalRandom;

public class BlockBreaker {

	// creates static game console
	static Console c;

	// declares all static variables (there are many because all of these variables
	// are used in different methods
	static int floor_x[] = new int[100];
	static int floor_y[] = new int[100];
	static int floor_w[] = new int[100];
	static int enemy_x[] = new int[100];
	static int enemy_y[] = new int[100];
	static int level_floor[] = new int[100];
	static int level_enemy[] = new int[100];
	static final int enemy_w = 20;
	static int char_x = 30;
	static int char_y = 380;
	static int score = 0;
	static final int char_w = 20;
	static int bullet_x;
	static int bullet_y;
	static int bullet_Ex[] = new int[100];
	static int bullet_Ey[] = new int[100];
	static final int bullet_w = 10;
	static double mouse_x;
	static double mouse_y;
	static int motion_x;
	static int level[] = new int[0];
	static int enemy_direction[] = new int[100];
	static int motion_y;
	static int floor_count = 0;
	static double yVelocity;
	static double xVelocity;
	static double EyVelocity[] = new double[100];
	static double ExVelocity[] = new double[100];
	static boolean enemy_shoot[] = { false, false, false, false, false, false, false, false, false, false, false };
	static boolean first = true;
	static boolean enemy_life[] = { true, true, true, true, true, true, true, true, true, true, true, true, true };
	static boolean up = false;
	static boolean down = false;
	static int velocity = 15;
	static boolean can_shoot = false;
	static boolean death = false;
	static String user;

	// main method to run all base code
	public static void main(String[] args) throws InterruptedException {

		// runs title method
		title();

		// plays music (doesn't work)
		// AudioClip music =
		// Applet.newAudioClip(c.getClass().getClassLoader().getResource("mario/ElevatorMusic.wav"));
		// music.loop();

		// opens game console
		c = new Console(500, 600, "Block Boss!");

		boolean new_level = false;

		c.enableMouse();

		// begins game loop
		while (death == false) {

			// sets/resets all variables for if a player decides to play again
			boolean enemy_pos = false;
			char_x = 30;
			char_y = 380;
			score = 0;
			floor_count = 0;
			for (int i = 0; i <= 10; i++) {
				enemy_shoot[i] = false;
			}
			first = true;
			for (int i = 0; i <= 12; i++) {
				enemy_life[i] = true;
			}
			up = false;
			down = false;
			velocity = 15;
			can_shoot = false;
			death = false;
			level = new int[0];

			level_floor[0] = 4;
			level_floor[1] = 5;
			level_floor[2] = 7;
			level_floor[3] = 9;
			level_floor[4] = 14;
			level_floor[5] = 17;

			level_enemy[0] = 0;
			level_enemy[1] = 0;
			level_enemy[2] = 3;
			level_enemy[3] = 4;
			level_enemy[4] = 7;
			level_enemy[5] = 12;

			new_level = false;

			floor_x[0] = 0;
			floor_x[1] = 300;
			floor_x[2] = 330;
			floor_x[3] = 360;
			floor_x[4] = 390;
			floor_x[5] = 500;
			floor_x[6] = 900;
			floor_x[7] = 1000;
			floor_x[8] = 1400;
			floor_x[9] = 1500;
			floor_x[10] = 1750;

			floor_y[0] = 400;
			floor_y[1] = 360;
			floor_y[2] = 340;
			floor_y[3] = 320;
			floor_y[4] = 300;
			floor_y[5] = 300;
			floor_y[6] = 350;
			floor_y[7] = 350;
			floor_y[8] = 350;
			floor_y[9] = 350;
			floor_y[10] = 350;

			floor_w[0] = 300;
			floor_w[1] = 30;
			floor_w[2] = 30;
			floor_w[3] = 30;
			floor_w[4] = 110;
			floor_w[5] = 300;
			floor_w[6] = 100;
			floor_w[7] = 300;
			floor_w[8] = 100;
			floor_w[9] = 150;
			floor_w[10] = 250;

			enemy_x[0] = 200;
			enemy_x[1] = 0;
			enemy_x[2] = 220;
			enemy_x[3] = 420;
			enemy_x[4] = 0;

			// level 1 loop
			while (death == false) {
				synchronized (c) {
					move();
					draw();
					check_deaths();
				}
				Thread.sleep(20);
				// next level loop and loop break
				if (char_x + char_w >= 500) {
					char_x = 30;
					bullet_x = 600;
					score += 100;
					level = new int[1];
					break;
				}
				// death sequence
				if (death == true) {
					for (int i = 0; i < 5; i++) {
						Thread.sleep(100);
						c.clear();
						Thread.sleep(100);
						draw();
					}
				}
			}

			char_x = 500;

			// side scroll loop
			while (death == false && floor_x[5] > 0) {
				for (int i = 0; i < floor_x.length; i++) {
					floor_x[i] -= 10;
				}
				char_x -= 10;
				draw();
				Thread.sleep(20);
			}

			// level 2 loop
			while (death == false && true) {
				synchronized (c) {
					move();
					draw();
					check_deaths();
				}
				Thread.sleep(20);
				// next level loop and loop break (checks if all enemies are dead)
				if (char_x + char_w >= 500) {
					for (int i = level_enemy[level.length]; i >= level_enemy[level.length - 1]; i--) {
						enemy_shoot[i] = false;
						bullet_Ex[i] = 600;
					}
					char_x = 30;
					bullet_x = 600;
					floor_count = 0;
					score += 100;
					level = new int[2];
					break;
				}
				// death sequence
				if (death == true) {
					for (int i = 0; i < 5; i++) {
						Thread.sleep(100);
						c.clear();
						Thread.sleep(100);
						draw();
					}
				}
			}

			char_x = 500;

			// side scroll loop
			while (death == false && floor_x[7] > 0) {
				for (int i = 0; i < floor_x.length; i++) {
					floor_x[i] -= 10;
				}
				char_x -= 10;
				draw();
				Thread.sleep(20);
			}

			// level 3 loop
			while (death == false && true) {
				synchronized (c) {
					move();
					draw();
					check_deaths();
				}
				Thread.sleep(20);
				// next level loop and loop break(checks if all enemies are dead)
				if (char_x + char_w >= 500) {
					for (int i = level_enemy[level.length]; i >= level_enemy[level.length - 1]; i--) {
						enemy_shoot[i] = false;
						bullet_Ex[i] = 600;
					}
					char_x = 30;
					bullet_x = 600;
					floor_count = 0;
					score += 100;
					level = new int[3];
					break;
				}
				// death sequence
				if (death == true) {
					for (int i = 0; i < 5; i++) {
						Thread.sleep(100);
						c.clear();
						Thread.sleep(100);
						draw();
					}
				}
			}

			char_x = 500;

			// side scroll loop
			while (death == false && floor_x[9] > 0) {
				for (int i = 0; i < floor_x.length; i++) {
					floor_x[i] -= 10;
				}
				char_x -= 10;
				draw();
				Thread.sleep(20);
			}

			// level 4 loop
			while (death == false && true) {
				synchronized (c) {
					move();
					draw();
					check_deaths();
				}
				Thread.sleep(20);
				// next level loop and loop break(checks if all enemies are dead)
				if (char_x + char_w >= 500) {
					for (int i = level_enemy[level.length]; i >= level_enemy[level.length - 1]; i--) {
						enemy_shoot[i] = false;
						bullet_Ex[i] = 600;
					}
					char_x = 30;
					bullet_x = 600;
					floor_count = 0;
					score += 100;
					level = new int[4];
					break;
				}
				// death sequence
				if (death == true) {
					for (int i = 0; i < 5; i++) {
						Thread.sleep(100);
						c.clear();
						Thread.sleep(100);
						draw();
					}
				}
			}

			char_x = 500;

			// side scroll
			while (death == false && floor_x[0] > 0) {
				for (int i = 0; i < floor_x.length; i++) {
					floor_x[i] -= 10;
				}
				char_x -= 10;
				draw();
				Thread.sleep(20);
			}

			// sets floor for first randomized level
			floor_y[5] = floor_y[10];

			// Loop for randomized levels
			while (death == false) {

				char_x = 500;

				// creates random floors
				floor_x[6] = 500;
				floor_w[6] = ThreadLocalRandom.current().nextInt(100, 500 + 1);
				floor_y[6] = floor_y[5];
				for (int i = 7; i <= 11; i++) {
					floor_x[i] = ThreadLocalRandom.current().nextInt(1, 10 + 1);
					floor_w[i] = ThreadLocalRandom.current().nextInt(10, 50 + 1);
					floor_y[i] = ThreadLocalRandom.current().nextInt(floor_y[i - 1], 500 + 1);
					floor_x[i] = floor_x[i - 1] + floor_w[i - 1] + (floor_x[i] * 10);
					floor_w[i] = floor_w[i] * 10;
					if (floor_w[i] + floor_x[i] >= 1000) {
						floor_w[i] = 1000 - floor_x[i];
						level_floor[level.length] = i;
						floor_x[i + 1] = 1100;
						floor_y[11] = floor_y[i];
						break;
					}
				}

				// creates random enemies
				level_enemy[level.length] = ThreadLocalRandom.current().nextInt(1, 4 + 1);
				for (int i = 0; i <= level_enemy[level.length]; i++) {
					enemy_life[i] = true;
				}
				// looks to see if enemies spawn on top of each other
				while (death == false && true) {
					for (int i = 0; i <= level_enemy[level.length]; i++) {
						enemy_x[i] = ThreadLocalRandom.current().nextInt(1, 480 + 1);
						if (i > 0) {
							for (int q = 0; q < i; q++) {
								while (enemy_x[i] > enemy_x[q] && enemy_x[i] < enemy_x[q] + enemy_w) {
									enemy_x[i] = ThreadLocalRandom.current().nextInt(1, 480 + 1);
									enemy_pos = true;
									break;
								}
								if (enemy_pos == true) {
									break;
								}
							}
						}
						if (enemy_pos == true) {
							break;
						}
					}
					if (enemy_pos == true) {
						enemy_pos = false;
						continue;
					} else {
						break;
					}
				}

				// side scrolls
				while (death == false && floor_x[6] > 0) {
					for (int i = 0; i < floor_x.length; i++) {
						floor_x[i] -= 10;
					}
					char_x -= 10;
					draw();
					Thread.sleep(20);
				}

				for (int i = 0; i <= 5; i++) {
					floor_x[i] = -1;
					floor_w[i] = 0;
				}
				// first random level loop
				while (death == false) {
					synchronized (c) {
						move();
						draw();
						check_deaths();
					}
					Thread.sleep(20);

					// next level loop and loop break(checks if all enemies are dead)
					if (char_x + char_w >= 500) {
						if (level.length > 0) {
							for (int i = 0; i <= level_enemy[level.length]; i++) {
								if (enemy_life[i] == false) {
									new_level = true;
								} else {
									char_x = 500 - char_w;
									new_level = false;
									break;
								}
							}
						}
					}
					if (new_level == true) {
						for (int i = level_enemy[level.length]; i >= 0; i--) {
							enemy_shoot[i] = false;
							bullet_Ex[i] = 600;
						}
						char_x = 30;
						bullet_x = 600;
						score += 100;
						level = new int[level.length + 1];
						new_level = false;
						break;
					}
					// death sequence
					if (death == true) {
						for (int i = 0; i < 5; i++) {
							Thread.sleep(100);
							c.clear();
							Thread.sleep(100);
							draw();
						}
					}
				}

				char_x = 500;

				// randomizes floor
				floor_x[0] = 500;
				floor_w[0] = ThreadLocalRandom.current().nextInt(100, 500 + 1);
				floor_y[0] = floor_y[11];
				for (int i = 1; i <= 5; i++) {
					floor_x[i] = ThreadLocalRandom.current().nextInt(1, 10 + 1);
					floor_w[i] = ThreadLocalRandom.current().nextInt(10, 50 + 1);
					floor_y[i] = ThreadLocalRandom.current().nextInt(floor_y[i - 1], 500 + 1);
					floor_x[i] = floor_x[i - 1] + floor_w[i - 1] + floor_x[i] * 10;
					floor_w[i] = floor_w[i] * 10;
					if (floor_w[i] + floor_x[i] >= 1000) {
						floor_w[i] = 1000 - floor_x[i - 1];
						level_floor[level.length] = i;
						floor_x[i + 1] = 1100;
						floor_y[5] = floor_y[i];
						break;
					}
				}

				// randomizes enemies
				level_enemy[level.length] = ThreadLocalRandom.current().nextInt(1, 4 + 1);
				for (int i = 0; i <= level_enemy[level.length]; i++) {
					enemy_life[i] = true;
				}
				// looks to see if enemies spawn on top of each other
				while (death == false && true) {
					for (int i = 0; i <= level_enemy[level.length]; i++) {
						enemy_x[i] = ThreadLocalRandom.current().nextInt(1, 480 + 1);
						if (i > 0) {
							for (int q = 0; q < i; q++) {
								while (enemy_x[i] > enemy_x[q] && enemy_x[i] < enemy_x[q] + enemy_w) {
									enemy_x[i] = ThreadLocalRandom.current().nextInt(1, 480 + 1);
									enemy_pos = true;
									break;
								}
								if (enemy_pos == true) {
									break;
								}
							}
						}
						if (enemy_pos == true) {
							break;
						}
					}
					if (enemy_pos == true) {
						enemy_pos = false;
						continue;
					} else {

						break;
					}
				}

				// side scroll
				while (death == false && floor_x[0] > 0) {
					for (int i = 0; i < floor_x.length; i++) {
						floor_x[i] -= 10;
					}
					char_x -= 10;
					draw();
					Thread.sleep(20);
				}
				for (int i = 6; i <= 11; i++) {
					floor_x[i] = -1;
					floor_w[i] = 0;
				}

				// randomized level loop
				while (death == false && true) {
					synchronized (c) {
						move();
						draw();
						check_deaths();
					}
					Thread.sleep(20);
					// next level loop and loop break(checks if all enemies are dead)
					if (char_x + char_w >= 500) {
						if (level.length > 0) {
							for (int i = 0; i <= level_enemy[level.length]; i++) {
								if (enemy_life[i] == false) {
									new_level = true;
								} else {
									char_x = 500 - char_w;
									new_level = false;
									break;
								}
							}
						}
					}
					if (new_level == true) {
						for (int i = level_enemy[level.length]; i >= 0; i--) {
							enemy_shoot[i] = false;
							bullet_Ex[i] = 600;
						}
						char_x = 30;
						bullet_x = 600;
						score += 100;
						level = new int[level.length + 1];
						new_level = false;
						break;
					}
					// death sequence
					if (death == true) {
						for (int i = 0; i < 5; i++) {
							Thread.sleep(100);
							c.clear();
							Thread.sleep(100);
							draw();
						}
					}
				}
			}
			// runs death method if death is true
			if (death == true) {
				death();
			}
		}
		// closes console after all loops to end program
		c.close();
	}

	// title method for title screen
	static void title() throws InterruptedException {
		// opens and sets up console for title screen
		Console open = new Console(500, 600, 14, "Block Boss!");
		open.setFont(new Font("Impact", Font.BOLD, 70));

		// prints out the title screen and moves the char across the front
		while (char_x <= 500) {
			synchronized (open) {
				open.clear();
				open.setColor(Color.MAGENTA);
				open.drawString("Block Boss", 70, 70);
				open.setColor(Color.RED);
				open.fillRect(char_x, char_y, char_w, char_w);
				open.setColor(Color.BLACK);
				open.fillRect(0, 400, 600, 500);
				open.setCursor(15, 0);
				open.println("Use a, d, and space to move.");
				open.println("Use The mouse to aim and shoot.");
				open.println("Destroy enemy blocks before they destroy you!");
			}
			Thread.sleep(20);
			char_x += 5;
		}

		char_x = 30;

		// inputs user name
		open.setFont(new Font("Ariel", Font.BOLD, 20));
		open.setColor(Color.BLACK);
		open.drawString(" Enter Your Username:", 150, 240);
		open.setCursor(11, 47);
		user = open.readLine();
		Thread.sleep(2000);
		open.close();

	}

	// bullet shooting method for character
	static void shoot() {

		// moves the char bullet
		bullet_x += xVelocity;
		bullet_y += yVelocity;

		// resets bullet
		if (bullet_y < -10 || bullet_y > 600 || bullet_x < -10 || bullet_x > 500) {
			can_shoot = false;
			bullet_y = 610;
			bullet_x = 610;
		}

	}

	// draw method prints to the console
	static void draw() {
		// draws floor
		c.clear();
		c.setColor(Color.BLACK);
		for (int i = 0; i <= level_floor[level.length]; i++) {
			c.fillRect(floor_x[i], floor_y[i], floor_w[i], 600);
		}

		// draws char
		c.setColor(Color.RED);
		c.fillRect(char_x, char_y, char_w, char_w);

		// draws enemies for non randomized level
		if (level.length > 0) {
			if (level.length <= 3) {
				if (level_enemy[level.length] >= 0) {
					for (int i = level_enemy[level.length]; i >= level_enemy[level.length - 1]; i--) {
						if (enemy_life[i] == true) {
							c.setColor(Color.GREEN);
							c.fillRect(enemy_x[i], enemy_y[i], enemy_w, enemy_w);
						}
					}
				}
				// draws enemy bullets for non randomized levels
				for (int i = level_enemy[level.length]; i >= level_enemy[level.length - 1]; i--) {
					if (enemy_shoot[i] == true) {
						c.setColor(Color.PINK);
						c.fillOval(bullet_Ex[i], bullet_Ey[i], bullet_w, bullet_w);
					}
				}
				// draws enemies for randomized levels
			} else {
				for (int i = level_enemy[level.length]; i >= 0; i--) {
					if (enemy_life[i] == true) {
						c.setColor(Color.GREEN);
						c.fillRect(enemy_x[i], enemy_y[i], enemy_w, enemy_w);
					}
				}
				// draws enemy bullets of randomized levels
				for (int i = level_enemy[level.length]; i >= 0; i--) {
					if (enemy_shoot[i] == true) {
						c.setColor(Color.PINK);
						c.fillOval(bullet_Ex[i], bullet_Ey[i], bullet_w, bullet_w);
					}
				}
			}
		}

		// draws char bullet
		if (can_shoot == true) {
			c.setColor(Color.blue);
			c.fillOval(bullet_x, bullet_y, bullet_w, bullet_w);
		}

		// draws user name
		c.setCursor(33, 0);
		c.setColor(Color.red);
		c.setBackgroundColor(Color.black);
		c.print(user + ": " + score);
		c.setBackgroundColor(Color.white);

	}

	// move method does general motion tests and checks before actually moving any
	// game pieces
	static void move() {

		// checks if char shoots and sets velocity and angle for char bullet
		if (c.getMouseClick() >= 1 && can_shoot == false) {
			mouse_x = c.getMouseX();
			mouse_y = c.getMouseY();
			if (bullet_x == char_x && bullet_y == char_y) {
				bullet_x--;
				bullet_y--;
			}
			bullet_x = char_x;
			bullet_y = char_y;
			double bulletVelocity = 7.0;
			double angle = Math.atan2(mouse_x - char_x, mouse_y - char_y);
			xVelocity = (bulletVelocity) * Math.sin(angle);
			yVelocity = (bulletVelocity) * Math.cos(angle);
			can_shoot = true;
		}

		// runs the move_char method
		move_char();

		// runs enemy motion method if enemies should move
		if (level.length >= 3) {
			enemy_move();
		}

		// runs shooting method if char is shooting
		if (can_shoot == true) {
			shoot();
		}

		// checks if enemy can shoot and sets enemy shot angle and velocity (if for non
		// randomized levels else for randomized ones)
		if (level.length > 0) {
			if (level.length <= 3) {
				for (int i = level_enemy[level.length]; i >= level_enemy[level.length - 1]; i--) {
					if (enemy_shoot[i] == false && enemy_life[i] == true) {
						bullet_Ex[i] = enemy_x[i];
						bullet_Ey[i] = enemy_y[i];
						double bulletVelocity = 7.0;
						double angle = Math.atan2(char_x - enemy_x[i], char_y - enemy_y[i]);
						ExVelocity[i] = (bulletVelocity) * Math.sin(angle);
						EyVelocity[i] = (bulletVelocity) * Math.cos(angle);
						enemy_shoot[i] = true;
					}
				}
			} else {
				for (int i = level_enemy[level.length]; i >= 0; i--) {
					if (enemy_shoot[i] == false && enemy_life[i] == true) {
						bullet_Ex[i] = enemy_x[i];
						bullet_Ey[i] = enemy_y[i];

						double bulletVelocity = 7.0;
						double angle = Math.atan2(char_x - enemy_x[i], char_y - enemy_y[i]);
						ExVelocity[i] = (bulletVelocity) * Math.sin(angle);
						EyVelocity[i] = (bulletVelocity) * Math.cos(angle);
						enemy_shoot[i] = true;
					}
				}
			}
		}

		// runs enemy shot method if it should run (if for non randomized else for
		// randomized)
		if (level.length > 0) {
			if (level.length <= 3) {
				for (int i = level_enemy[level.length]; i >= level_enemy[level.length - 1]; i--) {
					if (enemy_shoot[i] == true) {
						shoot_enemy();
						break;
					}
				}
			} else {
				for (int i = level_enemy[level.length]; i >= 0; i--) {
					if (enemy_shoot[i] == true) {
						shoot_enemy();
						break;
					}
				}
			}
		}
	}

	// this method checks to see if any enemies or if the char has died
	static void check_deaths() {
		// checks deaths of enemies and char due to bullet collision
		// checks char death
		if (level.length > 0) {
			if (level.length <= 3) {
				for (int i = level_enemy[level.length]; i >= level_enemy[level.length - 1]; i--) {
					if (bullet_Ex[i] + 20 > char_x && bullet_Ex[i] < char_x + char_w && bullet_Ey[i] + 20 > char_y
							&& bullet_Ey[i] < char_y + char_w) {
						death = true;
					}
				}
				// checks enemy deaths
				for (int i = level_enemy[level.length]; i >= level_enemy[level.length - 1]; i--) {
					if (bullet_x < enemy_x[i] + enemy_w && bullet_x + bullet_w > enemy_x[i]
							&& bullet_y < enemy_y[i] + enemy_w && bullet_y + bullet_w > enemy_y[i]
							&& enemy_life[i] == true) {
						enemy_life[i] = false;
						score += 30;
					}
				}
				// makes final floor appear when all enemies are dead
				if (level.length < 3) {
					for (int i = level_enemy[level.length]; i >= level_enemy[level.length - 1]; i--) {
						if (enemy_life[i] == false) {
							floor_count++;
						} else {
							floor_count = 0;
							break;
						}
					}
					if (floor_count == level_enemy[level.length] + 1) {
						level_floor[level.length]++;
					}
				}

				// adds floor if all enemies are dead
				if (level.length >= 3) {
					for (int i = level_enemy[level.length]; i > level_enemy[level.length - 1]; i--) {
						if (enemy_life[i] == false) {
							floor_count++;
						} else {
							floor_count = 0;
							break;
						}
					}
					if (floor_count == 1) {
						level_floor[level.length]++;
					}
				}
				// checks char death for randomized levels
			} else {
				for (int i = level_enemy[level.length]; i >= 0; i--) {
					if (bullet_Ex[i] + 20 > char_x && bullet_Ex[i] < char_x + char_w && bullet_Ey[i] + 20 > char_y
							&& bullet_Ey[i] < char_y + char_w) {
						death = true;
					}
				}
				// checks enemy deaths
				for (int i = level_enemy[level.length]; i >= 0; i--) {
					if (bullet_x < enemy_x[i] + enemy_w && bullet_x + bullet_w > enemy_x[i]
							&& bullet_y < enemy_y[i] + enemy_w && bullet_y + bullet_w > enemy_y[i]
							&& enemy_life[i] == true) {
						enemy_life[i] = false;
						score += 30;
					}
				}
			}
		}

		// checks to see if char falls off console
		if (char_y > 600) {
			death = true;
		}
	}

	// move_char method moves the character
	static void move_char() {

		// sets local variables for motion
		boolean mover = true;
		boolean movel = true;

		// checks to see if if char is hitting a wall to the right
		for (int i = 0; i <= level_floor[level.length]; i++) {
			if (char_x + char_w == floor_x[i + 1] && char_y + char_w >= floor_y[i]) {
				mover = false;
			}
		}

		// checks to see if char is hitting a wall on the left
		for (int i = 0; i <= level_floor[level.length]; i++) {
			if (char_x == floor_x[i] + floor_w[i] && char_y + char_w >= floor_y[i]) {
				movel = false;
			}
		}

		// checks to see if char is below the floor and corrects if it is
		for (int i = 0; i <= level_floor[level.length]; i++) {
			if (char_x >= floor_x[i] && char_x + char_w <= floor_x[i] + floor_w[i] && char_y + char_w > floor_y[i]) {
				char_y = floor_y[i] - char_w;
				break;
			}
		}

		// moves char
		if (mover == true) {
			if (c.isKeyDown('D') && char_x + char_w < 520) {
				char_x += 5;
			}
		}
		if (movel == true) {
			if (c.isKeyDown('A') && char_x > 0) {
				char_x -= 5;
			}
		}

		// checks to see if jump has reached peak
		if (velocity == 0 && up == true) {
			up = false;
			down = true;
		}

		// makes char fall if char is over the edge of a floor
		for (int i = 0; i < level_floor[level.length]; i++) {
			if (char_x > floor_x[i] + floor_w[i] || char_x < floor_x[i]) {
				down = true;
			}
		}

		// resets velocity after jumps
		for (int i = 0; i <= level_floor[level.length]; i++) {
			if (char_y + char_w >= floor_y[i] && char_y + char_w <= floor_y[i] + 30 && char_x >= floor_x[i] - 10
					&& char_x <= floor_x[i] + floor_w[i] && down == true) {
				down = false;
				velocity = 15;
				break;
			}
		}

		// checks to see if char should jump
		if (c.isKeyDown(' ') && up == false && down == false) {
			up = true;
		}

		// does jump velocity
		if (up == true) {
			velocity -= 1;
			char_y -= velocity;
		} else if (down == true) {
			velocity += 1;
			char_y += velocity;
		}
	}

	// shoot enemy runs the code for enemy shots
	static void shoot_enemy() {

		// makes enemies shoot for non randomized levels
		if (level.length <= 3) {
			for (int i = level_enemy[level.length]; i >= level_enemy[level.length - 1]; i--) {
				if (enemy_shoot[i] == true) {
					bullet_Ex[i] += ExVelocity[i];
					bullet_Ey[i] += EyVelocity[i];
					if (bullet_Ey[i] < 0 || bullet_Ey[i] > 600 || bullet_Ex[i] < 0 || bullet_Ex[i] > 500) {
						enemy_shoot[i] = false;
					}
				}
			}
			// makes enemies shoot for randomized levels
		} else {
			for (int i = level_enemy[level.length]; i >= 0; i--) {
				if (enemy_shoot[i] == true) {
					bullet_Ex[i] += ExVelocity[i];
					bullet_Ey[i] += EyVelocity[i];
					if (bullet_Ey[i] < 0 || bullet_Ey[i] > 600 || bullet_Ex[i] < 0 || bullet_Ex[i] > 500) {
						enemy_shoot[i] = false;
					}
				}
			}
		}
	}

	// moves the enemies (if they move)
	static void enemy_move() {

		// makes enemies move for non randomized levels
		if (level.length <= 3) {
			for (int i = level_enemy[level.length]; i >= level_enemy[level.length - 1]; i--) {
				if (enemy_life[i] == true) {
					if (enemy_direction[i] == 0) {
						enemy_x[i] += 2;
					} else {
						enemy_x[i] -= 2;
					}
					if (enemy_x[i] + enemy_w >= 500) {
						enemy_direction[i] = 1;
					} else if (enemy_x[i] <= 0) {
						enemy_direction[i] = 0;
					}
				}
			}
			// makes enemies move for randomized levels
		} else {
			if (level_enemy[level.length] >= 0) {
				for (int i = level_enemy[level.length]; i >= 0; i--) {
					if (enemy_life[i] == true) {
						if (enemy_direction[i] == 0) {
							enemy_x[i] += 2;
						} else {
							enemy_x[i] -= 2;
						}
						if (enemy_x[i] + enemy_w >= 500) {
							enemy_direction[i] = 1;
						} else if (enemy_x[i] <= 0) {
							enemy_direction[i] = 0;
						}
					}
				}
			}
		}
	}

	// runs the death sequence
	static void death() throws InterruptedException {

		// set local variables for death sequence
		boolean move_on = false;
		int mousex = c.getMouseX();
		int mousey = c.getMouseY();

		// draws the death/reset screen
		c.setFont(new Font("Impact", Font.BOLD, 34));
		c.clear();
		c.setColor(Color.MAGENTA);
		c.drawString("You Died! Your Score Was " + score, 10, 70);
		c.setFont(new Font("Ariel", Font.BOLD, 20));
		c.drawString("Would you like to play again?", 100, 240);
		c.setColor(Color.BLACK);
		c.fillRect(50, 300, 150, 100);
		c.fillRect(300, 300, 150, 100);
		c.setColor(Color.MAGENTA);
		c.drawString("Yes", 80, 370);
		c.drawString("No", 357, 370);

		// checks to see which button the user presses
		while (move_on == false) {
			Thread.sleep(1);
			if (c.getMouseButton(0)) {
				mousex = c.getMouseX();
				mousey = c.getMouseY();
				if (mousex >= 50 && mousex <= 200 && mousey >= 300 && mousey <= 400) {
					death = false;
					move_on = true;
				} else if (mousex >= 300 && mousex <= 450 && mousey >= 300 && mousey <= 400) {
					move_on = true;
				}
			}
		}
	}

}
