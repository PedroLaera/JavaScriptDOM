package br.ufsc.gdev.zkirmisher.javaquest;


import java.util.InputMismatchException;
import java.util.Scanner;

import br.ufsc.gdev.zkirmisher.javaquest.entities.Player;
import br.ufsc.gdev.zkirmisher.javaquest.statistics.*;


/**
 * Handles printing and scanning text from the terminal.
 */
public class TextGameView {

	// ATTRIBUTES
	private Scanner read = new Scanner(System.in);


	// METHODS
	public void show(final Room room) {
		String content = "";

		if (room.getOccupant() != null) {
			content = "\nUm " + room.getOccupant().name() + " se move rapidamente na sua direção.\n";
		} else if (!room.loot().isEmpty()) {
			content = "\nVocê também vê alguns itens espalhados pelo chão:\n" + room.loot().toString();
		}

		System.out.print(String.format(
			"\n\n%s\n" +
			"%s",
			room.getDescription(),
			content
		));
	}

	public String getUserCommand() {
		System.out.print("\n> ");
		return read.nextLine();
	}

	public void showMessage(final String message) {
		System.out.println("\n" + message);
	}

	public void greet() {
		System.out.println(
			"   ___                  _____                 _   \n" +
			"  |_  |                |  _  |               | |  \n" +
			"    | | __ ___   ____ _| | | |_   _  ___  ___| |_ \n" +
			"    | |/ _` \\ \\ / / _` | | | | | | |/ _ \\/ __| __|\n" +
			"/\\__/ / (_| |\\ V / (_| \\ \\/' / |_| |  __/\\__ \\ |_ \n" +
			"\\____/ \\__,_| \\_/ \\__,_|\\_/\\_\\\\__,_|\\___||___/\\__|\n" +
			"\tAutor: Gabriel B. Sant'Anna\n" );
	}

	/**
	 * @return User-created Player.
	 */
	public Player createPlayer() {
		Player player = new Player();

		//name
		boolean hasSetName = false;
		while (!hasSetName) {
			System.out.print("\nInsira o nome do seu personagem\n> ");

			try {
				player.setName(read.nextLine());
				hasSetName = true;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				continue;
			}
		}

		//role
		boolean hasChoosenRole = false;
		while (!hasChoosenRole) {
			System.out.print(
				"\nEscolha sua classe (insira um número)\n" +
				" [0] Aventureiro\n" +
				" [1] Mago\n" +
				" [2] Guerreiro\n" +
				" [3] Ladino\n" +
				"> " );

			int role = 0;

			try {
				role = read.nextInt();
				read.nextLine(); //clear buffer
			} catch (InputMismatchException e) {
				read.nextLine(); //clear buffer
				continue;
			}

			switch (role) {
				case 1:
					player.setRole("Mago");
					player.setStatCalculator(new MageCalculator());
					break;

				case 2:
					player.setRole("Guerreiro");
					player.setStatCalculator(new WarriorCalculator());
					break;

				case 3:
					player.setRole("Ladino");
					player.setStatCalculator(new ThiefCalculator());
					break;

				case 0:
					player.setRole("Aventureiro");
					player.setStatCalculator(new AdventurerCalculator());
					break;

				default:
					continue;
			}
			hasChoosenRole = true;
		}

		return player;
	}

	public void bye() {
		System.out.print(
				"\n\nSaindo do mundo de JavaQuest...\n\n" +
				"Pressione ENTER para terminar o programa\n> " );
		read.nextLine();
	}

}
