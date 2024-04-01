#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <stdlib.h>

typedef struct banheiro {
    int numero_pessoas;
    sem_t sem;
} banheiro_t;

struct args {
    int id;
    banheiro_t *banheiro;
};

void banheiro_init(banheiro_t *banheiro) {
    banheiro->numero_pessoas = 0;
    sem_init(&banheiro->sem, 0, 3);
}

void usa_banheiro(banheiro_t *banheiro, int id) {
    printf("%d esperando para entrar no banheiro\n", id);
    sem_wait(&banheiro->sem);

    printf("%d entrou no banheiro\n", id);

    int tempo = rand() % 5 + 1;  // NOLINT
    sleep(tempo);

    sem_post(&banheiro->sem);
    printf("%d saiu do banheiro\n", id);
}

void *simula_pessoa(void *arg) {
    struct args *args = (struct args *)arg;
    usa_banheiro(args->banheiro, args->id);

    return NULL;
}

int main() {
    banheiro_t banheiro;
    banheiro_init(&banheiro);

    pthread_t threads[10];
    struct args thread_args[10];

    for (int i = 0; i < 10; i++) {
        thread_args[i].id = i;
        thread_args[i].banheiro = &banheiro;
        pthread_create(&threads[i], NULL, simula_pessoa, &thread_args[i]);
    }

    for (int i = 0; i < 10; i++) {
        pthread_join(threads[i], NULL);
    }

    return 0;
}