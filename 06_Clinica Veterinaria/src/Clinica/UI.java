package Clinica;

import java.util.ArrayList;
import java.util.Scanner;

class Animal{
	int idani;
	String nome;
	String especie;
	Cliente dono;
	
	public Animal(int idani, String nome, String especie, Cliente dono) {
		this.idani = idani;
		this.nome = nome;
		this.especie = especie;
		this.dono = dono;
	}

	@Override
	public String toString() {
		return "Animal [idani=" + idani + ", nome=" + nome + ", especie=" + especie + ", dono=" + dono + "]";
	}
	
}

class Cliente{
	String idcli;
	String nome;
	Repositorio<Animal> animais;
	
	public Cliente(String idcli, String nome) {
		super();
		this.idcli = idcli;
		this.nome = nome;
		animais = new Repositorio<Animal>("animais");
	}

	public String getIdCli() {
		return idcli;
	}

	public void setIdCli(String idCli) {
		this.idcli = idCli;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Repositorio<Animal> getAnimais() {
		return animais;
	}

	public String mostraranimais() {
		String saida = " ";
		for(Animal a : animais.getAll())
			saida += a.toString() + "\n";
		return saida;
	}

	@Override
	public String toString() {
		return "Cliente [idcli=" + idcli + ", nome=" + nome +"]";
	}
	
}

class Venda{
	
	String idcli;
	String idani;
	String idser;
	
	public Venda(String idcli, String idani, String idser) {
		super();
		this.idcli = idcli;
		this.idani = idani;
		this.idser = idser;
	}

	public String getIdcli() {
		return idcli;
	}

	public void setIdcli(String idcli) {
		this.idcli = idcli;
	}

	public String getIdani() {
		return idani;
	}

	public void setIdani(String idani) {
		this.idani = idani;
	}

	public String getIdser() {
		return idser;
	}

	public void setIdser(String idser) {
		this.idser = idser;
	}

	@Override
	public String toString() {
		return "Venda [idcli=" + idcli + ", idani=" + idani + ", idser=" + idser + "]";
	}
	
}

class Servico{
	public String idser;
	public float valor;
	
	public Servico(String idser, float valor) {
		super();
		this.idser = idser;
		this.valor = valor;
	}

	public String getIdser() {
		return idser;
	}

	public void setIdser(String idser) {
		this.idser = idser;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Servico [idser=" + idser + ", valor=" + valor + "]";
	}
	
}

class Controller{
	Scanner s;
	Repositorio <Cliente> clientes;
	Repositorio<Servico> servicos;
	Repositorio<Venda> vendas;
	int nanimal = 1;
	int nvendas = 1;
	float saldo = 0;
	
	public Controller() {
		super();
		s = new Scanner (System.in);
		clientes = new Repositorio<Cliente>("username");
		servicos = new Repositorio<Servico>("servicos");
		vendas = new Repositorio<Venda>("vendas");
	}
	
	public String oracle(String line){
        String ui[] = line.split(" ");
		
		
		if (ui[0].equals("help")) {
			return "addcliente, showcliente, addanimal, showanimal\n" + 
	                "showanimalcliente, vender, showvendas, \n" + 
	                "showsaldo, addservico, showservicos";
		}
		
		else if (ui[0].equals("addcliente")) {
			String cli = " ";
			for (int i = 2; i < ui.length; i++ ) {
				cli = ui[i] + " ";
				clientes.add(ui[1], new Cliente(ui[1], cli));
			}
		}
		
		else if(ui[0].equals("showcliente")) {
			String saida = " ";
			for (Cliente cli : clientes.getAll())
				saida += cli.toString() + "\n";
			return saida;
		}
		
		else if(ui[0].equals("addanimal")) {
				clientes.get(ui[1]).animais.add(ui[2], new Animal(nanimal, ui[2], ui[3], ui[1]));
			nanimal+=1;
		}
		
		else if (ui[0].equals("showanimal")) {
			 String saida = "";
			   for(Cliente cli : clientes.getAll())
				   saida += cli.mostraranimais() + "\n";
			   return saida;
		}
		
		else if (ui[0].equals("showanimalcliente")) {
			System.out.println(clientes.get(ui[1]).animais.getAll().toString());
		}
		else if (ui[0].equals("vender")) {
			clientes.get(ui[1]); 
		    clientes.get(ui[1]).animais.get(ui[2]);
		    servicos.get(ui[3]);
		    vendas.add(""+nvendas, new Venda(ui[1],ui[2],ui[3]));
		    saldo += servicos.get(ui[3]).getValor();
		    nvendas++;
		}
		
		else if (ui[0].equals("showvendas")) {
			 String saida = "";
			    for(Venda v : vendas.getAll())
				    saida += v.toString() + "\n";
			    return saida;
		}
		else if (ui[0].equals("showsaldo")) {
			System.out.println(saldo);
		}
		else if (ui[0].equals("addservico")) {
			servicos.add(ui[1], new Servico(ui[1],Float.parseFloat(ui[2])));
		}
		
		else if (ui[0].equals("showservicos")) {
			String saida = "";
			for(Servico s : servicos.getAll())
				saida += s.toString() + "\n";
			return saida;
		}
		
	}
	
}
public class UI{
	 //cria um objeto scan para ler strings do teclado
    static Scanner scan = new Scanner(System.in);
    
    //aplica um tab e retorna o texto tabulado com dois espaços
    static private String tab(String text){
        return "  " + String.join("\n  ", text.split("\n"));
    }
    
    public static void main(String[] args) {
        Controller cont = new Controller();
        System.out.println("Digite um comando ou help:");
        while(true){
            String line = scan.nextLine();
            try {
                //se não der problema, faz a pergunta e mostra a resposta
                System.out.println(tab(cont.oracle(line)));
            }catch(Exception e) {
                //se der problema, mostre o erro que deu
                System.out.println(tab(e.getMessage()));
            }
        }
    }
}