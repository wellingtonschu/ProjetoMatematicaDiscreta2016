package com.reticulados;

/**
 * Created by wschutz on 02/06/16.
 */

import java.util.Scanner;
import java.util.Arrays;

public class funcoes {

    public final int NUMERO_DE_PONTOS;
    private Integer[][] matrizDePesos;
    private Integer[][] matrizElemSuperiores;
    private Integer[][] matrizElemInferiores;
    private Integer[][] novaMatriz;
    int listaEuleriana[];
    int listaAdjacentes[], listaValorSup[], listaValorInf[], guardaVertice[], guardaChegadas[];
    public int numeroVisitados = getNUMERO_DE_PONTOS();
    boolean[] visitados = new boolean[numeroVisitados];
    private int guardaIteracaoElementoSup, guardaIteracaoElementoInf;
    private int verificaValoresIguais = 0;
    private int maior = 0, segundoMaior =0, menor = 1000000, segundoMenor=1000000, maior2 = 0, segundoMaior2 = 0,
            menor2 = 1000000, segundoMenor2 = 1000000, menor3 = 1000000;
    private int valorDeParada = 0;
    //int contaDifZero = 0;
    int contaElementValor1 = 0;
    int contaElementValor2 = 0;
    int contaIgualdade = 0;
    int grau = 0, grau2 = 0,contaImpar = 0;
    int guardaElemInf = 0;
    int guardaElemSup = 0;
    int guardaElemInf2 = 0;
    int guardaElemSup2 = 0;
    int valorFinal = 0;
    boolean aresta=false;
    int contador1;
    int contaLigacoes;

    Scanner entrada = new Scanner(System.in);

    //Contrutor
    public funcoes(int numeroDePontos) {
        setMatrizDePesos(criaMatrizDePesos(numeroDePontos, 0));
        setMatrizElemSuperiores(criaMatrizDePesos(numeroDePontos, 0));
        setMatrizElemInferiores(criaMatrizDePesos(numeroDePontos, 0));
        setNovaMatriz(criaMatrizDePesos(numeroDePontos, 0));
        NUMERO_DE_PONTOS = numeroDePontos;
        this.visitados = new boolean[numeroDePontos];
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
                    //Se não tiver sido visitado
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
                    //Se não tiver sido visitado
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

    public void identificaElemtentoSuperioresDoisValores(int valor1, int valor2) {
        listaValorSup = new int[getNUMERO_DE_PONTOS()];
        verificaValoresIguais = 0;
        contaIgualdade = 0;
        contaElementValor1 = 0;
        contaElementValor2 = 0;
        menor = getNUMERO_DE_PONTOS();
        guardaElemInf = 0;
        menor2 = getNUMERO_DE_PONTOS();
        grau = 0;
        segundoMenor = getNUMERO_DE_PONTOS();
        segundoMenor2 = getNUMERO_DE_PONTOS();
        grau2 = 0;

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

        //System.out.println("v1 " + contaElementValor1);
        //System.out.println("v2 " + contaElementValor2);
        //System.out.println("igualdade " + contaIgualdade);
        if (contaIgualdade == 0) {
            System.out.println("Não existe fronteira superior para os elementos " + (valor1+1) + " e " + (valor2+1) + ".\n");
        } else {
            System.out.print("Fronteira superior dos elementos " + (valor1 + 1) + " e " + (valor2 + 1) + " é --> ");
            for (int k = 0; k < contaIgualdade; k++) {
                System.out.print("[" + (listaValorSup[k]) + "]");
            }
            System.out.print("\n");

            if (contaIgualdade > 0 && contaIgualdade == contaElementValor1 && contaIgualdade == contaElementValor2) {
                //System.out.println("tudo igual");

                //verificar o primeiro elemento inferior de cada elemento analisado.
                int auxi;
                for (int x = 0; x < getNUMERO_DE_PONTOS(); x++) {
                    auxi = matrizElemSuperiores[valor1][x];
                    if (menor > auxi && auxi != (valor1 + 1) && auxi != 0) {
                        menor = matrizElemSuperiores[valor1][x];
                    }
                }

                for (int x = 0; x < getNUMERO_DE_PONTOS(); x++) {
                    auxi = matrizElemSuperiores[valor1][x];
                    if(auxi != menor){
                        if (segundoMenor > auxi && segundoMenor > menor && auxi != (valor1 + 1) && auxi != 0) {
                            segundoMenor = matrizElemSuperiores[valor1][x];
                        }
                    }
                }
                //System.out.println("menor " + menor+" segundo menor " +segundoMenor);
                int aux2;
                for (int x = 0; x < getNUMERO_DE_PONTOS(); x++) {
                    aux2 = matrizElemSuperiores[valor2][x];
                    if (menor2 > aux2 && aux2 != (valor2 + 1) && aux2 != 0) {
                        menor2 = matrizElemSuperiores[valor2][x];
                    }
                }
                for (int x = 0; x < getNUMERO_DE_PONTOS(); x++) {
                    aux2 = matrizElemSuperiores[valor2][x];
                    if(aux2 != menor2){
                        if (segundoMenor2 > aux2 && segundoMenor2 > menor2 && aux2 != (valor2 + 1) && aux2 != 0) {
                            segundoMenor2 = matrizElemSuperiores[valor2][x];
                        }
                    }
                }
                //System.out.println("menor2 " + menor + "segundo menor "+segundoMenor2);
                if (menor == menor2 && segundoMenor == segundoMenor2) {
                    //Descobre se o maior tem fronteira superior além dele mesmo, e descobre grau de entrada
                    for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
                        if (matrizDePesos[i][menor - 1] == 1) {
                            grau++;
                        }
                        if (matrizDePesos[i][segundoMenor - 1] == 1) {
                            grau2++;
                        }
                        if (matrizElemSuperiores[menor - 1][i] != 0) {
                            guardaElemSup++;
                        }
                        if (matrizElemSuperiores[segundoMenor - 1][i] != 0) {
                            guardaElemSup2++;
                        }
                    }
                }
                //System.out.println("grau " + grau);
                //System.out.println("Incidencias "+guardaElemSup);
                if ((grau % 2 == 0 && grau2 % 2 != 0) || (grau % 2 != 0 && grau2 % 2 == 0) && guardaElemSup > 1) {
                    descobreMinimaFronteira(contaIgualdade, listaValorSup, valor1, valor2);
                } else {
                    if (menor == getNUMERO_DE_PONTOS()) {
                        descobreMinimaFronteira(contaIgualdade, listaValorSup, valor1, valor2);
                    } else {
                        System.out.println("Não tem fronteira superior mínima.");
                    }
                }

            } else {
                descobreMinimaFronteira(contaIgualdade, listaValorSup, valor1, valor2);
            }
        }
    }

    public void identificaElemtentoInferioresDoisValores1706(int valor1, int valor2) {

        listaValorInf = new int[getNUMERO_DE_PONTOS()];
        verificaValoresIguais = 0;
        contaIgualdade = 0;
        contaElementValor1 = 0;
        contaElementValor2 = 0;
        maior = 0;
        guardaElemInf = 0;
        maior2 = 0;
        grau = 0;
        grau2 = 0;
        segundoMaior = 0;
        segundoMaior2 = 0;

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

        //System.out.println("v1 " + contaElementValor1);
        //System.out.println("v2 " + contaElementValor2);
        //System.out.println("igualdade " + contaIgualdade);
        if (contaIgualdade == 0) {
            System.out.println("Não existe fronteira inferior para os elementos " + (valor1+1) + " e " + (valor2+1) + ".\n");
        } else {
            System.out.print("Fronteira inferior dos elementos " + (valor1 + 1) + " e " + (valor2 + 1) + " é --> ");
            for (int k = 0; k < contaIgualdade; k++) {
                System.out.print("[" + (listaValorInf[k]) + "]");
            }
            System.out.print("\n");

            if (contaIgualdade > 0 && contaIgualdade == contaElementValor1 && contaIgualdade == contaElementValor2) {
                //System.out.println("tudo igual");

                //verificar o primeiro elemento inferior de cada elemento analisado.
                int aux;
                for (int x = 0; x < getNUMERO_DE_PONTOS(); x++) {
                    aux = matrizElemInferiores[valor1][x];
                    if (maior < aux && aux != (valor1 + 1)) {
                        maior = matrizElemInferiores[valor1][x];
                    }
                }

                for (int x = 0; x < getNUMERO_DE_PONTOS(); x++) {
                    aux = matrizElemInferiores[valor1][x];
                    if(aux != maior){
                        if (segundoMaior < aux && segundoMaior < maior && aux != (valor1 + 1) && aux != 0) {
                            segundoMaior = matrizElemInferiores[valor1][x];
                        }
                    }
                }
                //System.out.println("maior " + maior+"segundo maior "+segundoMaior);
                int aux2;
                for (int x = 0; x < getNUMERO_DE_PONTOS(); x++) {
                    aux2 = matrizElemInferiores[valor2][x];
                    if (maior2 < aux2 && aux2 != (valor2 + 1)) {
                        maior2 = matrizElemInferiores[valor2][x];
                    }
                }

                for (int x = 0; x < getNUMERO_DE_PONTOS(); x++) {
                    aux2 = matrizElemInferiores[valor2][x];
                    if(aux2 != maior2){
                        if (segundoMaior2 < aux2 && segundoMaior2 < maior2 && aux2 != (valor2 + 1) && aux2 != 0) {
                            segundoMaior2 = matrizElemInferiores[valor2][x];
                        }
                    }
                }
                //System.out.println("maior2 " + maior2+"segundo maior "+segundoMaior2);
                if (maior == maior2) {
                    //Descobre se o maior tem fronteira inferior além dele mesmo, e descobre grau de entrada
                    for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
                        if (matrizDePesos[i][maior - 1] == 1) {
                            grau++;
                        }
                        if (matrizElemInferiores[maior - 1][i] != 0) {
                            guardaElemInf++;
                        }
                    }
                }
                //System.out.println("grau " + grau);
                //System.out.println("Incidencias "+guardaElemInf);

                if ((grau % 2 == 0 && grau2 % 2 != 0) || (grau % 2 != 0 && grau2 % 2 == 0) && guardaElemInf > 1) {
                    descobreMaximaFronteira(contaIgualdade, listaValorInf, valor1, valor2);
                } else {
                    if (maior == 1) {
                        descobreMaximaFronteira(contaIgualdade, listaValorInf, valor1, valor2);
                    } else {
                        System.out.println("Não tem fronteira inferior máxima.");
                    }
                }

            } else {
                descobreMaximaFronteira(contaIgualdade, listaValorInf, valor1, valor2);
            }
        }
    }

    public void identificaElemtentoInferioresDoisValores2506(int valor1, int valor2) {

        listaValorInf = new int[getNUMERO_DE_PONTOS()];
        verificaValoresIguais = 0;
        contaIgualdade = 0;
        contaElementValor1 = 0;
        contaElementValor2 = 0;
        maior = 0;
        guardaElemInf = 0;
        maior2 = 0;
        grau = 0;

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

        //System.out.println("v1 " + contaElementValor1);
        //System.out.println("v2 " + contaElementValor2);
        //System.out.println("igualdade " + contaIgualdade);
        if (contaIgualdade == 0) //System.out.println("Não existe fronteira inferior para os elementos " + valor1 + " e " + valor2 + ".");
        {
            valorDeParada = 1;
        } else {
            /*System.out.print("Fronteira inferior dos elementos " + (valor1 + 1) + " e " + (valor2 + 1) + " é --> ");
             for (int k = 0; k < contaIgualdade; k++) {
             System.out.print("[" + (listaValorInf[k]) + "]");
             }
             System.out.println("\n");*/

            if (contaIgualdade > 0 && contaIgualdade == contaElementValor1 && contaIgualdade == contaElementValor2) {
                //System.out.println("tudo igual");
                //verificar o primeiro elemento inferior de cada elemento analisado.
                int aux;
                for (int x = 0; x < getNUMERO_DE_PONTOS(); x++) {
                    aux = matrizElemInferiores[valor1][x];
                    if (maior < aux && aux != (valor1 + 1)) {
                        maior = matrizElemInferiores[valor1][x];
                    }
                }
                //System.out.println("maior " + maior);
                int aux2;
                for (int x = 0; x < getNUMERO_DE_PONTOS(); x++) {
                    aux2 = matrizElemInferiores[valor2][x];
                    if (maior2 < aux2 && aux2 != (valor2 + 1)) {
                        maior2 = matrizElemInferiores[valor2][x];
                    }
                }
                //System.out.println("maior2 " + maior2);

                if (maior == maior2) {
                    //Descobre se o maior tem fronteira inferior além dele mesmo, e descobre grau de entrada
                    for (int i = 0; i < getNUMERO_DE_PONTOS(); i++) {
                        if (matrizDePesos[i][maior - 1] == 1) {
                            grau++;
                        }
                        if (matrizElemInferiores[maior - 1][i] != 0) {
                            guardaElemInf++;
                        }
                    }
                }
                //System.out.println("grau " + grau);
                //System.out.println("Incidencias "+guardaElemInf);

                if (grau % 2 != 0 && guardaElemInf > 1) {
                    //descobreMaximaFronteira(contaIgualdade, listaValorInf, valor1, valor2);
                } else {
                    if (maior == 1) {
                        //descobreMaximaFronteira(contaIgualdade, listaValorInf, valor1, valor2);
                    } else //System.out.println("Não tem fronteira inferior máxima.");
                    {
                        valorDeParada = 1;
                    }
                }

            } else {
                //descobreMaximaFronteira(contaIgualdade, listaValorInf, valor1, valor2);
            }
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
        System.out.print("Maior Elemento da fronteira inferior de " + (valor1 + 1) + " e " + (valor2 + 1) + " --> " + "["+ maior +"]");
        System.out.print("\n\n");
    }

    public void descobreMinimaFronteira(int verifica, int vetor[], int valor1, int valor2) {
        int aux;
        for (int x = 0; x < verifica; x++) {
            aux = vetor[x];
            if (menor3 > aux) {
                menor3 = vetor[x];
            }
        }
        System.out.println("Menor Elemento da fronteira superior de " + (valor1 + 1) + " e " + (valor2 + 1) + " --> " + "["+ menor3 +"]");
        System.out.print("\n");
    }

    public void verificaReticulado() {
        valorFinal = getNUMERO_DE_PONTOS();
        contador1 = 0;
        contaLigacoes = 0;

        for (int i = 0; i < valorFinal; i++) {
            if (matrizElemSuperiores[0][i] == (i + 1)) {
                contaLigacoes++;
            }
        }

        int contaLigacoes2 = 0;

        for (int i = 0; i < valorFinal; i++) {
            if (matrizElemInferiores[valorFinal - 1][i] == (i + 1)) {
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
                            //System.out.println("Imprime valor " + i + " -- " + contador1);
                            //identificaElemtentoInferioresDoisValoresModificado(i, contador1);
                            identificaElemtentoInferioresDoisValores2506(i, contador1);
                        }
                    }
                    contador1++;
                }
                contador1 = 0;
            }
            if (valorDeParada != 1) {
                System.out.println("É reticulado.");
            } else {
                System.out.println("Não é reticulado, pois não existe fronteira inferior máxima e fronteira superior mínima para "
                        + "todos os pares de elementos.");
            }
        } else {
            System.out.println("Não é reticulado, pois não é possível que o todos elementos consigam chegar até no último elemento, ou até no primeiro elemento.");
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

        funcoes Grafo = new funcoes(6);

        /*//grafo 1
         Grafo.insereAresta(0, 1, 1);
         Grafo.insereAresta(1, 0, 1);
         Grafo.insereAresta(0, 3, 1);
         Grafo.insereAresta(3, 0, 1);
         Grafo.insereAresta(2, 4, 1);
         Grafo.insereAresta(4, 2, 1);
         Grafo.insereAresta(2, 6, 1);
         Grafo.insereAresta(6, 2, 1);
         Grafo.insereAresta(1, 4, 1);
         Grafo.insereAresta(4, 1, 1);
         Grafo.insereAresta(1, 5, 1);
         Grafo.insereAresta(5, 1, 1);
         Grafo.insereAresta(3, 5, 1);
         Grafo.insereAresta(5, 3, 1);
         Grafo.insereAresta(3, 6, 1);
         Grafo.insereAresta(6, 3, 1);
         Grafo.insereAresta(4, 7, 1);
         Grafo.insereAresta(7, 4, 1);
         Grafo.insereAresta(6, 7, 1);
         Grafo.insereAresta(7, 6, 1);
         Grafo.insereAresta(0, 2, 1);
         Grafo.insereAresta(2, 0, 1);
         Grafo.insereAresta(5, 7, 1);
         Grafo.insereAresta(7, 5, 1);
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
        /*//grafo 3
        Grafo.insereAresta(0, 1, 1);
        Grafo.insereAresta(1, 0, 1);
        Grafo.insereAresta(0, 2, 1);
        Grafo.insereAresta(2, 0, 1);
        Grafo.insereAresta(1, 3, 1);
        Grafo.insereAresta(3, 1, 1);
        Grafo.insereAresta(2, 4, 1);
        Grafo.insereAresta(4, 2, 1);
        Grafo.insereAresta(1, 4, 1);
        Grafo.insereAresta(4, 1, 1);
        Grafo.insereAresta(2, 3, 1);
        Grafo.insereAresta(3, 2, 1);
        Grafo.insereAresta(5, 4, 1);
        Grafo.insereAresta(4, 5, 1);
        Grafo.insereAresta(3, 5, 1);
        Grafo.insereAresta(5, 3, 1);
        Grafo.insereAresta(0, 0, 1);
        Grafo.insereAresta(1, 1, 1);
        Grafo.insereAresta(2, 2, 1);
        Grafo.insereAresta(3, 3, 1);
        Grafo.insereAresta(4, 4, 1);
        Grafo.insereAresta(5, 5, 1);*/
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

        //grafo 7
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
        Grafo.insereAresta(5, 5, 1);

        Grafo.imprimeGrafo(Grafo.getMatrizDePesos());
        Grafo.identElementosSuperiores();
        Grafo.identElementosInferiores();
        Grafo.imprimeMatrizElemSuperiores(Grafo.getMatrizElemSuperiores());
        Grafo.imprimeMatrizElemInferiores(Grafo.getMatrizElemInferiores());
        Grafo.identificaElemtentoSuperioresDoisValores(1, 2);
        Grafo.identificaElemtentoInferioresDoisValores1706(1, 2);
        Grafo.verificaReticulado();
        //Grafo.criaListas(Grafo.getMatrizDePesos(),1);
        //Grafo.imprimeGrafo(Grafo.getMatrizDePesos());
        //Grafo.verifica(Grafo.getMatrizDePesos());

    }

    /**
     * @return the novaMatriz
     */
}