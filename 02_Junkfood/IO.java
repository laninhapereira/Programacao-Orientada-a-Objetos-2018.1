package Junkfood;

import java.util.ArrayList;
import java.util.Scanner;

//classe espiral onde criamos nosso espiral e objetos que estarão contidos nele
class Espiral{
	public String nome = " ";
	public int quantidade = 0;
	public float preco = 0f;
	
	public String toString() {
		return "[Produto: "+ nome + " : "+ quantidade + " Uni" + " : " + preco + " RS]";
	}
}

//nossa classe máquina onde criamos nosso arraylist de espirais 
class Maquina{
	double saldo = 0;
	double lucro = 0;
	double troco = 0;
	ArrayList<Espiral> espirais;
	
	public Maquina(int qtdEspirais, int maxProdutos){
		this.espirais = new ArrayList<Espiral>();
		for(int i = 0; i < qtdEspirais; i++)
			this.espirais.add(new Espiral());
	}
	

	public String toString() {
		String saida = "";
		for(int i = 0; i < espirais.size(); i++)
			saida += " " + espirais.get(i).toString() +" saldo: "+ saldo +" troco: "+ troco +" lucro: "+ lucro +"\n";
		return saida;
	}
	
	//metodo onde estamos adicionando produtos e suas caracteristicas em nosso espiral
	public void adicionar(int indice, String nome, int qtd, float preco) {
		espirais.get(indice).nome = nome;
		espirais.get(indice).quantidade = qtd;
		espirais.get(indice).preco = preco;
		
	}
	//metodo onde estamos comprando algum produto contido no espiral (que ja contém um possível problema incluso - o produto não existir ou o saldo não existir.
	public void comprar(String nome) {
		for(Espiral e : espirais) {
			if (e.nome.equals(nome)) 
				if(saldo >= e.preco) {
					e.quantidade--;
				lucro = e.preco + lucro;
				saldo = saldo - e.preco;
				troco = saldo;
				saldo = troco - saldo;
				
				}
				else
					throw new RuntimeException("Produto não existe ou saldo não existe");
		        }
			
	}
	// metodo onde alteramos as informações já inclusas no nosso espiral
	public void alterar(int indice, String nome, int qtd, float vl){
		this.espirais.get(indice).nome = nome;
		this.espirais.get(indice).quantidade = qtd;
		this.espirais.get(indice).preco = vl;
	}
	
	//onde removemos o que foi adicionado/alterado no espiral 
	public void remover(int indice) {
		this.espirais.get(indice).nome = "-";
		this.espirais.get(indice).quantidade = 0;
		this.espirais.get(indice).preco = 0f;
	}
	//onde inserimos o nosso saldo
	public void inserirDinheiro(float vl ){
		saldo += vl;
		
	}
}

//nossa classe controle que esta responsavel por fazer todo - como o proprio nome diz -, controle da nossa maquina
class Controller{
    Maquina maq;
    static final int DEFAULT_ESPIRAIS = 3;
    static final int DEFAULT_MAX = 6;
    Scanner sca;
    public Controller() {
        maq = new Maquina(DEFAULT_ESPIRAIS, DEFAULT_MAX);
        sca = new Scanner(System.in);
    }
    
    //comando que é responsável por tranformar uma string em float
    private float toFloat(String s) {
        return Float.parseFloat(s);
    }
    
    //função que faz a pergunnta e mostra a resposta 
    public String oracle(String line){
        String ui[] = line.split(" ");
        //aqui temos as perguntas que serão feitas ao usuario e o que será retornado ou adicionado em cada pergunta
        if(ui[0].equals("socorro@dels"))
            return "mostrar, inserir _espirais _maximo";
        else if(ui[0].equals("inserir"))
            maq = new Maquina(Integer.parseInt(ui[1]), Integer.parseInt(ui[2]));
        else if(ui[0].equals("mostrar"))
            return "" + maq;
        else if(ui[0].equals("adicionar"))
        	maq.adicionar(Integer.parseInt(ui[1]),ui[2], Integer.parseInt(ui[3]), Float.parseFloat(ui[4]) );
        else if(ui[0].equals("remover"))
			maq.remover(Integer.parseInt(ui[1]));
        else if (ui[0].equals("alterar"))
			maq.alterar(Integer.parseInt(ui[1]),ui[2], Integer.parseInt(ui[3]), Float.parseFloat(ui[4]));
        else if(ui[0].equals("inserirDinheiro"))
			maq.inserirDinheiro(Float.parseFloat(ui[1]));
        else if(ui[0].equals("comprar"))
			maq.comprar(ui[1]);
        else
            return "comando invalido";
        return "done";
    }
}

public class IO {
    //cria um scan que vai ler o que for digitado
    static Scanner scan = new Scanner(System.in);
    
    //aplica um tab e retorna o texto tabulado com dois espaços
    static private String tab(String text){
        return "  " + String.join("\n  ", text.split("\n"));
    }
    
    public static void main(String[] args) {
        Controller cont = new Controller();
        System.out.println("Digite um comando ou socorro@dels:");
        while(true){
            String line = scan.nextLine();
            try {
                //se não der erro, só vai direto e faz as perguntas
                System.out.println(tab(cont.oracle(line)));
            }catch(Exception e) {
                //mostra o problema caso dê erro
                System.out.println(tab(e.getMessage()));
            }
        }
    }
}

