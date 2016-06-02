package com.reticulados;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        int numero_elem;
        int saida=0;
        int elemento1;
        int elemento2;

        System.out.println("Informe o número de elementos.");
        numero_elem = entrada.nextInt();

        verificaReticulados Grafo = new verificaReticulados(numero_elem);

        //for para fazer todas ligações com o proprio elemento, ex: 1,1; 2,2; 3,3; ....
        for (int i = 0; i < numero_elem; i++) {
            Grafo.insereAresta(i, i, 1);
        }

        while(saida != 5){
            System.out.println("\nO que deseja fazer:");
            System.out.println("Digite 1 : Inserir relações.");
            System.out.println("Digite 2 : Excluir relações.");
            System.out.println("Digite 3 : Para consultar elementos.");
            System.out.println("Digite 4 : Para verificar se é um reticulado.");
            System.out.println("Digite 5 : Sair.");
            saida = entrada.nextInt();

            if(saida == 1){
                System.out.println("Digite o elemento 1 que será inserido. ");
                elemento1 = entrada.nextInt();
                System.out.println("Digite o elemento 2 que será inserido. ");
                elemento2 = entrada.nextInt();
                Grafo.insereAresta((elemento1-1), (elemento2-1), 1);


            }else if(saida == 2){
                System.out.println("Digite o elemento 1 que será excluído. ");
                elemento1 = entrada.nextInt();
                System.out.println("Digite o elemento 2 que será excluído. ");
                elemento2 = entrada.nextInt();
                Grafo.retiraAresta((elemento1-1), (elemento2-1));
                Grafo.esvaziaMatriz();
                Grafo.identElementosSuperiores();
                Grafo.identElementosInferiores();

            }else if(saida == 3){
                System.out.println("Digite o elemento 1 que será consultado. ");
                elemento1 = entrada.nextInt();
                System.out.println("Digite o elemento 2 que será consultado. ");
                elemento2 = entrada.nextInt();
                Grafo.esvaziaMatriz();
                Grafo.identElementosSuperiores();
                Grafo.identElementosInferiores();
                Grafo.imprimeMatrizElemSuperiores(Grafo.getMatrizElemSuperiores());
                Grafo.imprimeMatrizElemInferiores(Grafo.getMatrizElemInferiores());
                Grafo.identFronteiraSupMinima((elemento1-1), (elemento2-1));
                Grafo.identFronteiraInfMaxima((elemento1-1), (elemento2-1));
            }else if(saida == 4){
                Grafo.esvaziaMatriz();
                Grafo.identElementosSuperiores();
                Grafo.identElementosInferiores();
                Grafo.imprimeMatrizElemSuperiores(Grafo.getMatrizElemSuperiores());
                Grafo.imprimeMatrizElemInferiores(Grafo.getMatrizElemInferiores());
                Grafo.verificaReticulado();
            }
        }
    }
}
