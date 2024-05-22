#include <stdio.h>
#include <pthread.h>

#define NUM_THREADS 10
#define COUNT 100000

int counter = 0;
pthread_mutex_t lock;
int thread_ids[NUM_THREADS]; // vetor de IDs das threads

void* increment(void* arg) {
    // Cast e dereferência do argumento para obter o ID da thread
    int id = *((int*)arg);
    printf("Iniciando thread %d\n", id);
    for(int i = 0; i < COUNT; i++) {
        pthread_mutex_lock(&lock);
        counter++;
        pthread_mutex_unlock(&lock);
    }
    return NULL;
}

int main() {
    pthread_t threads[NUM_THREADS];
    pthread_mutex_init(&lock, NULL);

    for(int i = 0; i < NUM_THREADS; i++) {
        // Salva o ID da thread
        thread_ids[i] = i;
        printf("Criando thread %d\n", i);
        pthread_create(&threads[i], NULL, increment, &thread_ids[i]);
    }

    for(int i = 0; i < NUM_THREADS; i++) {
        pthread_join(threads[i], NULL);
    }

    printf("Resultado final: %d\n", counter);
    pthread_mutex_destroy(&lock);

    return 0;
}
