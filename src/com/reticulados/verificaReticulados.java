package com.reticulados;

/**
 * Created by wschutz on 02/06/16.
 */

import java.util.Scanner;

public class verificaReticulados {

    public final int NUMERO_DE_PONTOS;
    private Integer[][] matrizDePesos;
    private Integer[][] matrizElemSuperiores;
    private Integer[][] matrizElemInferiores;
    private Integer[][] matrizEleMesmoNivel;
    private Integer[][] novaMatriz;
    int listaEuleriana[];
    int listaAdjacentes[], listaValorSup[], listaValorInf[], guardaVertice[], guardaChegadas[], listaAdjacentesValor1[], listaAdjacentesValor2[];
    public int numeroVisitados = getNUMERO_DE_PONTOS();
    boolean[] visitados = new boolean[numeroVisitados];
    private int guardaIteracaoElementoSup, guardaIteracaoElementoInf;
    private int verificaValoresIguais = 0;
    private int maior = 0, segundoMaior = 0, menor = 1000000, segundoMenor = 1000000, maior2 = 0, segundoMaior2 = 0,
            menor2 = 1000000, segundoMenor2 = 1000000, menor3 = 1000000;
    private int valorDeParada = 0, valorDeConferencia = 0;
    //int contaDifZero = 0;
    int contaElementValor1 = 0;
    int contaElementValor2 = 0;
    int contaIgualdade = 0;
    int grau = 0, grau2 = 0, contaImpar = 0;
    int guardaElemInf = 0;
    int guardaElemSup = 0;
    int guardaElemInf2 = 0;
    int guardaElemSup2 = 0;
    int valorFinal = 0;
    boolean aresta = false;
    int contador1;
    int contaLigacoes;
    int contaIgualdadeEleMesmoNivel = 0, contaIgualdadeEleMesmoNivel2 = 0;
    int conferirLigacaoEleMesmoNivel;
    int guardaValorListaAdj = 0;

    Scanner entrada = new Scanner(System.in);

    //Contrutor
    public verificaReticulados(int numeroDePontos) {
        setMatrizDePesos(criaMatrizDePesos(numeroDePontos, 0));
        setMatrizElemSuperiores(criaMatrizDePesos(numeroDePontos, 0));
        setMatrizElemInferiores(criaMatrizDePesos(numeroDePontos, 0));
        setNovaMatriz(criaMatrizDePesos(numeroDePontos, 0));
        NUMERO_DE_PONTOS = numeroDePontos;
        this.visitados = new boolean[numeroDePontos];
        this.matrizEleMesmoNivel = new Integer[numeroDePontos][2];
    }

    /*Método para criar a matriz vazia, para facilitar a programação no método construtor é passado a quantidade de vértices
     (numeroDePontos), através disso é criado a matriz ("setMatrizDePesos(criaMatrizDePesos(numeroDePontos, 0))").*/
    public Integer[][] criaMatrizDePesos(int tamanho, Integer tipoLigacao) {

        Integer matriz[][] = new Integer[tamanho + 1][];

        for (int i = 0; i < tamanho; i++) {
            matriz[i] = new Integer[tamanho + 1];
            for (int j = 0; j < tamanho; j++) {
                matriz[i][j] = tipoLigacao;
            }
        }
        return matriz;
    }

    public void criaMatrizEleMesmoNivel(Integer matriz[][]) {

        for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
            for (int j = 0; j < 2; j++) {
                matriz[i][j] = 0;
            }
        }
    }

    /*Método para inserir um vértice na matriz de adjacências, a inserção deve ser acompanhada do peso
     Ex: insereAresta(1, 1 ,-1) 1 ou insereAresta(1, 1 ,1), que indica se o grafo é direcinado ou não.*/
    public void insereAresta(int A, int B, int peso) {
        matrizDePesos[A][B] = peso;
        matrizDePesos[B][A] = peso;

    }

    /*Método para retirar uma aresta, para isso é informado os 2 vértices que se ligam através dela.
     Então a função coloca zero nas posições indicadas pelos vértices.*/
    public void retiraAresta(int A, int B) {
        matrizDePesos[A][B] = 0;
        matrizDePesos[B][A] = 0;
    }

    //Método para mostrar matriz de adjacências
    public void imprimeGrafo(Integer matriz[][]) {

        int tamanho = getNUMERO_DE_PONTOS();

        System.out.println("Matriz de adjacências.");
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                System.out.printf("[" + matriz[i][j] + "] ");
            }
            System.out.println("");
        }
        System.out.printf("\n");
    }

    public void imprimeMatrizElemSuperiores(Integer matriz[][]) {

        int tamanho = getNUMERO_DE_PONTOS();
        System.out.println("Matriz dos elementos Superiores:");
        for (int i = 0; i < tamanho; i++) {
            System.out.print("" + (i + 1) + " - ");
            for (int j = 0; j < tamanho; j++) {
                System.out.printf("[" + matriz[i][j] + "] ");
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");
    }

    public void imprimeMatrizElemInferiores(Integer matriz[][]) {

        int tamanho = getNUMERO_DE_PONTOS();
        System.out.println("Matriz dos elementos Inferiores:");
        for (int i = 0; i < tamanho; i++) {
            System.out.print("" + (i + 1) + " - ");
            for (int j = 0; j < tamanho; j++) {
                System.out.printf("[" + matriz[i][j] + "] ");
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");
    }

    public void imprimeMatrizEleMesmoNivel(Integer matriz[][]) {

        int tamanho = getNUMERO_DE_PONTOS();
        System.out.println("Matriz dos elementos mesmo nível:");
        for (int i = 0; i < tamanho; i++) {
            System.out.print("" + (i + 1) + " - ");
            for (int j = 0; j < 2; j++) {
                System.out.printf("[" + matriz[i][j] + "] ");
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");
    }

    public void esvaziaMatriz() {
        for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
            for (int j = 0; j < getNUMERO_DE_PONTOS(); j++) {
                matrizElemSuperiores[i][j] = 0;
                matrizElemInferiores[i][j] = 0;
            }
        }
    }

    //Busca em profundidade, recebe o vértice inicial.
    public void identElementosSuperiores() {
        for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
            guardaIteracaoElementoSup = i;
            elementoSuperior(i);
            for (int j = 0; j < getNUMERO_DE_PONTOS(); j++) {
                visitados[j] = false;
            }
        }
    }

    private void elementoSuperior(int vertice) {
        int tamanho = getNUMERO_DE_PONTOS();
        //Marca o vértice passado no parâmetro como true.
        visitados[vertice] = true;
        //Enquando for verdade vai percorrer todos os vizinhos
        while (true) {
            //Imprime vertice visitado.
            //System.out.println("Visitando vértice :" + (vertice + 1));
            matrizElemSuperiores[guardaIteracaoElementoSup][vertice] = vertice + 1;
            for (int i = 0; i < tamanho; i++) {
                if (matrizDePesos[vertice][i] != 0) {
                    //Se não tiver sido visitado e a interação for maior que o vertice avaliado
                    if (!visitados[i] && i > vertice) {
                        //Chama o método novamente e passa o vértice que não foi visitado como parâmetro.
                        elementoSuperior(i);
                    }
                }
            }
            /*Se o vértice tiver sido visitado, ou seja, se todos os vértices tiverem sido visitados,
             o comando break entra em ação.*/
            break;

        }
    }

    public void identElementosInferiores() {
        for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
            guardaIteracaoElementoInf = i;
            elementoInferior(i);
            for (int j = 0; j < getNUMERO_DE_PONTOS(); j++) {
                visitados[j] = false;
            }
        }

    }

    private void elementoInferior(int vertice) {
        int tamanho = getNUMERO_DE_PONTOS();
        //Marca o vértice passado no parâmetro como true.
        visitados[vertice] = true;
        //Enquando for verdade vai percorrer todos os vizinhos.
        while (true) {
            //Insere na matriz de Elementos inferiores.
            matrizElemInferiores[guardaIteracaoElementoInf][vertice] = vertice + 1;
            for (int i = 0; i < tamanho; i++) {
                if (matrizDePesos[i][vertice] != 0) {
                    //Se não tiver sido visitadoe for menor que o vértice visita
                    if (!visitados[i] && i < vertice) {
                        //Chama o método novamente e passa o vértice que não foi visitado como parâmetro.
                        elementoInferior(i);
                    }
                }
            }
            /*Se o vértice tiver sido visitado, ou seja, se todos os vértices tiverem sido visitados,
             o comando break entra em ação.*/
            break;
        }
    }

    public void descobreMaximaFronteira(int verifica, int vetor[], int valor1, int valor2) {
        int aux;
        maior = 0;
        for (int x = 0; x < verifica; x++) {
            aux = vetor[x];
            if (maior < aux) {
                maior = vetor[x];
            }
        }
        System.out.print("Maior Elemento da fronteira inferior de " + (valor1 + 1) + " e " + (valor2 + 1) + " --> " + "[" + maior + "]");
        System.out.print("\n\n");
    }

    public void descobreMinimaFronteira(int verifica, int vetor[], int valor1, int valor2) {
        int aux;
        for (int x = 0; x < verifica; x++) {
            if (vetor[x] != 0) {
                aux = vetor[x];
                if (menor3 > aux) {
                    menor3 = vetor[x];
                }
            }
        }
        System.out.println("Menor Elemento da fronteira superior de " + (valor1 + 1) + " e " + (valor2 + 1) + " --> " + "[" + menor3 + "]");
        System.out.print("\n");
    }

    public void ListaAdjacencia(int vertice, int priOuSeg) {
        int cont = 0;
        if (priOuSeg == 1) {
            listaAdjacentesValor1 = new int[getNUMERO_DE_PONTOS()];
            for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
                if (matrizDePesos[vertice][i] == 1) {
                    listaAdjacentesValor1[cont] = (i + 1);
                    //System.out.print("" + listaAdjacentesValor1[cont] + "  ");
                    cont++;
                }
            }
            //System.out.println("\n");

        } else {
            listaAdjacentesValor2 = new int[getNUMERO_DE_PONTOS()];
            for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
                if (matrizDePesos[vertice][i] == 1) {
                    listaAdjacentesValor2[cont] = (i + 1);
                    //System.out.print("" + listaAdjacentesValor2[cont] + "  ");
                    cont++;
                }
            }
        }
    }

    public void identFronteiraSupMinima(int valor1, int valor2) {
        listaValorSup = new int[getNUMERO_DE_PONTOS()];
        contaElementValor1 = 0;
        contaElementValor2 = 0;
        contaIgualdade = 0;
        valorDeConferencia = 0;
        contaIgualdadeEleMesmoNivel = 0;
        guardaValorListaAdj = 0;

        for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
            listaValorSup[i] = 0;
        }
        //for para contar quantos se ligam com elemento valor1
        for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
            if (matrizElemSuperiores[valor1][i] != 0 && matrizElemSuperiores[valor1][i] != (valor1 + 1)) {
                contaElementValor1++;
            }
        }
        //for para contar quantos se ligam com elemento valor2
        for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
            if (matrizElemSuperiores[valor2][i] != 0 && matrizElemSuperiores[valor2][i] != (valor2 + 1)) {
                contaElementValor2++;
            }
        }

        for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
            if ((matrizElemSuperiores[valor1][i] != 0)
                    && (matrizElemSuperiores[valor1][i] == matrizElemSuperiores[valor2][i])) {
                listaValorSup[contaIgualdade] = matrizElemSuperiores[valor1][i];
                contaIgualdade++;
            }
        }

        if (contaIgualdade == 0) {
            System.out.println("Não existe fronteira superior para os elementos " + (valor1 + 1) + " e " + (valor2 + 1) + ".\n");
        } else {
            System.out.print("Fronteira superior dos elementos " + (valor1 + 1) + " e " + (valor2 + 1) + " é --> ");
            for (int k = 0; k < contaIgualdade; k++) {
                if (listaValorSup[k] != 0) {
                    System.out.print("[" + (listaValorSup[k]) + "]");
                }
            }
            System.out.print("\n");

            ListaAdjacencia(valor1, 1);
            ListaAdjacencia(valor2, 2);
            for (int j = 0; j < getNUMERO_DE_PONTOS(); j++) {
                guardaValorListaAdj = 0;
                if (listaAdjacentesValor1[j] > (valor1 + 1) && listaAdjacentesValor1[j] > (valor2 + 1)) {
                    guardaValorListaAdj = listaAdjacentesValor1[j];
                }
                if (guardaValorListaAdj != 0) {
                    for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
                        if (guardaValorListaAdj == listaAdjacentesValor2[i]) {
                            contaIgualdadeEleMesmoNivel++;
                            //System.out.println("valor que é igual: " + listaAdjacentesValor2[i]);
                        }
                    }
                }
            }

            if (contaIgualdadeEleMesmoNivel == 2) {
                System.out.println("Não tem fronteira superior mínima.");
            } else {
                descobreMinimaFronteira(contaIgualdade, listaValorSup, valor1, valor2);
            }
        }
    }

    public void identFronteiraInfMaxima(int valor1, int valor2) {
        listaValorInf = new int[getNUMERO_DE_PONTOS()];
        contaElementValor1 = 0;
        contaElementValor2 = 0;
        contaIgualdade = 0;
        valorDeConferencia = 0;
        contaIgualdadeEleMesmoNivel = 0;
        guardaValorListaAdj =0;

        for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
            listaValorInf[i] = 0;
        }
        //for para contar quantos se ligam com elemento valor1
        for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
            if (matrizElemInferiores[valor1][i] != 0 && matrizElemInferiores[valor1][i] != (valor1 + 1)) {
                contaElementValor1++;
            }
        }
        //for para contar quantos se ligam com elemento valor2
        for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
            if (matrizElemInferiores[valor2][i] != 0 && matrizElemInferiores[valor2][i] != (valor2 + 1)) {
                contaElementValor2++;
            }
        }

        for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
            if ((matrizElemInferiores[valor1][i] != 0)
                    && (matrizElemInferiores[valor1][i] == matrizElemInferiores[valor2][i])) {
                listaValorInf[contaIgualdade] = matrizElemInferiores[valor1][i];
                contaIgualdade++;
            }
        }

        if (contaIgualdade == 0) {
            System.out.println("Não existe fronteira inferior para os elementos " + (valor1 + 1) + " e " + (valor2 + 1) + ".\n");
        } else {
            System.out.print("Fronteira inferior dos elementos " + (valor1 + 1) + " e " + (valor2 + 1) + " é --> ");
            for (int k = 0; k < contaIgualdade; k++) {
                if (listaValorInf[k] != 0) {
                    System.out.print("[" + (listaValorInf[k]) + "]");
                }
            }
            System.out.print("\n");

            ListaAdjacencia(valor1, 1);
            ListaAdjacencia(valor2, 2);
            for (int j = 0; j < getNUMERO_DE_PONTOS(); j++) {
                guardaValorListaAdj = 0;
                if (listaAdjacentesValor1[j] < (valor1 + 1) && listaAdjacentesValor1[j] < (valor2 + 1) &&
                        listaAdjacentesValor1[j] != 0 && listaAdjacentesValor1[j] != 0) {
                    guardaValorListaAdj = listaAdjacentesValor1[j];
                }
                if (guardaValorListaAdj != 0) {
                    for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
                        if (guardaValorListaAdj == listaAdjacentesValor2[i]) {
                            contaIgualdadeEleMesmoNivel++;
                            //System.out.println("valor que é igual: " + listaAdjacentesValor2[i]);
                        }
                    }
                }
            }
            if (contaIgualdadeEleMesmoNivel == 2) {
                System.out.println("Não tem fronteira inferior máxima.");
            } else {
                descobreMaximaFronteira(contaIgualdade, listaValorInf, valor1, valor2);
            }

        }

    }

    public void complementoVerificaReticulado(int valor1, int valor2) {
        listaValorInf = new int[getNUMERO_DE_PONTOS()];
        contaElementValor1 = 0;
        contaElementValor2 = 0;
        contaIgualdade = 0;
        valorDeConferencia = 0;
        contaIgualdadeEleMesmoNivel = 0;
        contaIgualdadeEleMesmoNivel2 = 0;

        ListaAdjacencia(valor1, 1);
        ListaAdjacencia(valor2, 2);
        for (int j = 0; j < getNUMERO_DE_PONTOS(); j++) {
            guardaValorListaAdj = 0;
            if (listaAdjacentesValor1[j] < (valor1 + 1) && listaAdjacentesValor1[j] < (valor2 + 1) &&
                    listaAdjacentesValor1[j] != 0 && listaAdjacentesValor1[j] != 0) {
                guardaValorListaAdj = listaAdjacentesValor1[j];
            }
            if (guardaValorListaAdj != 0) {
                for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
                    if (guardaValorListaAdj == listaAdjacentesValor2[i]) {
                        contaIgualdadeEleMesmoNivel++;
                        //System.out.println("valor que é igual: " + listaAdjacentesValor2[i]);
                    }
                }
            }
        }

        ListaAdjacencia(valor1, 1);
        ListaAdjacencia(valor2, 2);
        for (int j = 0; j < getNUMERO_DE_PONTOS(); j++) {
            guardaValorListaAdj = 0;
            if (listaAdjacentesValor1[j] > (valor1 + 1) && listaAdjacentesValor1[j] > (valor2 + 1)) {
                guardaValorListaAdj = listaAdjacentesValor1[j];
            }
            if (guardaValorListaAdj != 0) {
                for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
                    if (guardaValorListaAdj == listaAdjacentesValor2[i]) {
                        contaIgualdadeEleMesmoNivel2++;
                        //System.out.println("valor que é igual: " + listaAdjacentesValor2[i]);
                    }
                }
            }
        }

        if (contaIgualdadeEleMesmoNivel == 2 || contaIgualdadeEleMesmoNivel2 == 2) {
            valorDeParada = 1;
        }

    }

    public void verificaReticulado() {
        valorFinal = getNUMERO_DE_PONTOS();
        contador1 = 0;
        contaLigacoes = 0;
        valorDeParada = 0;

        //for para verificar se todos elementos pode ir até o último elemento
        for (int i = 0; i < valorFinal; i++) {
            if (matrizElemSuperiores[i][valorFinal - 1] == getNUMERO_DE_PONTOS()) {
                contaLigacoes++;
            }
        }

        int contaLigacoes2 = 0;

        for (int i = 0; i < valorFinal; i++) {
            if (matrizElemInferiores[i][0] == 1) {
                contaLigacoes2++;
            }
        }

        //System.out.println("Ligacao 1 "+contaLigacoes);
        //System.out.println("Ligacao 2 "+contaLigacoes2);
        // Se o primeiro elemento puder chegar em todos elementos, e o ultimo elemento puder chegar em todos elementos, continua..
        if ((contaLigacoes == valorFinal) && (contaLigacoes2 == valorFinal)) {

            for (int i = 0; i < valorFinal; i++) {
                for (int j = 0; j < valorFinal; j++) {
                    if ((contador1 != i) && (contador1 > i)) {
                        if (valorDeParada != 1) {
                            //identificaElemtentoInferioresDoisValores2506(i, contador1);
                            complementoVerificaReticulado(i, contador1);
                        }
                    }
                    contador1++;
                }
                contador1 = 0;
            }
            if (valorDeParada != 1) {
                System.out.print("\n");
                System.out.println("É reticulado.");
            } else {
                System.out.print("\n");
                System.out.println("Não é reticulado, pois não existe fronteira inferior máxima e fronteira superior mínima para "
                        + "todos os pares de elementos.");
            }
        } else {
            System.out.print("\n");
            System.out.println("Não é reticulado, pois não tem limite mínimo ou limite máximo.");
        }
    }

    public Integer[][] getMatrizDePesos() {
        return this.matrizDePesos;
    }

    public void setMatrizDePesos(Integer[][] pesos) {
        this.matrizDePesos = pesos;
    }

    public Integer[][] getMatrizElemSuperiores() {
        return this.matrizElemSuperiores;
    }

    public void setMatrizElemSuperiores(Integer[][] zero) {
        this.matrizElemSuperiores = zero;
    }

    public Integer[][] getMatrizElemInferiores() {
        return this.matrizElemInferiores;
    }

    public void setMatrizElemInferiores(Integer[][] zero) {
        this.matrizElemInferiores = zero;
    }

    public Integer[][] getNovaMatriz() {
        return novaMatriz;
    }

    public void setNovaMatriz(Integer[][] novaMatriz) {
        this.novaMatriz = novaMatriz;
    }

    public int getNUMERO_DE_PONTOS() {
        return NUMERO_DE_PONTOS;
    }

    public static void main(String args[]) {

        Scanner entrada = new Scanner(System.in);

        verificaReticulados Grafo = new verificaReticulados(6);

        /*//grafo 1
        Grafo.insereAresta(0, 1, 1);
        Grafo.insereAresta(0, 3, 1);
        Grafo.insereAresta(2, 4, 1);
        Grafo.insereAresta(1, 2, 1);
        Grafo.insereAresta(2, 6, 1);
        Grafo.insereAresta(1, 4, 1);
        Grafo.insereAresta(1, 5, 1);
        Grafo.insereAresta(3, 5, 1);
        Grafo.insereAresta(3, 6, 1);
        Grafo.insereAresta(4, 7, 1);
        Grafo.insereAresta(6, 7, 1);
        Grafo.insereAresta(0, 2, 1);
        Grafo.insereAresta(5, 7, 1);
        Grafo.insereAresta(0, 0, 1);
        Grafo.insereAresta(1, 1, 1);
        Grafo.insereAresta(2, 2, 1);
        Grafo.insereAresta(3, 3, 1);
        Grafo.insereAresta(4, 4, 1);
        Grafo.insereAresta(5, 5, 1);
        Grafo.insereAresta(6, 6, 1);
        Grafo.insereAresta(7, 7, 1);*/
        /*//grafo 2
         Grafo.insereAresta(0, 1, 1);
         Grafo.insereAresta(1, 0, 1);
         Grafo.insereAresta(0, 2, 1);
         Grafo.insereAresta(2, 0, 1);
         Grafo.insereAresta(2, 3, 1);
         Grafo.insereAresta(3, 2, 1);
         Grafo.insereAresta(1, 3, 1);
         Grafo.insereAresta(3, 1, 1);
         Grafo.insereAresta(2, 4, 1);
         Grafo.insereAresta(4, 2, 1);
         Grafo.insereAresta(3, 5, 1);
         Grafo.insereAresta(5, 3, 1);
         Grafo.insereAresta(4, 5, 1);
         Grafo.insereAresta(5, 4, 1);
         Grafo.insereAresta(3, 6, 1);
         Grafo.insereAresta(6, 3, 1);
         Grafo.insereAresta(4, 7, 1);
         Grafo.insereAresta(7, 4, 1);
         Grafo.insereAresta(6, 8, 1);
         Grafo.insereAresta(8, 6, 1);
         Grafo.insereAresta(7, 8, 1);
         Grafo.insereAresta(8, 7, 1);
         Grafo.insereAresta(5, 8, 1);
         Grafo.insereAresta(8, 5, 1);
         Grafo.insereAresta(0, 0, 1);
         Grafo.insereAresta(1, 1, 1);
         Grafo.insereAresta(2, 2, 1);
         Grafo.insereAresta(3, 3, 1);
         Grafo.insereAresta(4, 4, 1);
         Grafo.insereAresta(5, 5, 1);
         Grafo.insereAresta(6, 6, 1);
         Grafo.insereAresta(7, 7, 1);
         Grafo.insereAresta(8, 8, 1);*/
        //grafo 3
        Grafo.insereAresta(0, 1, 1);
        Grafo.insereAresta(0, 2, 1);
        Grafo.insereAresta(1, 3, 1);
        Grafo.insereAresta(2, 4, 1);
        Grafo.insereAresta(1, 4, 1);
        Grafo.insereAresta(2, 3, 1);
        Grafo.insereAresta(5, 4, 1);
        Grafo.insereAresta(3, 5, 1);
        Grafo.insereAresta(0, 0, 1);
        Grafo.insereAresta(1, 1, 1);
        Grafo.insereAresta(2, 2, 1);
        Grafo.insereAresta(3, 3, 1);
        Grafo.insereAresta(4, 4, 1);
        Grafo.insereAresta(5, 5, 1);
        /*//grafo 4
         Grafo.insereAresta(0, 1, 1);
         Grafo.insereAresta(0, 2, 1);
         Grafo.insereAresta(1, 3, 1);
         Grafo.insereAresta(1, 4, 1);
         Grafo.insereAresta(2, 3, 1);
         Grafo.insereAresta(5, 2, 1);
         Grafo.insereAresta(3, 4, 1);
         Grafo.insereAresta(3, 5, 1);
         Grafo.insereAresta(4, 6, 1);
         Grafo.insereAresta(5, 6, 1);
         Grafo.insereAresta(0, 0, 1);
         Grafo.insereAresta(1, 1, 1);
         Grafo.insereAresta(2, 2, 1);
         Grafo.insereAresta(3, 3, 1);
         Grafo.insereAresta(4, 4, 1);
         Grafo.insereAresta(5, 5, 1);
         Grafo.insereAresta(6, 6, 1);*/

        /*Grafo.insereAresta(0, 1, 1);
         Grafo.insereAresta(1, 0, 1);
         Grafo.insereAresta(1, 2, 1);
         Grafo.insereAresta(2, 1, 1);
         Grafo.insereAresta(2, 3, 1);
         Grafo.insereAresta(3, 2, 1);*/
        /*//grafo 5
         Grafo.insereAresta(0, 1, 1);
         Grafo.insereAresta(1, 0, 1);
         Grafo.insereAresta(0, 2, 1);
         Grafo.insereAresta(2, 0, 1);
         Grafo.insereAresta(1, 3, 1);
         Grafo.insereAresta(3, 1, 1);
         Grafo.insereAresta(2, 4, 1);
         Grafo.insereAresta(4, 2, 1);
         Grafo.insereAresta(3, 5, 1);
         Grafo.insereAresta(5, 3, 1);
         Grafo.insereAresta(3, 6, 1);
         Grafo.insereAresta(6, 3, 1);
         Grafo.insereAresta(4, 5, 1);
         Grafo.insereAresta(5, 4, 1);
         Grafo.insereAresta(4, 6, 1);
         Grafo.insereAresta(6, 4, 1);
         Grafo.insereAresta(5, 7, 1);
         Grafo.insereAresta(7, 5, 1);
         Grafo.insereAresta(6, 7, 1);
         Grafo.insereAresta(7, 6, 1);
         Grafo.insereAresta(0, 0, 1);
         Grafo.insereAresta(1, 1, 1);
         Grafo.insereAresta(2, 2, 1);
         Grafo.insereAresta(3, 3, 1);
         Grafo.insereAresta(4, 4, 1);
         Grafo.insereAresta(5, 5, 1);
         Grafo.insereAresta(6, 6, 1);
         Grafo.insereAresta(7, 7, 1);*/
        /*//grafo 6
         Grafo.insereAresta(0, 2, 1);
         Grafo.insereAresta(2, 0, 1);
         Grafo.insereAresta(0, 3, 1);
         Grafo.insereAresta(3, 0, 1);
         Grafo.insereAresta(1, 2, 1);
         Grafo.insereAresta(2, 1, 1);
         Grafo.insereAresta(1, 3, 1);
         Grafo.insereAresta(3, 1, 1);
         Grafo.insereAresta(3, 7, 1);
         Grafo.insereAresta(7, 3, 1);
         Grafo.insereAresta(2, 5, 1);
         Grafo.insereAresta(5, 2, 1);
         Grafo.insereAresta(4, 5, 1);
         Grafo.insereAresta(5, 4, 1);
         Grafo.insereAresta(5, 6, 1);
         Grafo.insereAresta(6, 5, 1);
         Grafo.insereAresta(5, 7, 1);
         Grafo.insereAresta(7, 5, 1);
         Grafo.insereAresta(6, 8, 1);
         Grafo.insereAresta(8, 6, 1);
         Grafo.insereAresta(8, 7, 1);
         Grafo.insereAresta(7, 8, 1);
         Grafo.insereAresta(0, 0, 1);
         Grafo.insereAresta(1, 1, 1);
         Grafo.insereAresta(2, 2, 1);
         Grafo.insereAresta(3, 3, 1);
         Grafo.insereAresta(4, 4, 1);
         Grafo.insereAresta(5, 5, 1);
         Grafo.insereAresta(6, 6, 1);
         Grafo.insereAresta(7, 7, 1);
         Grafo.insereAresta(8, 8, 1);*/
        /*Grafo.insereAresta(0, 1, 1);
         Grafo.insereAresta(1, 0, 1);
         Grafo.insereAresta(0, 4, 1);
         Grafo.insereAresta(4, 0, 1);
         Grafo.insereAresta(1, 3, 1);
         Grafo.insereAresta(3, 1, 1);
         Grafo.insereAresta(2, 3, 1);
         Grafo.insereAresta(3, 2, 1);
         Grafo.insereAresta(3, 4, 1);
         Grafo.insereAresta(4, 3, 1);
         Grafo.insereAresta(3, 5, 1);
         Grafo.insereAresta(5, 3, 1);
         Grafo.insereAresta(5, 7, 1);
         Grafo.insereAresta(7, 5, 1);
         Grafo.insereAresta(6, 7, 1);
         Grafo.insereAresta(7, 6, 1);
         Grafo.insereAresta(6, 8, 1);
         Grafo.insereAresta(8, 6, 1);
         Grafo.insereAresta(4, 6, 1);
         Grafo.insereAresta(6, 4, 1);
         Grafo.insereAresta(5, 8, 1);
         Grafo.insereAresta(8, 5, 1);
         Grafo.insereAresta(0, 0, 1);
         Grafo.insereAresta(1, 1, 1);
         Grafo.insereAresta(2, 2, 1);
         Grafo.insereAresta(3, 3, 1);
         Grafo.insereAresta(4, 4, 1);
         Grafo.insereAresta(5, 5, 1);
         Grafo.insereAresta(6, 6, 1);
         Grafo.insereAresta(7, 7, 1);
         Grafo.insereAresta(8, 8, 1);*/
        /*//grafo 7
         Grafo.insereAresta(0, 1, 1);
         Grafo.insereAresta(1, 0, 1);
         Grafo.insereAresta(0, 2, 1);
         Grafo.insereAresta(2, 0, 1);
         Grafo.insereAresta(1, 3, 1);
         Grafo.insereAresta(3, 1, 1);
         Grafo.insereAresta(2, 3, 1);
         Grafo.insereAresta(3, 2, 1);
         Grafo.insereAresta(3, 4, 1);
         Grafo.insereAresta(4, 3, 1);
         Grafo.insereAresta(4, 5, 1);
         Grafo.insereAresta(5, 4, 1);
         Grafo.insereAresta(0, 0, 1);
         Grafo.insereAresta(1, 1, 1);
         Grafo.insereAresta(2, 2, 1);
         Grafo.insereAresta(3, 3, 1);
         Grafo.insereAresta(4, 4, 1);
         Grafo.insereAresta(5, 5, 1);*/

        /*Grafo.insereAresta(0, 2, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(0, 3, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(1, 2, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(1, 3, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(2, 5, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(4, 5, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(3, 7, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(5, 6, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(5, 7, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(6, 9, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(6, 8, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(7, 8, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(7, 9, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(8, 10, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(9, 10, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(0, 0, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(1, 1, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(2, 2, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(3, 3, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(4, 4, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(5, 5, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(6, 6, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(7, 7, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(8, 8, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(9, 9, 1, Grafo.getMatrizEleMesmoNivel());
        Grafo.insereAresta(10, 10, 1, Grafo.getMatrizEleMesmoNivel());*/

        Grafo.imprimeGrafo(Grafo.getMatrizDePesos());
        Grafo.identElementosSuperiores();
        Grafo.identElementosInferiores();
        Grafo.imprimeMatrizElemSuperiores(Grafo.getMatrizElemSuperiores());
        Grafo.imprimeMatrizElemInferiores(Grafo.getMatrizElemInferiores());
        Grafo.identFronteiraSupMinima(3, 4);
        Grafo.identFronteiraInfMaxima(3, 4);
        Grafo.verificaReticulado();
    }

}
