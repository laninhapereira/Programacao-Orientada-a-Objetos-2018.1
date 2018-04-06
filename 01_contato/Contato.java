package teste;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Contato {

	public String nome;
	static int aux;


	static Scanner entrada = new Scanner(System.in);
	static ArrayList<Telefone> lista = new ArrayList<Telefone>();

	public static void inserirContato() {

		String nome;
		String id = null;
		int telefone;
		int op = 1;

		while(op == 1) {

			System.out.println("Nome: ");
			nome = entrada.next();
			System.out.println(nome);

			do {
				aux = 0;
				System.out.println("Telefone: ");
				telefone = entrada.nextInt();

			} while(aux == 1); 

			System.out.println("Digite sua operadora: ");
			id = entrada.next();
			if(lista.size() > 0) {
				for(int i = 0; i < lista.size(); i++) {
					if(id.equals(lista.get(i).id)) {
						aux = 1;
					}

					if(aux == 1) {
						System.out.println("Id já cadastrado!");
					}
				}
			}
			lista.add(new Telefone(nome, telefone, id));
			do {
				System.out.println("Efetuar um novo cadastro? \n1 - Sim \n2 - Nao");
				op = entrada.nextInt();

			} while(op != 1 && op != 2);
		}
	}

	public static void atualizar() {

		String nome;
		String id = null;
		int telefone;
		int posicao = 0;

		if(lista.size() > 0) {

			System.out.println("Digite o id que deseja alterar os dados: ");
			id = entrada.next();

			aux = 0;

			for(int i = 0; i < lista.size(); i++) {
				if(id.equals(lista.get(i).id)) {
					aux = 1;
					
				}
			}

			if(aux == 1) {

				System.out.println("Nome: ");
				nome = entrada.next();

				do {
					aux = 0;

					System.out.println("Telefone: ");
					telefone = entrada.nextInt();

					if(lista.size() > 0) {
						for(int i = 0; i < lista.size(); i++) {
							if(id.equals(lista.get(i).id)) {
								aux = 1;
								
							}
						}

						if(aux == 1) {
							System.out.println("Id ja cadastrado!");
						}
					}
				} while(aux == 1);
				lista.set(posicao, new Telefone(nome, telefone, id));

			}else {
				System.out.println("Não encontrado");
			}

		}else {
			System.out.println("Nenhum cadastro realizado!");
		}
	}

	public static void remover(){

		int telefone; 
		String id;
		int posicao = 0;

		if (lista.size() > 0) {

			System.out.println("Digite o id que deseja remover:");
			id = entrada.next();

			for(int i = 0 ; i < lista.size(); i++) {

				if (id.equals(lista.get(i).id)) {
					aux = 1;
					posicao = i;

					if (aux == 1){

						lista.remove(posicao);
						System.out.println("Excluído!");
					}else {
						System.out.println("Telefone não encontrado");
					}

				}

			}

		}

	}

	public static void sair() {
		System.exit(0);
	}

	public void mostrarLista() {
		for(int i = 0; i < lista.size(); i++) {
			Telefone cc = lista.get(i);
			System.out.println(cc.nome + " " + cc.telefone + " " + cc.id);
		}
	}
}

