import java.util.Scanner;

public class Conversor {
    private final Scanner scanner;
    private final ExchangeRateService api;

    public Conversor(Scanner scanner) {
        this.scanner = scanner;
        this.api = new ExchangeRateService();
    }

    public void executar() {
        while (true) {
            System.out.println("\n=== Conversor de Moedas ===");
            System.out.println("1 - USD para BRL");
            System.out.println("2 - EUR para BRL");
            System.out.println("3 - BRL para USD");
            System.out.println("4 - BRL para EUR");
            System.out.println("5 - USD para EUR");
            System.out.println("6 - EUR para USD");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            if (opcao == 0) break;

            String from = "", to = "";

            switch (opcao) {
                case 1 -> { from = "USD"; to = "BRL"; }
                case 2 -> { from = "EUR"; to = "BRL"; }
                case 3 -> { from = "BRL"; to = "USD"; }
                case 4 -> { from = "BRL"; to = "EUR"; }
                case 5 -> { from = "USD"; to = "EUR"; }
                case 6 -> { from = "EUR"; to = "USD"; }
                default -> {
                    System.out.println("Opção inválida.");
                    continue;
                }
            }

            System.out.print("Digite o valor para conversão: ");
            double valor = scanner.nextDouble();

            double taxa = api.obterTaxaCambio(from, to);
            if (taxa == -1) {
                System.out.println("Erro ao obter taxa de câmbio.");
                continue;
            }

            double convertido = valor * taxa;
            System.out.printf("Resultado: %.2f %s = %.2f %s\n", valor, from, convertido, to);
        }

        System.out.println("Programa encerrado.");
    }
}
