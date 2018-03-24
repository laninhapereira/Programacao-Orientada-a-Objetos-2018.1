package projectagenda;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Contato {
	
	public String nome;
	static ArrayList<Telefone> dados = new ArrayList<Telefone>();
	static int acho;


	public static void cadastrar() {
		 
		 int op = 1;
		 String nome;
		 int tel;
		 int foneid;
		 
		 while (op == 1) {
		 
		 nome = JOptionPane.showInputDialog(null, "Digite o nome por favor","Cadastro",3);
		 do {
		 acho = 0;
		 tel = Integer.parseInt(JOptionPane.showInputDialog(null, "Agora o telefone\n(Somente números)","Cadastro",3));
		 
		 if (dados.size() > 0)
		 for(int i=0 ;i < dados.size(); i++) {
		 if(tel == dados.get(i).tel)
		 acho = 1;
		 }
		 if(acho == 1)
		 JOptionPane.showMessageDialog(null, "Telefone já cadastrado!","Aviso",1);
			 
		 } while (acho == 1);
			 foneid = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite sua identificação","Cadastro",3));
			 dados.add(new Telefone(nome, tel, foneid));
		do {
			 op = Integer.parseInt(JOptionPane.showInputDialog(null,"Deseja efetuar outro cadastro?\n\n1. Sim\n2. Não\n\nDigite sua opção:"));
		 }
		 
		 while(op != 1 && op !=2);
		 
		 }
		 
		 }
	
	
	public static void atualizar() {
		 
		 String nome;
		 int tel;
		 int foneid = 0;
		 int posicao = 0;
		 
		 if (dados.size() > 0) {
		 
		 tel = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o id da pessoa que deseja atualizar os dados:\n(Somente números)"));
		 
		 acho = 0;
		 for(int i=0 ;i < dados.size(); i++) {
		 if(foneid == dados.get(i).foneid) {
		 acho = 1;
		 posicao = i;
		 }
		 }// fim da busca
		 if(acho == 1) {
		 nome = JOptionPane.showInputDialog(null, "Nome do cliente?","Cadastro",3);
		 do {
		 acho = 0;
		 tel = Integer.parseInt(JOptionPane.showInputDialog(null, "Telefone do cliente?\n(Somente números)","Cadastro",3));
		 if (dados.size() > 0)
		 for(int i=0 ;i < dados.size(); i++) {
		 if(tel == dados.get(i).tel)
		 acho = 1;
		 }
		 if(acho == 1)
		 JOptionPane.showMessageDialog(null, "Id já cadastrado!","Aviso",1);
		 }while(acho == 1);
		 
		 dados.set(posicao, new Telefone(nome, tel, foneid));
		 
		 }
		 else
		 JOptionPane.showMessageDialog(null, "Id não encontrado!","Aviso",1);
		 } else {
		 JOptionPane.showMessageDialog(null, "Nenhum cadastro realizado até o momento!","Aviso",1);
		 }
		 }
		 
		 
	
	static void remover(){
		 
		 int tel, foneid = 0;
		 int posicao = 0;
		 
		 if (dados.size() > 0) {
		 tel = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o id da pessoa que deseja excluir os dados:\n(Somente números)"));
		 for(int i=0 ;i < dados.size(); i++)
		 if(foneid == dados.get(i).foneid) {
		 acho = 1;
		 posicao = i;
		 }
		 
		 if (acho==1){
		 
		 int conf;
		 conf = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir?");
		 
		 if(conf == 0) {
		 dados.remove(posicao);
		 JOptionPane.showMessageDialog(null, "Telefone excluído", "Aviso", 1);
		 }
		 
		 }else
		 JOptionPane.showMessageDialog(null, "Telefone não encontrado!","Aviso",1);
		 }
		 else{
		 JOptionPane.showMessageDialog(null, "Ainda nenhum cadastro foi realizado!","Aviso",1);
		 }
		 
		 }//fim do metodo remover
	
	
	static void sair(){

		 System.exit(0);
		 }
	// fim do método sair
	
	
}


	
	
