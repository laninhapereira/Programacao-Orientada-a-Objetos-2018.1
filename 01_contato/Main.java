package projectagenda;
import javax.swing.JOptionPane;

public class Main {
	 
	 /**
	 * @param args the command line arguments
	 */
	 public static void main(String[] args) {
	 
	 Contato x = new Contato();
	 
	 int op = 0;
	 
	 while(true){
	 
	 op = Integer.parseInt(JOptionPane.showInputDialog(null, "AGENDA TELEFONICA:\n\n1. Inclusão do telefone\n2. Atualização de dados\n3. Exclusão de contato\n4. Sair\n\nDigite a opção:"));
	 
	 switch (op){
	 
	 case 1:
	 x.cadastrar();
	 break;
	 case 2:
	 x.atualizar();
	 break;
	 case 3:
	 x.remover();
	 break;
	 case 4:
	 x.sair();
	 break;
	 default:
	 JOptionPane.showMessageDialog(null, "OPÇÃO INVÁLIDA!", "AVISO", 1);
	 
	 }
	 
	 }
	 }
	 
}
