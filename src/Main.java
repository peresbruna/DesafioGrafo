import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grafo grafo = new Grafo();

        System.out.println("Adicione cidades (digite 'sair' para finalizar):");
        while (true) {
            String cidade = scanner.nextLine();
            if (cidade.equalsIgnoreCase("sair")) break;
            grafo.adicionarCidade(cidade);
        }
        System.out.println("Adicione estradas no formato 'Cidade1-Cidade2 Distância Custo' (digite 'sair' para finalizar):");
        while (true) {
            String entrada = scanner.nextLine();
            if (entrada.equalsIgnoreCase("sair")) break;
            String[] partes = entrada.split(" ");
            String[] cidades = partes[0].split("-");
            double distancia = Double.parseDouble(partes[1]);
            double custo = Double.parseDouble(partes[2]);
            grafo.adicionarEstrada(cidades[0], cidades[1], distancia, custo);
        }

        System.out.println("Digite a cidade de partida:");
        String partida = scanner.nextLine();
        System.out.println("Digite a cidade de destino:");
        String destino = scanner.nextLine();
        System.out.println("Digite o custo máximo permitido:");
        double custoMaximo = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Digite as estradas bloqueadas no formato 'Cidade1-Cidade2' (digite 'sair' para finalizar):");
        Set<String> bloqueadas = new HashSet<>();
        while (true) {
            String bloqueio = scanner.nextLine();
            if (bloqueio.equalsIgnoreCase("sair")) break;
            bloqueadas.add(bloqueio);
        }

        List<String> rota = grafo.calcularRota(partida, destino, custoMaximo, bloqueadas);
        if (!rota.isEmpty()) {
            System.out.println("Rota ótima: " + String.join(" -> ", rota));
        } else {
            System.out.println("Nenhuma rota encontrada dentro do custo máximo.");
        }

        List<String> rotaAlternativa = grafo.calcularRotaAlternativa(partida, destino);
        if (!rotaAlternativa.isEmpty()) {
            System.out.println("Rota alternativa: " + String.join(" -> ", rotaAlternativa));
        } else {
            System.out.println("Nenhuma rota alternativa encontrada.");
        }

        scanner.close();
    }
}