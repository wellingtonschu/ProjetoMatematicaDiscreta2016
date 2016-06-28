package com.reticulados;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        int valorElemento;
        int opcao = 0;
        int elemento01;
        int elemento02;

        System.out.print("Informe a quantidade de elementos: ");
        valorElemento = entrada.nextInt();

        verificaReticulados funcoes = new verificaReticulados(valorElemento);

        //for para fazer todas ligações com o proprio elemento, ex: 1,1; 2,2; 3,3; ....
        for (int i = 0; i < valorElemento; i++) {

            funcoes.insereAresta(i, i, 1);

        }

        while(opcao != 4){

            System.out.println("\nOpções: ");
            System.out.println("1 - Inserir Relação");
            System.out.println("2 - Consultar Relação");
            System.out.println("3 - Verificar Reticulado");
            System.out.println("4 - Sair");
            System.out.print("\nOpção: ");
            opcao = entrada.nextInt();

            if(opcao == 1){

                System.out.print("\nInforme o primeiro elemento: ");
                elemento01 = entrada.nextInt();
                System.out.print("Informe o segundo elemento: ");
                elemento02 = entrada.nextInt();
                funcoes.insereAresta((elemento01 - 1), (elemento02 - 1), 1);

            }else if(opcao == 2){

                System.out.print("\nInforme o primeiro elemento a ser consultado: ");
                elemento01 = entrada.nextInt();
                System.out.print("Informe o segundo elemento a ser consultado: ");
                elemento02 = entrada.nextInt();
                funcoes.esvaziaMatriz();
                System.out.println("");
                funcoes.identElementosSuperiores();
                funcoes.identElementosInferiores();
                funcoes.identFronteiraSupMinima((elemento01 - 1), (elemento02 - 1));
                funcoes.identFronteiraInfMaxima((elemento01 - 1), (elemento02 - 1));

            }else if(opcao == 3){

                funcoes.esvaziaMatriz();
                funcoes.identElementosSuperiores();
                funcoes.identElementosInferiores();
                funcoes.verificaReticulado();

            }

        }

    }

}
