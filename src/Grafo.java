import java.util.*;

public class Grafo {
    private final Map<String, List<Estrada>> cidades;

    public Grafo() {
        cidades = new HashMap<>();
    }

    public void adicionarCidade(String cidade) {
        cidades.putIfAbsent(cidade, new ArrayList<>());
    }

    public void adicionarEstrada(String de, String para, double distancia, double custo) {
        if (!cidades.containsKey(de) || !cidades.containsKey(para)) {
            System.out.println("Uma ou ambas as cidades não existem. Adicione as cidades primeiro.");
            return;
        }
        cidades.get(de).add(new Estrada(para, distancia, custo));
        cidades.get(para).add(new Estrada(de, distancia, custo));
    }

    public List<String> calcularRota(String inicio, String fim, double custoMaximo, Set<String> bloqueadas) {
        PriorityQueue<Caminho> pq = new PriorityQueue<>(Comparator.comparingDouble(c -> c.custo));
        Map<String, Double> custos = new HashMap<>();
        Map<String, String> antecessores = new HashMap<>();
        Set<String> visitados = new HashSet<>();

        for (String cidade : cidades.keySet()) {
            custos.put(cidade, Double.MAX_VALUE);
        }
        custos.put(inicio, 0.0);
        pq.add(new Caminho(inicio, 0, 0));

        while (!pq.isEmpty()) {
            Caminho atual = pq.poll();
            String cidadeAtual = atual.destino;

            if (visitados.contains(cidadeAtual)) continue;
            visitados.add(cidadeAtual);

            if (cidadeAtual.equals(fim)) {
                break; // Encontrou o destino
            }

            for (Estrada estrada : cidades.get(cidadeAtual)) {
                if (bloqueadas.contains(cidadeAtual + "-" + estrada.destino) || bloqueadas.contains(estrada.destino + "-" + cidadeAtual)) {
                    continue;
                }

                double novoCusto = custos.get(cidadeAtual) + estrada.custo;
                if (novoCusto <= custoMaximo && novoCusto < custos.get(estrada.destino)) {
                    custos.put(estrada.destino, novoCusto);
                    antecessores.put(estrada.destino, cidadeAtual);
                    pq.add(new Caminho(estrada.destino, estrada.distancia, novoCusto));
                }
            }
        }

        List<String> caminho = new ArrayList<>();
        String cidade = fim;
        while (cidade != null) {
            caminho.add(cidade);
            cidade = antecessores.get(cidade);
        }
        Collections.reverse(caminho);

        if (caminho.size() > 1 && caminho.getFirst().equals(inicio)) {
            return caminho; // Rota encontrada
        } else {
            return Collections.emptyList(); // Nenhuma rota encontrada
        }
    }

    public List<String> calcularRotaAlternativa(String inicio, String fim) {
        PriorityQueue<Caminho> pq = new PriorityQueue<>(Comparator.comparingDouble(c -> c.distancia));
        Map<String, Double> distancias = new HashMap<>();
        Map<String, String> antecessores = new HashMap<>();
        Set<String> visitados = new HashSet<>();

        for (String cidade : cidades.keySet()) {
            distancias.put(cidade, Double.MAX_VALUE);
        }
        distancias.put(inicio, 0.0);
        pq.add(new Caminho(inicio, 0, 0));

        while (!pq.isEmpty()) {
            Caminho atual = pq.poll();
            String cidadeAtual = atual.destino;

            if (visitados.contains(cidadeAtual)) continue;
            visitados.add(cidadeAtual);

            if (cidadeAtual.equals(fim)) {
                break; // Encontrou o destino
            }

            for (Estrada estrada : cidades.get(cidadeAtual)) {
                double novaDistancia = distancias.get(cidadeAtual) + estrada.distancia;
                if (novaDistancia < distancias.get(estrada.destino)) {
                    distancias.put(estrada.destino, novaDistancia);
                    antecessores.put(estrada.destino, cidadeAtual);
                    pq.add(new Caminho(estrada.destino, novaDistancia, 0));
                }
            }
        }


        List<String> caminho = new ArrayList<>();
        String cidade = fim;
        while (cidade != null) {
            caminho.add(cidade);
            cidade = antecessores.get(cidade);
        }
        Collections.reverse(caminho);

        if (caminho.size() > 1 && caminho.getFirst().equals(inicio)) {
            return caminho; // Rota encontrada
        } else {
            return Collections.emptyList(); // Nenhuma rota encontrada
        }
    }

    // Classe Estrada
    public static class Estrada {
        String destino;
        double distancia;
        double custo;

        public Estrada(String destino, double distancia, double custo) {
            this.destino = destino;
            this.distancia = distancia;
            this.custo = custo;
        }
    }

    // Classe Caminho
    public static class Caminho {
        String destino;
        double distancia;
        double custo;

        public Caminho(String destino, double distancia, double custo) {
            this.destino = destino;
            this.distancia = distancia;
            this.custo = custo;
        }
    }
}
