#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

typedef struct {
    int numero;
    double saldo;
} ContaBancaria;

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

int depositar(ContaBancaria *conta, double valor) 
{
    if (valor < 0) {
        return -1;
    }
    pthread_mutex_lock(&mutex);
    conta->saldo += valor;
    pthread_mutex_unlock(&mutex);
    return conta->saldo;
}

int sacar(ContaBancaria *conta, double valor)
{
    if (valor < 0 || conta->saldo < valor) {
        return -1;
    }
    pthread_mutex_lock(&mutex);
    conta->saldo -= valor;
    pthread_mutex_unlock(&mutex);
    return conta->saldo;
}

void *thread_depositos(void *args)
{
    ContaBancaria *conta = (ContaBancaria*)args;
    for (int i = 0; i < 100; i++) {
        depositar(conta, 200);
    }
    return NULL;
}

void *thread_saques(void *args)
{
    ContaBancaria *conta = (ContaBancaria*)args;
    for (int i = 0; i < 100; i++) {
        sacar(conta, 200);
    }
    return NULL;
}

int main() {
    pthread_t thread_id1, thread_id2;
    ContaBancaria conta;
    conta.numero = 1;
    conta.saldo = 1000;

    pthread_create(&thread_id1, NULL, thread_depositos, &conta);
    pthread_create(&thread_id2, NULL, thread_saques, &conta);
    pthread_join(thread_id1, NULL);
    pthread_join(thread_id2, NULL);

    printf("Saldo final = %lf\n", conta.saldo);
    return 0;
}