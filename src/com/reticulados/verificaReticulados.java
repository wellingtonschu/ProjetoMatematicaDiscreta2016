package com.reticulados;

public class verificaReticulados {

    private final int numNodos;

    private Integer[][] matrizCriada;
    private Integer[][] matrizElementoSuperiores;
    private Integer[][] matrizElementoInferiores;

    private int listaValorSup[];
    private int listaValorInf[];
    private int listaAdjacentesValor01[];
    private int listaAdjacentesValor02[];

    private int numeroVisitados = getNumNodos();

    private boolean[] visitados = new boolean[numeroVisitados];

    private int armazenaIteracaoElementoSup;
    private int armazenaIteracaoElementoInf;

    private int maior = 0;
    private int menor = 1000000;

    private int valorDeParada = 0;

    private int contadorIgualdade = 0;

    private int valorFinal = 0;

    private int contador01;
    private int contadorLigacoes01;
    private int contadorLigacoes02;

    private int contadorIgualdadeElementosMesmoNivel01 = 0;
    private int contadorIgualdadeElementosMesmoNivel02 = 0;

    private int armazenaValorListaAdjacente = 0;

    public verificaReticulados(int numeroDeNodos) {

        setMatrizCriada(criaMatriz(numeroDeNodos, 0));
        setMatrizElementoSuperiores(criaMatriz(numeroDeNodos, 0));
        setMatrizElementoInferiores(criaMatriz(numeroDeNodos, 0));
        numNodos = numeroDeNodos;
        this.visitados = new boolean[numeroDeNodos];

    }

    private Integer[][] criaMatriz(int tamanho, Integer tipoLigacao) {

        Integer matriz[][] = new Integer[tamanho + 1][];

        for (int i = 0; i < tamanho; i++) {

            matriz[i] = new Integer[tamanho + 1];

            for (int j = 0; j < tamanho; j++) {

                matriz[i][j] = tipoLigacao;

            }

        }

        return matriz;

    }

    public void insereAresta(int A, int B, int peso) {

        matrizCriada[A][B] = peso;
        matrizCriada[B][A] = peso;

    }

    public void limpaMatriz() {

        for (int i = 0; i < getNumNodos(); i++) {

            for (int j = 0; j < getNumNodos(); j++) {

                matrizElementoSuperiores[i][j] = 0;
                matrizElementoInferiores[i][j] = 0;

            }

        }

    }

    public void identificaElementosSuperiores() {

        for (int i = 0; i < getNumNodos(); i++) {

            armazenaIteracaoElementoSup = i;
            elementoSuperior(i);

            for (int j = 0; j < getNumNodos(); j++) {

                visitados[j] = false;

            }

        }

    }

    private void elementoSuperior(int vertice) {

        visitados[vertice] = true;

        while (true) {

            matrizElementoSuperiores[armazenaIteracaoElementoSup][vertice] = vertice + 1;

            for (int i = 0; i < getNumNodos(); i++) {

                if (matrizCriada[vertice][i] != 0) {

                    if (!visitados[i] && i > vertice) {

                        elementoSuperior(i);

                    }

                }

            }

            break;

        }

    }

    public void identificaElementosInferiores() {

        for (int i = 0; i < getNumNodos(); i++) {

            armazenaIteracaoElementoInf = i;
            elementoInferior(i);

            for (int j = 0; j < getNumNodos(); j++) {

                visitados[j] = false;

            }

        }

    }

    private void elementoInferior(int vertice) {

        visitados[vertice] = true;

        while (true) {

            matrizElementoInferiores[armazenaIteracaoElementoInf][vertice] = vertice + 1;

            for (int i = 0; i < getNumNodos(); i++) {

                if (matrizCriada[i][vertice] != 0) {

                    if (!visitados[i] && i < vertice) {

                        elementoInferior(i);

                    }

                }

            }

            break;

        }

    }

    private void identificaMaximaFronteira(int verifica, int vetor[], int valor01, int valor02) {

        int auxiliar;
        maior = 0;

        for (int x = 0; x < verifica; x++) {

            auxiliar = vetor[x];

            if (maior < auxiliar) {

                maior = vetor[x];

            }

        }

        System.out.print("Maior Elemento da fronteira inferior de " + (valor01 + 1) + " e " + (valor02 + 1) + " : " + maior);
        System.out.print("\n\n");

    }

    private void identificaMinimaFronteira(int verifica, int vetor[], int valor01, int valor02) {

        int auxiliar;

        for (int x = 0; x < verifica; x++) {

            if (vetor[x] != 0) {

                auxiliar = vetor[x];

                if (menor > auxiliar) {

                    menor = vetor[x];

                }

            }

        }

        System.out.println("Menor Elemento da fronteira superior de " + (valor01 + 1) + " e " + (valor02 + 1) + " : " + menor);
        System.out.print("\n");

    }

    private void ListaAdjacencia(int vertice, int primeiroOuSegundo) {

        int contador = 0;

        if (primeiroOuSegundo == 1) {

            listaAdjacentesValor01 = new int[getNumNodos()];

            for (int i = 0; i < getNumNodos(); i++) {

                if (matrizCriada[vertice][i] == 1) {

                    listaAdjacentesValor01[contador] = (i + 1);
                    contador++;

                }

            }

        } else {

            listaAdjacentesValor02 = new int[getNumNodos()];

            for (int i = 0; i < getNumNodos(); i++) {

                if (matrizCriada[vertice][i] == 1) {

                    listaAdjacentesValor02[contador] = (i + 1);
                    contador++;

                }

            }

        }

    }

    public void identificaFronteiraSuperiorMinima(int valor01, int valor02) {

        listaValorSup = new int[getNumNodos()];

        contadorIgualdade = 0;
        contadorIgualdadeElementosMesmoNivel01 = 0;
        armazenaValorListaAdjacente = 0;

        for (int i = 0; i < getNumNodos(); i++) {

            listaValorSup[i] = 0;

        }

        for (int i = 0; i < getNumNodos(); i++) {

            if ((matrizElementoSuperiores[valor01][i] != 0) && (matrizElementoSuperiores[valor01][i] == matrizElementoSuperiores[valor02][i])) {

                listaValorSup[contadorIgualdade] = matrizElementoSuperiores[valor01][i];
                contadorIgualdade++;

            }

        }

        if (contadorIgualdade == 0) {

            System.out.println("Não existe fronteira superior para os elementos " + (valor01 + 1) + " e " + (valor02 + 1) + ".\n");

        } else {

            System.out.print("Fronteira superior dos elementos " + (valor01 + 1) + " e " + (valor02 + 1) + " : ");

            for (int k = 0; k < contadorIgualdade; k++) {

                if (listaValorSup[k] != 0) {

                    System.out.print(listaValorSup[k]);

                }

            }

            System.out.print("\n");

            ListaAdjacencia(valor01, 1);
            ListaAdjacencia(valor02, 2);

            for (int j = 0; j < getNumNodos(); j++) {

                armazenaValorListaAdjacente = 0;

                if (listaAdjacentesValor01[j] > (valor01 + 1) && listaAdjacentesValor01[j] > (valor02 + 1)) {

                    armazenaValorListaAdjacente = listaAdjacentesValor01[j];

                }

                if (armazenaValorListaAdjacente != 0) {

                    for (int i = 0; i < getNumNodos(); i++) {

                        if (armazenaValorListaAdjacente == listaAdjacentesValor02[i]) {

                            contadorIgualdadeElementosMesmoNivel01++;

                        }

                    }

                }

            }

            if (contadorIgualdadeElementosMesmoNivel01 == 2) {

                System.out.println("Não possui fronteira superior mínima.");

            } else {

                identificaMinimaFronteira(contadorIgualdade, listaValorSup, valor01, valor02);

            }

        }

    }

    public void identificaFronteiraInferiorMaxima(int valor01, int valor02) {

        listaValorInf = new int[getNumNodos()];

        contadorIgualdade = 0;
        contadorIgualdadeElementosMesmoNivel01 = 0;
        armazenaValorListaAdjacente =0;

        for (int i = 0; i < getNumNodos(); i++) {

            listaValorInf[i] = 0;

        }

        for (int i = 0; i < getNumNodos(); i++) {

            if ((matrizElementoInferiores[valor01][i] != 0) && (matrizElementoInferiores[valor01][i] == matrizElementoInferiores[valor02][i])) {

                listaValorInf[contadorIgualdade] = matrizElementoInferiores[valor01][i];
                contadorIgualdade++;

            }

        }

        if (contadorIgualdade == 0) {

            System.out.println("Não existe fronteira inferior para os elementos " + (valor01 + 1) + " e " + (valor02 + 1) + ".\n");

        } else {

            System.out.print("Fronteira inferior dos elementos " + (valor01 + 1) + " e " + (valor02 + 1) + ": ");

            for (int k = 0; k < contadorIgualdade; k++) {

                if (listaValorInf[k] != 0) {

                    System.out.print(listaValorInf[k]);

                }

            }

            System.out.print("\n");

            ListaAdjacencia(valor01, 1);
            ListaAdjacencia(valor02, 2);

            for (int j = 0; j < getNumNodos(); j++) {

                armazenaValorListaAdjacente = 0;

                if (listaAdjacentesValor01[j] < (valor01 + 1) && listaAdjacentesValor01[j] < (valor02 + 1) && listaAdjacentesValor01[j] != 0 && listaAdjacentesValor01[j] != 0) {

                    armazenaValorListaAdjacente = listaAdjacentesValor01[j];

                }

                if (armazenaValorListaAdjacente != 0) {

                    for (int i = 0; i < getNumNodos(); i++) {

                        if (armazenaValorListaAdjacente == listaAdjacentesValor02[i]) {

                            contadorIgualdadeElementosMesmoNivel01++;

                        }

                    }

                }

            }

            if (contadorIgualdadeElementosMesmoNivel01 == 2) {

                System.out.println("Não possui fronteira inferior máxima.");

            } else {

                identificaMaximaFronteira(contadorIgualdade, listaValorInf, valor01, valor02);

            }

        }

    }

    private void complementoVerificaReticulado(int valor01, int valor02) {

        listaValorInf = new int[getNumNodos()];

        contadorIgualdade = 0;
        contadorIgualdadeElementosMesmoNivel01 = 0;
        contadorIgualdadeElementosMesmoNivel02 = 0;

        ListaAdjacencia(valor01, 1);
        ListaAdjacencia(valor02, 2);

        for (int j = 0; j < getNumNodos(); j++) {

            armazenaValorListaAdjacente = 0;

            if (listaAdjacentesValor01[j] < (valor01 + 1) && listaAdjacentesValor01[j] < (valor02 + 1) && listaAdjacentesValor01[j] != 0 && listaAdjacentesValor01[j] != 0) {

                armazenaValorListaAdjacente = listaAdjacentesValor01[j];

            }

            if (armazenaValorListaAdjacente != 0) {

                for (int i = 0; i < getNumNodos(); i++) {

                    if (armazenaValorListaAdjacente == listaAdjacentesValor02[i]) {

                        contadorIgualdadeElementosMesmoNivel01++;

                    }

                }

            }

        }

        ListaAdjacencia(valor01, 1);
        ListaAdjacencia(valor02, 2);

        for (int j = 0; j < getNumNodos(); j++) {

            armazenaValorListaAdjacente = 0;

            if (listaAdjacentesValor01[j] > (valor01 + 1) && listaAdjacentesValor01[j] > (valor02 + 1)) {

                armazenaValorListaAdjacente = listaAdjacentesValor01[j];

            }

            if (armazenaValorListaAdjacente != 0) {

                for (int i = 0; i < getNumNodos(); i++) {

                    if (armazenaValorListaAdjacente == listaAdjacentesValor02[i]) {

                        contadorIgualdadeElementosMesmoNivel02++;

                    }

                }

            }

        }

        if (contadorIgualdadeElementosMesmoNivel01 == 2 || contadorIgualdadeElementosMesmoNivel02 == 2) {

            valorDeParada = 1;

        }

    }

    public void verificaReticulado() {

        valorFinal = getNumNodos();
        contador01 = 0;
        contadorLigacoes01 = 0;
        contadorLigacoes02 = 0;
        valorDeParada = 0;

        for (int i = 0; i < valorFinal; i++) {

            if (matrizElementoSuperiores[i][valorFinal - 1] == getNumNodos()) {

                contadorLigacoes01++;

            }

        }

        for (int i = 0; i < valorFinal; i++) {

            if (matrizElementoInferiores[i][0] == 1) {

                contadorLigacoes02++;

            }

        }

        if ((contadorLigacoes01 == valorFinal) && (contadorLigacoes02 == valorFinal)) {

            for (int i = 0; i < valorFinal; i++) {

                for (int j = 0; j < valorFinal; j++) {

                    if ((contador01 != i) && (contador01 > i)) {

                        if (valorDeParada != 1) {

                            complementoVerificaReticulado(i, contador01);

                        }

                    }

                    contador01++;

                }

                contador01 = 0;

            }

            if (valorDeParada != 1) {

                System.out.print("\n");
                System.out.println("É reticulado.");

            } else {

                System.out.print("\n");
                System.out.println("Não é reticulado, pois não existe fronteira inferior máxima nem fronteira superior mínima em todos os pares de elementos.");

            }

        } else {

            System.out.print("\n");
            System.out.println("Não é reticulado, pois não possui fronteira superior mínima ou fronteira inferior máximo.");

        }

    }

    private void setMatrizCriada(Integer[][] valores) {

        this.matrizCriada = valores;

    }

    private void setMatrizElementoSuperiores(Integer[][] zero) {

        this.matrizElementoSuperiores = zero;

    }

    private void setMatrizElementoInferiores(Integer[][] zero) {

        this.matrizElementoInferiores = zero;

    }

    private int getNumNodos() {

        return numNodos;

    }

}
