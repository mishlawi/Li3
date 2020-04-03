#ifndef Filial_H
#define Filial_H

#include <glib.h>
#include <stdlib.h>
#include "Venda.h"
#include "Navegador.h"

#define FILIAL 3
#define MES 12

typedef struct filial *Filial;
typedef struct infoCli *InfoCli;
typedef struct infoProd *InfoProd;
typedef struct qntProds *QntProds;

Filial inicializa_Filial();
Filial insereFilial(Filial f,Venda v);
gboolean existeCliFil(Filial f,char* codigo);
gboolean existeProdFil(Filial f,char* codigo);
void removeFilial(Filial f);
int produtosCompradosCliente(Filial fil,int mes,char* code);
gboolean temProduto(InfoCli ic,char* productID,char tipo);
Lista buyers (Filial f,Lista lst,char* productID,char tipo);
GHashTable* produtosQueMaisComprou(gpointer ht,Filial f,char* codigoCli,int mes);
int getQntQP(QntProds qp);
char* getCodeQP(QntProds qp);


#endif