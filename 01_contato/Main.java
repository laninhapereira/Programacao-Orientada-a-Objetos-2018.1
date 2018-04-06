package teste;

import java.util.Scanner;

public class Main {
	public static void main(String args[]) {

		Contato c1 = new Contato();
		Scanner entrada = new Scanner(System.in);

		int aux = 0;

		while(true) {

			System.out.println("1 - Inserir contato");
			System.out.println("2 - Atualizar contato");
			System.out.println("3 - Remover contato");
			System.out.println("4 - Sair");
			System.out.println("5 - Lista de contatos");
			System.out.println("Escolha a opcao que deseja fazer? ");



			aux = entrada.nextInt();

			switch(aux) {
			case 1:
				c1.inserirContato();
				break;
			case 2:
				c1.atualizar();
				break;
			case 3:
				c1.remover();
				break;
			case 4:
				c1.sair();
				break;
			case 5: 
				c1.mostrarLista();
				break;
			default:
				System.out.println("Entrada invalida, escolha uma das opcoes acima!");
				break;
			}
		}
	}


}
