import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ==========================================
// CLASSE QUE REPRESENTA O PRODUTO (BEBIDA)
// ==========================================
class Produto {
    String nome;
    String categoria;
    double precoCusto;
    int quantidadeEstoque;
    double aliquotaImposto;
    double precoVendaSugerido;
}

public class Prototipo1 {

    // Lista global para simular o banco de dados do estoque na memória
    private static List<Produto> listaDeProdutos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = 0;

        // Menu interativo simples
        do {
            System.out.println("\n==========================================");
            System.out.println("   SISTEMA DE ESTOQUE TRIBUTÁVEL - ADEGA   ");
            System.out.println("==========================================");
            System.out.println("1. Cadastrar Nova Bebida");
            System.out.println("2. Exibir Relatório de Impostos no Estoque");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do teclado

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    exibirRelatorioImpostosEstoque();
                    break;
                case 3:
                    System.out.println("Encerrando o sistema... Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 3);
    }

    // ------------------------------------------
    // FUNÇÃO: Definir a alíquota de imposto por categoria
    // (Simula a lógica simplificada de um NCM)
    // ------------------------------------------
    public static double obterImpostoPorCategoria(String categoria) {
        // Usamos equalsIgnoreCase para aceitar maiúsculas/minúsculas
        if (categoria.equalsIgnoreCase("Cerveja")) {
            return 0.15; // 15% de imposto
        } else if (categoria.equalsIgnoreCase("Vinho")) {
            return 0.20; // 20% de imposto
        } else if (categoria.equalsIgnoreCase("Destilado")) {
            return 0.30; // 30% de imposto
        } else {
            return 0.18; // Padrão geral (18%)
        }
    }

    // ------------------------------------------
    // PROCEDIMENTO: Cadastrar um novo produto
    // ------------------------------------------
    public static void cadastrarProduto() {
        System.out.println("\n--- Cadastro de Bebida na Adega ---");
        
        Produto novoProd = new Produto();
        
        System.out.print("Digite o nome da bebida: ");
        novoProd.nome = scanner.nextLine();
        
        System.out.print("Digite a categoria (Cerveja, Vinho, Destilado): ");
        novoProd.categoria = scanner.nextLine();
        
        System.out.print("Digite o preço de custo unitário (R$): ");
        novoProd.precoCusto = scanner.nextDouble();
        
        System.out.print("Digite a quantidade em estoque: ");
        novoProd.quantidadeEstoque = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer
        
        // Define o imposto automaticamente com base na categoria escolhida
        novoProd.aliquotaImposto = obterImpostoPorCategoria(novoProd.categoria);
        
        // Calcula o preço de venda sugerido: Custo + Impostos + 40% de margem de lucro
        double custoComImposto = novoProd.precoCusto * (1 + novoProd.aliquotaImposto);
        novoProd.precoVendaSugerido = custoComImposto * 1.40;
        
        // Adiciona o produto na lista do sistema
        listaDeProdutos.add(novoProd);
        
        System.out.println("\n[Sucesso] Produto cadastrado!");
        System.out.println("Imposto aplicado automaticamente: " + (novoProd.aliquotaImposto * 100) + "%");
        System.out.printf("Preço de venda sugerido para garantir lucro: R$ %.2f\n", novoProd.precoVendaSugerido);
    }

    // ------------------------------------------
    // PROCEDIMENTO: Relatório de capital travado em impostos
    // ------------------------------------------
    public static void exibirRelatorioImpostosEstoque() {
        if (listaDeProdutos.isEmpty()) {
            System.out.println("\nNenhum produto cadastrado no estoque ainda.");
            return;
        }

        double valorTotalEstoque = 0;
        double totalImpostosEmbutidos = 0;
        
        System.out.println("\n=== RELATÓRIO FISCAL DO ESTOQUE ===");
        
        // Percorre cada produto na lista
        for (Produto item : listaDeProdutos) {
            double valorItem = item.precoCusto * item.quantidadeEstoque;
            double impostoItem = valorItem * item.aliquotaImposto;
            
            valorTotalEstoque += valorItem;
            totalImpostosEmbutidos += impostoItem;
            
            System.out.println("- " + item.nome + " (" + item.categoria + "): " 
                               + item.quantidadeEstoque + " un | Custo Total: R$ " + String.format("%.2f", valorItem));
        }
        
        System.out.println("------------------------------------------");
        System.out.printf("Valor total investido no estoque: R$ %.2f\n", valorTotalEstoque);
        System.out.printf("Valor aproximado retido em impostos: R$ %.2f\n", totalImpostosEmbutidos);
    }
}