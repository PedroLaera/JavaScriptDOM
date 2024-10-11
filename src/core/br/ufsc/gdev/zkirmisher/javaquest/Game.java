package br.ufsc.gdev.zkirmisher.javaquest;


import br.ufsc.gdev.zkirmisher.javaquest.entities.*;
import br.ufsc.gdev.zkirmisher.javaquest.entities.Character;
import br.ufsc.gdev.zkirmisher.javaquest.statistics.*;


public class Game {

	public static void main(String[] args) {
		//areas do jogo
		Room road = new Room(
			"Fim da estrada.\n" +
			"Você se encontra no final de uma longa estrada de terra que segue para o leste.\n" +
			"À distância você avista o que parece ser uma bifurcação.\n" +
			"Ao seu redor há apenas árvores balançando no ritmo do vento.\n" +
			"Você sente que deveria pedir por \"ajuda\"." );

		Room fork = new Room(
			"Encruzilhada.\n" +
			"Este é o ponto de encontro de duas estradas, uma no sentido norte-sul e a outra indo para o oeste.\n" +
			"De frente para você, uma placa tosca feita de madeira exibe a seguinte mensagem:\n"+
				"\t-----------------------------------------\n" +
				"\t|               CUIDADO!                |\n" +
				"\t|                                       |\n" +
				"\t|   A floresta é habitada por monstros  |\n" +
				"\t|_______________________________________|\n" +
				"\t                  | |                    \n" +
			"Você só consegue identificar as palavras riscadas na madeira durante a noite pois há uma tocha anexada à ela." );

		Room forest = new Room(
			"Clareira.\n" +
			"Você chega em uma área da floresta na qual as árvores se afastam, formando um espaço aberto.\n" +
			"É possível ver a lua cheia no céu estrelado. Além disso, seus ouvidos captam o som do correr de um rio.\n" +
			"Uma estrada de terra segue para o norte. Você também identifica uma trilha adentrando a floresta na direção leste." );

		Room river = new Room(
			"Margem do rio.\n" +
			"Você se vê chegando em um terreno baixo no que aparenta ser a curva de um rio. Também é a base de uma parede rochosa.\n" +
			"Aqui, é possível sentir melhor a refrescante mistura de odores da floresta, para a qual segue uma trilha no oeste.\n" +
			"A curva do rio dá fim ao seu caminho, " +
			"embora olhando para o norte você consiga identificar algumas pedras na encosta que possam lhe servir de degraus." );

		Room cliff = new Room(
			"Encosta rochosa.\n" +
			"Você está no topo de um pequeno penhasco, abaixo do qual corre um rio. A vertigem faz com que você se desoriente.\n" +
			"Embora não haja muito além de mato aqui, você deve ter um bom senso de direção para ter chegado tão longe.\n" +
			"Observando seus arredores, é possível ver o que parece ser uma pequena fonte de luz brilhando na distância." );

		Room gate = new Room(
			"Muro de pedras.\n" +
			"Você se encontra perante um muro de mais de dois metros de altura construído com pedras lisas que impedem a sua escalada.\n" +
			"Um portão de madeira, ao norte, parece ser a única forma de passar pelo muro. Além disso, uma estrada de terra segue para o sul." );

		Room house = new Room(
			"Ao oeste da casa.\n" +
			"Você está em um jardim ao oeste de uma casa de madeira com uma única porta.\n" +
			"Em meio à grama e às flores, um monolito escuro de formato retangular parece camuflar-se na escuridão da noite." );

		Room end = new Room(
			"Casa.\n" +
			"A porta da frente está destrancada. Você decide entrar.\n" +
			"Você se vê em um cômodo iluminado pela chama tremulante de uma vela acesa.\n" +
			"A vela fica sobre uma mesa, ao lado de um pergaminho com os seguintes dizeres:" );


		//caminhos
		road.setAdjacent(fork, Room.EAST);
		fork.setAdjacent(road, Room.WEST);

		fork.setAdjacent(forest, Room.SOUTH);
		forest.setAdjacent(fork, Room.NORTH);

		fork.setAdjacent(cliff, Room.EAST);
		cliff.setAdjacent(fork, Room.WEST);

		fork.setAdjacent(gate, Room.NORTH);
		gate.setAdjacent(fork, Room.SOUTH);

		forest.setAdjacent(river, Room.EAST);
		river.setAdjacent(forest, Room.WEST);

		river.setAdjacent(cliff, Room.NORTH);
		cliff.setAdjacent(river, Room.SOUTH);

		gate.setAdjacent(house, Room.NORTH);
		house.setAdjacent(gate, Room.SOUTH);

		house.setAdjacent(end, Room.EAST);


		//monstros
		Character slime = new Character(
			"Poring", 1,
			"Monstro Gosma", new MonsterCalculator(),
			2, 3, 1 //str, dex, int
		);
		forest.setOccupant(slime);

		Character bear = new Character(
			"Urso-Coruja", 3,
			"Monstro Mutante", new MonsterCalculator(),
			5, 5, 3 //str, dex, int
		);
		river.setOccupant(bear);

		Character gorillaphant = new Character(
			"Gorilafante", 5,
			"Monstro Mutante", new MonsterCalculator(),
			8, 2, 3 //str, dex, int
		);
		gate.setOccupant(gorillaphant);


		//itens
		Item healthPotion = new Item(
			"Poção de Vida (Pequena)", Item.USE * 1000 + 100,
			"\tRecupera 50 HP",
			user -> {
				user.heal(50);
			}
		);
		fork.loot().add(healthPotion);
		cliff.loot().add(healthPotion);

		Item manaPotion = new Item(
			"Poção de Mana (Pequena)", Item.USE * 1000 + 200,
			"\tRecupera 50 MP",
			user -> {
				user.restore(50);
			}
		);
		fork.loot().add(manaPotion);

		Item powerElixir = new Item(
			"Elixir do Poder", Item.USE * 1000 + 300,
			"\tSTR +2\n\tDEX -3\n\tINT +2",
			user -> {
				user.addModifier(+2, -3, +2, 0, 0);
			}
		);
		cliff.loot().add(powerElixir);

		Item ironElixir = new Item(
				"Elixir do Ferro", Item.USE * 1000 + 301,
				"\tSTR -1\n\tINT -1\n\tArmadura +15\n\tNegação Mágica +10",
				user -> {
					user.addModifier(-1, 0, -1, +15, +10);
				}
			);
		cliff.loot().add(ironElixir);

		Item wizardHat = new Equip(
			"Chapéu Pontudo", Item.EQUIP * 1000 + Item.EQUIP_HEAD * 100 + 10,
			"\tApenas para Magos Lvl 2+.\n" +
			"\tRequer ao menos 8 INT.\n" +
			"\tINT +3\n\tArmadura +5\n\tNegação Mágica +10\n\tMP +25",
			0, 0, +3, //primary stat bonuses
			0, 0, 0, //damage bonuses
			+5, +10, //reduction bonuses
			0, +25, //vitality bonuses
			2, "Mago", //level/role requirements
			0, 0, 8 //stat requirements
		);
		cliff.loot().add(wizardHat);

		Item sword = new Equip(
			"Espada Longa", Item.EQUIP * 1000 + Item.EQUIP_WEAPON * 100 + 20,
			"\tApenas para Guerreiros Lvl 3+.\n" +
			"\tRequer ao menos 8 STR e 3 DEX.\n" +
			"\tDano de Ataque +20",
			0, 0, 0, //primary stat bonuses
			+20, 0, 0, //damage bonuses
			0, 0, //reduction bonuses
			0, 0, //vitality bonuses
			3, "Guerreiro", //level/role requirements
			8, 3, 0 //stat requirements
		);
		river.loot().add(sword);

		Item crossbow = new Equip(
			"Besta", Item.EQUIP * 1000 + Item.EQUIP_WEAPON * 100 + 30,
			"\tApenas para Ladinos.\n" +
			"\tRequer ao menos 3 STR e 7 DEX\n" +
			"\tDano de Ataque +10\n\tCritico +15%",
			0, 0, 0, //primary stat bonuses
			+10, 0, +15000, //damage bonuses
			0, 0, //reduction bonuses
			0, 0, //vitality bonuses
			1, "Ladino", //level/role requirements
			3, 7, 0 //stat requirements
		);
		forest.loot().add(crossbow);

		Item paddedArmour = new Equip(
			"Armadura Leve", Item.EQUIP * 1000 + Item.EQUIP_BODY* 100 + 00,
			"\tApenas para Aventureiros destemidos.\n\tDEX -2\n\tArmadura +20\n\tHP +35",
			0, -2, 0, //primary stat bonuses
			0, 0, 0, //damage bonuses
			+20, 0, //reduction bonuses
			+35, 0, //vitality bonuses
			1, "Aventureiro", //level/role requirements
			0, 0, 0 //stat requirements
		);
		fork.loot().add(paddedArmour);

		//instanciando objetos de controle do jogo
		TextGameView cli = new TextGameView();
		TextGame world = new TextGame(road, end);
		TextGameController game = new TextGameController(world, cli);

		//roda o jogo
		game.start();

		//fim do programa
		System.exit(0);
	}

}
