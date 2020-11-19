package br.com.igti;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Cadastro {
	private static Scanner entrada = new Scanner(System.in);
	private static Scanner entradaInt = new Scanner(System.in);
	private static Funcionario f = new Funcionario();
	private static ArrayList<Funcionario> lista = new ArrayList<Funcionario>();
	private int idMax = 1;

	public void iniciar() {

		mostrarMenu();
		String opcao = entrada.nextLine();
		while (!opcao.equals("3")) {
			switch (opcao) {

			case "1":
				f.setId(idMax);
				System.out.println("Digite o nome do funcionário");
				f.setNome(entrada.nextLine());
				System.out.println("Digite o salário bruto do funcionário");
				f.setSalario(entradaInt.nextDouble());

				lista.add(f);
				f = new Funcionario();
				idMax = idMax + 1;
				mostrarMenu();
				opcao = entrada.nextLine();
				break;

			case "2":
				System.out.println("Digite o indice do funcionário que deseja imprimir o contracheque\n");
				int i = entradaInt.nextInt();
				boolean mensagem = true;
				
				for (Funcionario ff : lista) {
					if (ff.getId() == i) {
						calculoSalario(ff);
						mensagem = false;
					}
				}
				if (mensagem) {
					System.out.println("O funcionário com ID: " + i + " não existe no banco de dados");
				}
				mostrarMenu();
				opcao = entrada.nextLine();
				break;

			default:
				System.out.println("Opção Inválida");
				mostrarMenu();
				opcao = entrada.nextLine();
			}
		}
		System.out.println("Obrigado, volte sempre ! ");
		entrada.close();
		entradaInt.close();
	}

	private void mostrarMenu() {
		StringBuilder menu = new StringBuilder();
		menu.append("Bem vindo, digite uma das opções:\n");
		menu.append("1 - Para cadastrar um funcionário\n");
		menu.append("2 - Para imprimir o salário e tributos dos funcionários cadastrados\n");
		menu.append("3 - Para sair do programa\n");
		System.out.println(menu.toString());
	}

	private void calculoSalario(Funcionario f) {
		double calculoInss = calcularInss(f.getSalario());
		double calculoIrr = impostoRenda(f.getSalario(), calculoInss);
		double salarioLiquido = f.getSalario() - calculoInss - calculoIrr;
		DecimalFormat df = new DecimalFormat("0.00");

		System.out.println("Funcionário: " + f.getNome());

		System.out.println("Salário Bruto: " + df.format(f.getSalario()));

		System.out.println("Desconto INSS: " + df.format(calculoInss));

		System.out.println("Desconto Imposto de Renda: " + df.format(calculoIrr));

		System.out.println("Salário Líquido: " + df.format(salarioLiquido));
	}

	private double calcularInss(double salario) {
		double calculoInss = 0;

		if (salario <= 1045) {
			calculoInss = salario * 0.075;
		} else if (salario <= 2089.60) {
			calculoInss = ((salario - 1045) * 0.09) + 78.38;
		} else if (salario <= 3134.60) {
			calculoInss = ((salario - 2089.60) * 0.12) + 172.39;
		} else if (salario < 6101.06) {
			calculoInss = ((salario - 3134.40) * 0.14) + 297.77;
		} else {
			calculoInss = 713.10;
		}

		return calculoInss;
	}

	private double impostoRenda(double salario, double calculoInss) {
		double baseCalculo = salario - calculoInss;
		double imposto = 0;

		if (baseCalculo <= 1903.08) {
			imposto = 0.0;
		} else if (baseCalculo <= 2826.65) {
			imposto = (baseCalculo * 0.075) - 142.8;
		} else if (baseCalculo <= 3751.05) {
			imposto = (baseCalculo * 0.15) - 354.8;
		} else if (baseCalculo <= 4664.68) {
			imposto = (baseCalculo * 0.225) - 636.13;
		} else {
			imposto = (baseCalculo * 0.275) - 869.36;
		}
		return imposto;

	}

}
